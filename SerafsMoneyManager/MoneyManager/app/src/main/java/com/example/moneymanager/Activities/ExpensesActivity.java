package com.example.moneymanager.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.moneymanager.Adaptor.ExpensesAdaptor;
import com.example.moneymanager.Constants;
import com.example.moneymanager.Database.ExpenseRepositry;
import com.example.moneymanager.Database.IncomeRepositry;
import com.example.moneymanager.ModalClass.Expenses;
import com.example.moneymanager.databinding.ActivityExpensesBinding;
import com.example.moneymanager.utils.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.N)
public class ExpensesActivity extends AppCompatActivity {
    final Calendar myCalendar = Calendar.getInstance();
    ActivityExpensesBinding binding;
    ArrayList<Expenses> expensesList;
    ExpensesAdaptor adaptor;
    ExpenseRepositry expenseRepositry;
    List<Expenses> list;
    IncomeRepositry repositry;
    int cashBalance, cardBalance, depositBalance;
    String currencyType, paymentType;
    int euroAmount, ronAmount;
    boolean isEveryMonth = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExpensesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        currencyType = Utilities.getString(getApplicationContext(), Constants.CURRENCY);
        paymentType = Utilities.getString(getApplicationContext(), Constants.PAYMENT);


        repositry = new IncomeRepositry(getApplication());
        expenseRepositry = new ExpenseRepositry(getApplication());

        if(currencyType.equals(Constants.EURO)){

            cashBalance= (int) ((repositry.getIncomeSumType(Constants.CASH)-expenseRepositry.sumAmountType(Constants.CASH)));
            cardBalance= (int) ((repositry.getIncomeSumType(Constants.CARD)-expenseRepositry.sumAmountType(Constants.CARD)));
            depositBalance= (int) ((repositry.getIncomeSumType(Constants.DEPOSIT)-expenseRepositry.sumAmountType(Constants.DEPOSIT)));

        }else if(currencyType.equals(Constants.RON)) {
            cashBalance= (int) ((repositry.ronIncomeType(Constants.CASH)-expenseRepositry.sumExpenseRon(Constants.CASH)));
            cardBalance= (int) ((repositry.ronIncomeType(Constants.CARD)-expenseRepositry.sumExpenseRon(Constants.CARD)));
            depositBalance= (int) ((repositry.ronIncomeType(Constants.DEPOSIT)-expenseRepositry.sumExpenseRon(Constants.DEPOSIT)));

        }


        expensesList = new ArrayList<>();

        binding.toggle.toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ExpensesActivity.this, MenuActivity.class));
            }
        });
        String[] payment = {Constants.CARD, Constants.CASH, Constants.DEPOSIT};

        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (this, android.R.layout.select_dialog_item, payment);
        binding.type.setAdapter(adapter);

        if (paymentType != null) {
            int spinnerPosition = adapter.getPosition(paymentType);
            binding.type.setSelection(spinnerPosition);
        }
        binding.type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (payment[i].equals(Constants.CASH)) {
                    Utilities.saveString(getApplicationContext(), Constants.PAYMENT, Constants.CASH);
                    paymentType=Constants.CASH;
                }
                if (payment[i].equals(Constants.CARD)) {
                    Utilities.saveString(getApplicationContext(), Constants.PAYMENT, Constants.CARD);
                    paymentType=Constants.CARD;

                }
                if (payment[i].equals(Constants.DEPOSIT)) {
                    Utilities.saveString(getApplicationContext(), Constants.PAYMENT, Constants.DEPOSIT);
                    paymentType=Constants.DEPOSIT;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Utilities.saveString(getApplication(), Constants.PAYMENT, Constants.CASH);

            }
        });


        binding.swOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                isEveryMonth = isChecked;
            }
        });

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String expenseName = binding.evExpense.getText().toString();
                String amount = binding.etAmount.getText().toString();
                String date = binding.etDate.getText().toString();

                if (expenseName.isEmpty()) {
                    binding.evExpense.setError("please enter expense name");
                } else if (amount.isEmpty()) {
                    binding.etAmount.setError("please enter amount");
                } else if (date.isEmpty()) {
                    binding.etDate.setError("please enter date");
                } else if (currencyType.equals(Constants.EURO)) {
                    euroAmount = Integer.parseInt(amount);
                    ronAmount = (int) (Integer.parseInt(amount) * 4.95);
                    EuroData(paymentType, String.valueOf(euroAmount), date, expenseName, String.valueOf(ronAmount));

                } else if (currencyType.equals(Constants.RON)) {
                    ronAmount = Integer.parseInt(amount);
                    euroAmount = (int) (Integer.parseInt(amount) / 4.95);
                    ronData(paymentType, Integer.toString(euroAmount), date, expenseName, String.valueOf(ronAmount));
                }
            }
        });

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
            }
        };

        binding.etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ExpensesActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        binding.rvList.setLayoutManager(new LinearLayoutManager(this));
        list = expenseRepositry.getAllRecords();
        adaptor = new ExpensesAdaptor(list, ExpensesActivity.this);
        binding.rvList.setAdapter(adaptor);

    }

    private void addData(String expenseName, String date, String type, String ronAmount, String euroAmount) {

        Expenses expenses = new Expenses(expenseName,
                date,
                type,
                Integer.parseInt(ronAmount),
                Integer.parseInt(euroAmount), isEveryMonth);

        expenseRepositry.insert(expenses);
        adaptor.AddItem(expenses);
        binding.evExpense.setText("");
        binding.etAmount.setText("");
        binding.etDate.setText("");
    }

    private void updateLabel() {

        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        binding.etDate.setText(dateFormat.format(myCalendar.getTime()));
    }

    public void EuroData(String type, String euroAmount, String date, String expenseName, String ronAmount) {
        if (type.equals("cash")) {
            if (cashBalance - Integer.parseInt(euroAmount) < 0) {
                Toast.makeText(ExpensesActivity.this, "cash balance not ensough", Toast.LENGTH_SHORT).show();
            } else {
                addData(expenseName, date, type, ronAmount, euroAmount);

            }
        } else if (type.equals("card")) {
            if (cardBalance - Integer.parseInt(euroAmount) < 0) {
                Toast.makeText(ExpensesActivity.this, "card balance not ensough", Toast.LENGTH_SHORT).show();
            } else {
                addData(expenseName, date, type, ronAmount, euroAmount);
            }

        } else if (type.equals("deposit")) {
            if (depositBalance - Integer.parseInt(euroAmount) < 0) {
                Toast.makeText(ExpensesActivity.this, "Deposit balance not ensough", Toast.LENGTH_SHORT).show();
            } else {
                addData(expenseName, date, type, ronAmount, euroAmount);
            }
        }
    }

    public void ronData(String type, String euroAmount, String date, String expenseName, String ronAmount) {
        if (type.equals("cash")) {
            if (cashBalance - Integer.parseInt(ronAmount) < 0) {
                Toast.makeText(ExpensesActivity.this, "cash balance not ensough", Toast.LENGTH_SHORT).show();
            } else {
                addData(expenseName, date, type, ronAmount, euroAmount);
            }
        } else if (type.equals("card")) {
            if (cardBalance - Integer.parseInt(ronAmount) < 0) {
                Toast.makeText(ExpensesActivity.this, "card balance not ensough", Toast.LENGTH_SHORT).show();
            } else {
                addData(expenseName, date, type, ronAmount, euroAmount);
            }

        } else if (type.equals("deposit")) {
            if (depositBalance - Integer.parseInt(ronAmount) < 0) {
                Toast.makeText(ExpensesActivity.this, "Deposit balance not ensough", Toast.LENGTH_SHORT).show();
            } else {
                addData(expenseName, date, type, ronAmount, euroAmount);
            }
        }
    }

}
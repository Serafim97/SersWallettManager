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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.moneymanager.Adaptor.IncomeAdaptor;
import com.example.moneymanager.Constants;
import com.example.moneymanager.Database.IncomeRepositry;
import com.example.moneymanager.ModalClass.Income;
import com.example.moneymanager.MyWork;
import com.example.moneymanager.databinding.ActivityIncomeBinding;
import com.example.moneymanager.utils.Utilities;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@RequiresApi(api = Build.VERSION_CODES.N)
public class IncomeActivity extends AppCompatActivity {
    final Calendar myCalendar = Calendar.getInstance();
    ActivityIncomeBinding binding;
    IncomeRepositry repositry;
    List<Income> list;
    IncomeAdaptor adaptor;
    String paymentType,currencyType;
    int euroAmount, ronAmount;
    WorkManager manager;

    boolean isEveryMonth = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIncomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        manager = WorkManager.getInstance();

        paymentType = Utilities.getString(getApplicationContext(), Constants.PAYMENT);
        currencyType = Utilities.getString(getApplicationContext(), Constants.CURRENCY);
        repositry = new IncomeRepositry(getApplication());
        list = repositry.getAllRecords();
        adaptor = new IncomeAdaptor(list, this);
        binding.rvIncomeList.setLayoutManager(new LinearLayoutManager(this));
        binding.rvIncomeList.setAdapter(adaptor);

        binding.toggle.toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IncomeActivity.this, MenuActivity.class));
            }
        });


        binding.swOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    isEveryMonth = true;
                } else {
                    isEveryMonth = false;
                }
            }
        });

        String[] payment={Constants.CARD,Constants.CASH,Constants.DEPOSIT};

        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (this,android.R.layout.select_dialog_item,payment);
        binding.type.setAdapter(adapter);

        if (paymentType != null) {
            int spinnerPosition = adapter.getPosition(paymentType);
            binding.type.setSelection(spinnerPosition);
            adapter.notifyDataSetChanged();
        }
        binding.type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(payment[i].equals(Constants.CASH)){
                    Utilities.saveString(getApplicationContext(),Constants.PAYMENT,Constants.CASH);
                    paymentType= Constants.CASH;
                }
                if(payment[i].equals(Constants.CARD)){
                    Utilities.saveString(getApplicationContext(),Constants.PAYMENT,Constants.CARD);
                    paymentType= Constants.CARD;
                }
                if(payment[i].equals(Constants.DEPOSIT)){
                    Utilities.saveString(getApplicationContext(),Constants.PAYMENT,Constants.DEPOSIT);
                    paymentType= Constants.DEPOSIT;
                }
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Utilities.saveString(getApplication(), Constants.PAYMENT,Constants.CASH);

            }
        });


        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String incomeName = binding.etIncome.getText().toString();
                String amount = binding.etAmount.getText().toString();
                String date = binding.etDate.getText().toString();


                if (incomeName.isEmpty()) {
                    binding.etIncome.setError("please enter Income name");

                } else if (amount.isEmpty()) {
                    binding.etAmount.setError("please enter amount");

                } else if (date.isEmpty()) {
                    binding.etDate.setError("please enter date");

                }  else if (currencyType.equals(Constants.EURO)) {

                    euroAmount = Integer.parseInt(amount);
                    ronAmount = (int) (Integer.parseInt(amount) * 4.95);;
                    euroData(incomeName, date, paymentType, String.valueOf(euroAmount), String.valueOf(ronAmount));

                } else if (currencyType.equals(Constants.RON)) {
                    ronAmount = Integer.parseInt(amount);

                    try {
                        euroAmount = (int) (Integer.parseInt(amount) / 4.95);
                        ronData(incomeName, date, paymentType, Integer.toString(euroAmount), String.valueOf(ronAmount));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
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
                new DatePickerDialog(IncomeActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void euroData(String incomeName, String date, String type, String euroAmount, String ronAmount) {
        addData(incomeName, date, type, euroAmount, ronAmount);
    }

    private void addData(String incomeName, String time, String type, String euroAmount, String ronAmount) {

        Income income = new Income(incomeName, time, type, Integer.parseInt(euroAmount), Integer.parseInt(ronAmount), isEveryMonth);
        repositry.insert(income);
        adaptor.insertData(income);
        if(isEveryMonth) {
            PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(MyWork.class,
                    2, TimeUnit.MINUTES).build();
            manager.enqueueUniquePeriodicWork(incomeName, ExistingPeriodicWorkPolicy.KEEP, request);

        }
        binding.etIncome.setText("");
        binding.etAmount.setText("");
        binding.etDate.setText("");


    }

    private void updateLabel() {

        String myFormat = "MM.dd.yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        binding.etDate.setText(dateFormat.format(myCalendar.getTime()));
    }

    public void ronData(String incomeName, String date, String type, String euroAmount, String ronAmount) {
        addData(incomeName, date, type, euroAmount, ronAmount);
    }
}
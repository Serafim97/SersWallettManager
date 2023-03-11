package com.example.moneymanager.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.moneymanager.Constants;
import com.example.moneymanager.Database.ExpenseRepositry;
import com.example.moneymanager.Database.IncomeRepositry;
import com.example.moneymanager.ModalClass.Expenses;
import com.example.moneymanager.ModalClass.Income;
import com.example.moneymanager.R;
import com.example.moneymanager.databinding.ActivityMainBinding;
import com.example.moneymanager.utils.Utilities;

import java.util.List;

public class MainActivity extends AppCompatActivity {
   ActivityMainBinding binding;
   float balance,cashBalance,cardBalance,depositBalance;
   String currencyType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        currencyType= Utilities.getString(getApplicationContext(), Constants.CURRENCY);
        binding.txIncome.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,IncomeActivity.class));
            }
        });
        binding.txExpenses.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ExpensesActivity.class));
            }
        });
        binding.toggle.toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MenuActivity.class));
            }
        });

        ExpenseRepositry expenseRepositry= new ExpenseRepositry(getApplication());
        IncomeRepositry repositry=new IncomeRepositry(getApplication());


        if(currencyType.equals(Constants.EURO)){

            cashBalance= (float) ((repositry.getIncomeSumType(Constants.CASH)-expenseRepositry.sumAmountType(Constants.CASH)));
            cardBalance= (float) ((repositry.getIncomeSumType(Constants.CARD)-expenseRepositry.sumAmountType(Constants.CARD)));
            depositBalance= (float) ((repositry.getIncomeSumType(Constants.DEPOSIT)-expenseRepositry.sumAmountType(Constants.DEPOSIT)));

        }else if(currencyType.equals(Constants.RON)) {
            cashBalance= (float) ((repositry.ronIncomeType(Constants.CASH)-expenseRepositry.sumExpenseRon(Constants.CASH)));
            cardBalance= (float) ((repositry.ronIncomeType(Constants.CARD)-expenseRepositry.sumExpenseRon(Constants.CARD)));
            depositBalance= (float) ((repositry.ronIncomeType(Constants.DEPOSIT)-expenseRepositry.sumExpenseRon(Constants.DEPOSIT)));
        }

        binding.tvCashBal.setText(String.valueOf(cashBalance));
        binding.tvCardBal.setText(String.valueOf(cardBalance));
        binding.tvDepositBal.setText(String.valueOf(depositBalance));
        binding.currentBalance.setText("Current Balance"+" ("+currencyType+")");


    }
}
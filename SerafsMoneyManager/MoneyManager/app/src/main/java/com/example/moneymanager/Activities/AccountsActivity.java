package com.example.moneymanager.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moneymanager.Constants;
import com.example.moneymanager.Database.ExpenseRepositry;
import com.example.moneymanager.Database.IncomeRepositry;
import com.example.moneymanager.databinding.ActivityAccountsBinding;
import com.example.moneymanager.utils.Utilities;

public class AccountsActivity extends AppCompatActivity {
    ActivityAccountsBinding binding;
    IncomeRepositry incomeRepositry;
    ExpenseRepositry expenseRepositry;
    float cashBalance,cardBalance,depositBalance;
    String currencyType;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAccountsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        currencyType= Utilities.getString(getApplicationContext(), Constants.CURRENCY);

        incomeRepositry = new IncomeRepositry(getApplication());
        expenseRepositry = new ExpenseRepositry(getApplication());


        binding.toggle.toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountsActivity.this, MenuActivity.class));
            }
        });
        if(currencyType.equals(Constants.EURO)){

            cashBalance= (float) ((incomeRepositry.getIncomeSumType(Constants.CASH)-expenseRepositry.sumAmountType(Constants.CASH)));
            cardBalance= (float) ((incomeRepositry.getIncomeSumType(Constants.CARD)-expenseRepositry.sumAmountType(Constants.CARD)));
            depositBalance= (float) ((incomeRepositry.getIncomeSumType(Constants.DEPOSIT)-expenseRepositry.sumAmountType(Constants.DEPOSIT)));

        }else if(currencyType.equals(Constants.RON)) {
            cashBalance= (float) ((incomeRepositry.ronIncomeType(Constants.CASH)-expenseRepositry.sumExpenseRon(Constants.CASH)));
            cardBalance= (float) ((incomeRepositry.ronIncomeType(Constants.CARD)-expenseRepositry.sumExpenseRon(Constants.CARD)));
            depositBalance= (float) ((incomeRepositry.ronIncomeType(Constants.DEPOSIT)-expenseRepositry.sumExpenseRon(Constants.DEPOSIT)));

        }
        
        binding.etCard.setText(cardBalance +" ("+currencyType+")");
        binding.etCAsh.setText(cashBalance +" ("+currencyType+")");
        binding.etDeposit.setText(depositBalance +" ("+currencyType+")");
        binding.btnAddIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountsActivity.this, IncomeActivity.class));
            }
        });
        binding.btnSubtractInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountsActivity.this, ExpensesActivity.class));
            }
        });


    }
}
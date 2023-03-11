package com.example.moneymanager.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.moneymanager.Constants;
import com.example.moneymanager.Database.ExpenseRepositry;
import com.example.moneymanager.Database.IncomeRepositry;
import com.example.moneymanager.databinding.ActivitySettingBinding;
import com.example.moneymanager.utils.Utilities;

public class SettingActivity extends AppCompatActivity {
    ActivitySettingBinding binding;
    IncomeRepositry incomeRepositry;
    ExpenseRepositry expenseRepositry;
    String currencyType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        currencyType = Utilities.getString(getApplicationContext(),Constants.CURRENCY);

        incomeRepositry=new IncomeRepositry(getApplication());
        expenseRepositry=new ExpenseRepositry(getApplication());

        String[] currency={"Ron","Euro"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (this,android.R.layout.select_dialog_item,currency);
        binding.currency.setAdapter(adapter);


        if (currencyType != null) {
            int spinnerPosition = adapter.getPosition(currencyType);
            binding.currency.setSelection(spinnerPosition);
        }
        binding.currency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(currency[i].toString() == "Euro"){

                    Utilities.saveString(getApplication(), Constants.CURRENCY,Constants.EURO);
                    currencyType=Constants.EURO;

                }else{

                    Utilities.saveString(getApplication(), Constants.CURRENCY,Constants.RON);
                    currencyType=Constants.RON;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Utilities.saveString(getApplication(), Constants.CURRENCY,Constants.RON);

            }
        });

        binding.toggle.toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this,MenuActivity.class));
            }
        });
        binding.btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incomeRepositry.deleteAllCourses();
                expenseRepositry.deleteAllCourses();

            }
        });


    }
}
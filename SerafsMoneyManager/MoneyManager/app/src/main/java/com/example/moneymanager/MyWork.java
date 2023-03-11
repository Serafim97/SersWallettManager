package com.example.moneymanager;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.moneymanager.Database.ExpensesDatabase;
import com.example.moneymanager.Database.IncomeRepositry;
import com.example.moneymanager.ModalClass.Income;

import java.util.ArrayList;

public class MyWork extends Worker {

    public MyWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        ExpensesDatabase database;
        database = ExpensesDatabase.getInstance(getApplicationContext());
        ArrayList<Income> incomeArrayList = (ArrayList<Income>) database.Dao().getAllIncomes();

        Boolean isEveryMonth = false;

        for (int i = 0; i < incomeArrayList.size(); i++) {

            isEveryMonth = incomeArrayList.get(i).isEveryMonth();
            if (isEveryMonth) {
                Income income = new Income(incomeArrayList.get(i).getIncome(),
                        incomeArrayList.get(i).getDate(),
                        incomeArrayList.get(i).getIncomeType(),
                        incomeArrayList.get(i).getAmountEuro(),
                        incomeArrayList.get(i).getAmountRon(),
                        incomeArrayList.get(i).isEveryMonth());

                Log.d("Tag",income.getIncome());
                IncomeRepositry incomeRepositry = new IncomeRepositry(getApplicationContext());
                incomeRepositry.insert(income);
            }

        }
        return Result.success();
    }
}

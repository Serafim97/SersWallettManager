package com.example.moneymanager.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moneymanager.Database.ExpenseRepositry;
import com.example.moneymanager.Database.IncomeRepositry;
import com.example.moneymanager.ModalClass.Expenses;
import com.example.moneymanager.ModalClass.Income;
import com.example.moneymanager.R;
import com.example.moneymanager.databinding.ActivityStatsBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.List;

public class StatsActivity extends AppCompatActivity {
    ActivityStatsBinding binding;
    List<Income> incomes;
    List<Expenses> expenses;
    IncomeRepositry incomeRepositry;
    ExpenseRepositry expenseRepositry;
    BarChart barChart;

    SwitchMaterial switchExpense, switchIncome;
    BarDataSet barDataSet1, barDataSet2;

    ArrayList barEntries;
    ArrayList barEntries2;
    ArrayList<String> days = new ArrayList<>();
    ArrayList<String> days2 = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStatsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        barEntries = new ArrayList();
        barEntries2 = new ArrayList();

        expenses = new ArrayList<>();
        incomeRepositry = new IncomeRepositry(getApplication());
        expenseRepositry = new ExpenseRepositry(getApplication());
        switchExpense = binding.switchExpenses;
        switchIncome = binding.switchIncome;
        incomes = new ArrayList<>();

        binding.switchExpenses.setVisibility(View.GONE);
        binding.switchIncome.setVisibility(View.GONE);
        incomes = incomeRepositry.getAllRecords();
        expenses = expenseRepositry.getAllRecords();

        barChart = binding.barchart;


        for (int i = 0; i < incomes.size() - 1; i++) {


            days.add(incomes.get(i).getDate());
            barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(days));
            barEntries.add(new BarEntry(1f + i, incomes.get(i).getAmountEuro()));

        }

        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(days2));


        for (int i = 0; i < expenses.size() - 1; i++) {
            days2.add(expenses.get(i).getDate());
            barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(days));
            barEntries2.add(new BarEntry(1f + i, expenses.get(i).getAmountEuro()));

        }

        binding.toggle.toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(StatsActivity.this, MenuActivity.class));
                finish();
            }
        });

        barDataSet1 = new BarDataSet(barEntries, "Income");
        barDataSet1.setColor(getApplicationContext().getResources().getColor(R.color.skyblue));
        barDataSet2 = new BarDataSet(barEntries2, "Expenses");
        barDataSet2.setColor(getApplicationContext().getResources().getColor(R.color.orangle));


        BarData data = new BarData(barDataSet1, barDataSet2);


        barChart.setData(data);


        barChart.getDescription().setEnabled(false);


        XAxis xAxis = barChart.getXAxis();


        xAxis.setCenterAxisLabels(true);


        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


        xAxis.setGranularity(1);


        xAxis.setGranularityEnabled(true);


        barChart.setDragEnabled(true);



        float barSpace = 0.1f;


        float groupSpace = 0.5f;


        data.setBarWidth(0.15f);


        barChart.getXAxis().setAxisMinimum(0);


        barChart.animate();


        barChart.groupBars(0, groupSpace, barSpace);

        barChart.setDragEnabled(true);


        barChart.setVisibleXRangeMaximum(5);

        barChart.invalidate();


        switchExpense.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setVisibilityForExpenses(b);
            }
        });
        switchIncome.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                setVisibilityForIncome(b);

            }
        });
    }

    private void setVisibilityForIncome(boolean b) {

        if (b) {
            barDataSet1.setVisible(false);
        } else {
            barDataSet1.setVisible(true);
        }

    }

    private void setVisibilityForExpenses(boolean b) {
        if (b) {
            barDataSet2.setVisible(false);
        } else {
            barDataSet2.setVisible(true);
        }
    }

}
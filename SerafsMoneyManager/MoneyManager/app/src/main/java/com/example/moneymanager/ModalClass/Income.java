package com.example.moneymanager.ModalClass;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (tableName = "income_table")

public class Income {

    String income,date,incomeType;
    int amountEuro,amountRon;
    boolean isEveryMonth;
    @PrimaryKey(autoGenerate = true)
    int id;
    @Ignore
    public Income(String income, int amountEuro) {
        this.income = income;
        this.amountEuro = amountEuro;
    }
    public Income(String income, String date, String incomeType, int amountEuro, int amountRon, boolean isEveryMonth) {
        this.income = income;
        this.date = date;
        this.incomeType = incomeType;
        this.amountEuro = amountEuro;
        this.amountRon = amountRon;
        this.isEveryMonth = isEveryMonth;
    }


    public boolean isEveryMonth() {
        return isEveryMonth;
    }

    public void setEveryMonth(boolean everyMonth) {
        isEveryMonth = everyMonth;
    }

    public String getincome() {
        return income;
    }

    public void setincome(String income) {
        this.income = income;
    }

    public int getAmountEuro() {
        return amountEuro;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getIncomeType() {
        return incomeType;
    }

    public void setIncomeType(String incomeType) {
        this.incomeType = incomeType;
    }

    public void setAmountEuro(int amountEuro) {
        this.amountEuro = amountEuro;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getincomeType() {
        return incomeType;
    }
    public void setincomeType(String incomeType) {
        this.incomeType = incomeType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmountRon() {
        return amountRon;
    }

    public void setAmountRon(int amountRon) {
        this.amountRon = amountRon;
    }
}

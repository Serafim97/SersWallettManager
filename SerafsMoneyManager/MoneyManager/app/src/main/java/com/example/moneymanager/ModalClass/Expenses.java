package com.example.moneymanager.ModalClass;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "expenses_table")
public class Expenses {
    public boolean isEveryMonth;
    String expenses, date, expenseType;
    int ronAmount, sumExpenses, amountEuro;
    @PrimaryKey(autoGenerate = true)
    int id;

    @Ignore
    public Expenses(String expenses, int amount) {
        this.expenses = expenses;
        this.ronAmount = amount;
    }

    @Ignore
    public Expenses() {

    }

    public Expenses(String expenses, String date, String expenseType, int ronAmount, int amountEuro, boolean isEveryMonth) {
        this.expenses = expenses;
        this.date = date;
        this.expenseType = expenseType;
        this.ronAmount = ronAmount;
        this.amountEuro = amountEuro;
        this.isEveryMonth = isEveryMonth;
    }

    public int getSumExpenses() {
        return sumExpenses;
    }

    public void setSumExpenses(int sumExpenses) {
        this.sumExpenses = sumExpenses;
    }

    public String getExpenses() {
        return expenses;
    }

    public void setExpenses(String expenses) {
        this.expenses = expenses;
    }

    public int getRonAmount() {
        return ronAmount;
    }

    public void setRonAmount(int ronAmount) {
        this.ronAmount = ronAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmountEuro() {
        return amountEuro;
    }

    public void setAmountEuro(int amountEuro) {
        this.amountEuro = amountEuro;
    }
}

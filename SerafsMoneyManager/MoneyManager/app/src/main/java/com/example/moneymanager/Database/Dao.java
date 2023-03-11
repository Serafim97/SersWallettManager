package com.example.moneymanager.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.moneymanager.ModalClass.Expenses;
import com.example.moneymanager.ModalClass.Income;

import java.util.List;
@androidx.room.Dao
public interface Dao {

    @Query("SELECT * FROM expenses_table")
    List<Expenses> getAll();
    @Insert
    void insertAll(Expenses... expenses);

    @Delete
    void delete(Expenses expenses);

    @Query("DELETE FROM expenses_table")
    void deleteAllCourses();

    @Query("SELECT SUM(" + "ronAmount" + ") as amountSum "+ " FROM " + "EXPENSES_TABLE")
    int getRonAmountSum();

 @Query("SELECT SUM(" + "amountEuro" + ") as amountSum "+ " FROM " + "EXPENSES_TABLE")
    int getEuroAmount();

    @Query("SELECT * FROM expenses_table WHERE expenseType = :card")
    List<Expenses> expenseType(String card);


    @Query("SELECT SUM(" + "amountEuro" + ") as amountSum "+ " FROM " + "EXPENSES_TABLE WHERE expenseType =:card")
       int getSum(String card);

    @Query("SELECT SUM(" + "ronAmount" + ") as amountSum "+ " FROM " + "EXPENSES_TABLE WHERE expenseType =:card")
       int getRonSum(String card);


    /*
    ***
  Income Queries
    ***
     */

    @Query("SELECT * FROM income_table")
    List<Income> getAllIncomes();

    @Insert void insertAll(Income... incomes);

    @Delete
    void delete(Income income);


    @Query("SELECT SUM(" + "amount" + "Euro) as amountSum "+ " FROM " + "INCOME_TABLE")
    int getEuroIncome();

    @Query("SELECT SUM(" + "amount" + "Ron) as amountSum "+ " FROM " + "INCOME_TABLE")
    int getRonIncome();

    @Query("DELETE FROM income_table")
    void deleteAllIncomes();

    @Query("SELECT * FROM INCOME_TABLE WHERE incomeType = :card")
    List<Income> load(String card);

    @Query("SELECT SUM(" + "amount" + "Euro) as amountSum "+ " FROM " + "income_table WHERE incomeType =:card")
    int getIncomeSumType(String card);

@Query("SELECT SUM(" + "amount" + "Ron) as amountSum "+ " FROM " + "income_table WHERE incomeType =:card")
    int getRonIncomeType(String card);

}


package com.example.moneymanager.Database;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import com.example.moneymanager.ModalClass.Expenses;
import com.example.moneymanager.ModalClass.Income;

import java.util.ArrayList;
import java.util.List;

public class IncomeRepositry {

    private Dao dao;
    private List<Income> allRecodrs;

    public IncomeRepositry
            (Context application) {
        ExpensesDatabase database = ExpensesDatabase.getInstance(application);
        dao = database.Dao();
        allRecodrs =dao.getAllIncomes();
    }

    public void insert(Income income) {
        new IncomeRepositry.InsertCourseAsyncTask(dao).execute(income);
    }
    public List<Income> cardEntries(String card){
        List<Income> list;

       return  list=dao.load(card);
    }
    public int ronIncomeType(String type){
        return dao.getRonIncomeType(type);
    }

    public int euroIncomeSum(){
        return dao.getEuroAmount();
    }
    public int ronIncomeSum(){
        return dao.getRonAmountSum();
    }
    public int getIncomeSumType(String IncomeType){
       return dao.getIncomeSumType(IncomeType);
    }

    public void delete(Income model) {
        new IncomeRepositry.DeleteCourseAsyncTask(dao).execute(model);
    }


    public void deleteAllCourses() {
        new IncomeRepositry.DeleteAllCoursesAsyncTask(dao).execute();
    }

    public List<Income> getAllRecords() {
        return allRecodrs;
    }

    private static class InsertCourseAsyncTask extends AsyncTask<Income, Void, Void> {
        private Dao dao;

        private InsertCourseAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Income... incomes) {
            // below line is use to insert our modal in dao.
            dao.insertAll(incomes[0]);
            return null;
        }
    }

    private static class DeleteCourseAsyncTask extends AsyncTask<Income, Void, Void> {
        private Dao dao;

        private DeleteCourseAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Income... models) {

            dao.delete(models[0]);
            return null;
        }
    }
    private static class DeleteAllCoursesAsyncTask extends AsyncTask<Void, Void, Void> {
        private Dao dao;
        private DeleteAllCoursesAsyncTask(Dao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllIncomes();

            return null;
        }
    }

}

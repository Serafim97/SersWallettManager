package com.example.moneymanager.Database;
import android.app.Application;
import android.os.AsyncTask;

import com.example.moneymanager.ModalClass.Expenses;

import java.util.List;

public class ExpenseRepositry
 {
    private Dao dao;
    private List<Expenses> allRecodrs;

    public ExpenseRepositry
(Application application) {
        ExpensesDatabase database = ExpensesDatabase.getInstance(application);
        dao = database.Dao();
        allRecodrs =dao.getAll();
    }

    public void insert(Expenses expenses) {
        new InsertCourseAsyncTask(dao).execute(expenses);
    }

   public int sumExpenses(){
        return dao.getRonAmountSum();
    }
    public int EuroSumExpense(){
        return dao.getEuroAmount();
    }
    public void delete(Expenses expenses){
        dao.delete(expenses);
    }

    public int sumAmountType(String amountType){
        return dao.getSum(amountType);
    }
     public int sumExpenseRon(String type){
        return dao.getRonSum(type);
     }
    public void deleteAllCourses() {
        new DeleteAllCoursesAsyncTask(dao).execute();
    }

    public List<Expenses> getAllRecords() {
        return allRecodrs;
    }

    static class InsertCourseAsyncTask extends AsyncTask<Expenses, Void, Void> {
        private Dao dao;

        InsertCourseAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Expenses... expenses) {
            // below line is use to insert our modal in dao.
            dao.insertAll(expenses[0]);
            return null;
        }
    }

    static class delete extends AsyncTask<Expenses, Void, Void> {
        private Dao dao;

        delete(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Expenses... models) {

            dao.delete(models[0]);
            return null;
        }
    }
    // we are creating a async task method to delete all courses.
    static class DeleteAllCoursesAsyncTask extends AsyncTask<Void, Void, Void> {
        private Dao dao;
        DeleteAllCoursesAsyncTask(Dao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllCourses();
            return null;
        }
    }

     static class ExpensesSum extends AsyncTask<Integer, Void, Integer> {
         private Dao dao;


         ExpensesSum(Dao dao) {
             this.dao = dao;
         }

         @Override
         protected Integer doInBackground(Integer... voids) {

             int result = dao.getRonAmountSum();
             return result;
         }

     }
     }

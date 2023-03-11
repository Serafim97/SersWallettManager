package com.example.moneymanager.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.moneymanager.ModalClass.Expenses;
import com.example.moneymanager.ModalClass.Income;

@Database(entities = {Expenses.class, Income.class}, version =7)
public abstract class ExpensesDatabase extends RoomDatabase {

    private static volatile ExpensesDatabase instance;

    public abstract Dao Dao();

    public static synchronized ExpensesDatabase getInstance(Context context) {

        if (instance == null) {
            instance =

                    Room.databaseBuilder(context.getApplicationContext(),
                            ExpensesDatabase.class, "course_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback).allowMainThreadQueries()
                            .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };
s
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        PopulateDbAsyncTask(ExpensesDatabase instance) {
            Dao dao = instance.Dao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
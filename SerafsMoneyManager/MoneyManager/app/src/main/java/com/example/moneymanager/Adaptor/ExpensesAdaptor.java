package com.example.moneymanager.Adaptor;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.Constants;
import com.example.moneymanager.Database.ExpenseRepositry;
import com.example.moneymanager.ModalClass.Expenses;
import com.example.moneymanager.R;
import com.example.moneymanager.databinding.ExpensesItemBinding;
import com.example.moneymanager.utils.Utilities;

import java.util.List;

public class ExpensesAdaptor extends RecyclerView.Adapter<ExpensesAdaptor.ViewHolder> {
    List<Expenses> expensesList;
    Context context;
    String currencyType;
    ExpenseRepositry expenseRepositry;

    public ExpensesAdaptor(List<Expenses> expensesList, Context context) {
        this.expensesList = expensesList;
        this.context = context;
    }


    @NonNull
    @Override
    public ExpensesAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.expenses_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return expensesList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ExpensesAdaptor.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        expenseRepositry=new ExpenseRepositry((Application) context.getApplicationContext());
        currencyType= Utilities.getString(context.getApplicationContext(), Constants.CURRENCY);
        Expenses expenses = expensesList.get(position);
            holder.binding.tvExpenses.setText(expenses.getExpenses());
        if(currencyType.equals(Constants.EURO)) {
            holder.binding.tvAmount.setText(String.valueOf(expenses.getAmountEuro()));
        }else if(currencyType.equals(Constants.RON)) {
            holder.binding.tvAmount.setText(String.valueOf(expenses.getRonAmount()));
        }
        holder.binding.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expenseRepositry.delete(expenses);
                notifyItemRemoved(position);
                expensesList.remove(position);
            }
        });
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ExpensesItemBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ExpensesItemBinding.bind(itemView);
        }
    }

    public void AddItem(Expenses expenses){
        expensesList.add(expenses);
        notifyDataSetChanged();
    }
}

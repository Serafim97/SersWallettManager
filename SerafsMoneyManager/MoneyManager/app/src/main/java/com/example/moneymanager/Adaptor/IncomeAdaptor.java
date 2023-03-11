package com.example.moneymanager.Adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.Constants;
import com.example.moneymanager.Database.IncomeRepositry;
import com.example.moneymanager.ModalClass.Income;
import com.example.moneymanager.R;
import com.example.moneymanager.databinding.ExpensesItemBinding;
import com.example.moneymanager.utils.Utilities;

import java.util.List;

public class IncomeAdaptor extends RecyclerView.Adapter<IncomeAdaptor.ViewHolder> {
List<Income>list;
Context context;
String currencyType;
IncomeRepositry incomeRepositry;

    public IncomeAdaptor(List<Income> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.expenses_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Income income=list.get(position);
        incomeRepositry=new IncomeRepositry(context.getApplicationContext());
        currencyType= Utilities.getString(context.getApplicationContext(), Constants.CURRENCY);
        holder.binding.tvExpenses.setText(income.getIncome());
        if(currencyType.equals(Constants.RON)) {
            holder.binding.tvAmount.setText(String.valueOf(income.getAmountRon()));
        }else if(currencyType.equals(Constants.EURO)){
            holder.binding.tvAmount.setText(String.valueOf(income.getAmountEuro()));
        }
        holder.binding.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incomeRepositry.delete(income);
                list.remove(position);
                notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ExpensesItemBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=ExpensesItemBinding.bind(itemView);
        }
    }
    public void insertData(Income income){
          list.add(income);
          notifyDataSetChanged();
    }


}


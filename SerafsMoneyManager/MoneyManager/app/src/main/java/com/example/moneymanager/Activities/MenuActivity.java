package com.example.moneymanager.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.moneymanager.databinding.ActivityMainBinding;
import com.example.moneymanager.databinding.ActivityMenuBinding;

public class MenuActivity extends AppCompatActivity {
 ActivityMenuBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.accounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this,AccountsActivity.class));
                finish();

            }
        });

        binding.stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this,StatsActivity.class));
                finish();

            }
        });

        binding.settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this,SettingActivity.class));
                finish();

            }
        });

        binding.home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this,MainActivity.class));
                finish();
            }
        });

    }
}
package com.example.blrlight_908_3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class About extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ActionBar acb = getSupportActionBar();
        if (acb != null) {
            acb.hide();
        }

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.about);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bluetooth:
                        startblue();
                        return true;

                    case R.id.about:
                        startabout();
                        return true;

                    case R.id.operation:
                        startmain();
                        return true;
                }
                return false;
            }
        });
    }

    private void startblue() {
        startActivity(new Intent(getApplicationContext(), Connection.class));
        overridePendingTransition(0, 0);
        this.finish();
    }

    private void startmain() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        overridePendingTransition(0, 0);
        this.finish();

    }

    private void startabout() {
        startActivity(new Intent(getApplicationContext(), About.class));
        overridePendingTransition(0, 0);
        this.finish();

    }

}
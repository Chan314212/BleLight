package com.example.blrlight_908_3;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Connection extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private static final String TAG = "Connection";
    Switch ble_switch;
    ImageView ble_state;
    boolean isSelected = false;
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_DISABLE_BT = 2;
    //BluetoothManager bluetoothManager= (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
    //BluetoothAdapter bluetoothAdapter=bluetoothManager.getAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        ble_switch = findViewById(R.id.switch1);
        ble_state = findViewById(R.id.imageView);

        ble_state_init();
//        ble_state_switch();


        ActionBar acb = getSupportActionBar();
        if (acb != null) {
            acb.hide();
        }

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.bluetooth);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
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
        ble_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (isSelected) {
                    if (!bluetoothAdapter.isEnabled()) {
                        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        //startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                        startActivity(enableBtIntent);
//                      bluetoothAdapter.enable();
                    }
                }else {
                    Intent disableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(disableBtIntent, REQUEST_DISABLE_BT);
                    //bluetoothAdapter.disable();

                }
                Log.d(TAG, "onClick:2 ");
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

    public void ble_state_init() {
        if (bluetoothAdapter.isEnabled()) {
            ble_switch.setChecked(true);
            isSelected=true;
            ble_state.setImageResource(R.drawable.ble_on);
        }else ble_state.setImageResource(R.drawable.ble_off);
    }


}
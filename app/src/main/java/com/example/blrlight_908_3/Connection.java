package com.example.blrlight_908_3;

import static android.os.Build.VERSION_CODES.S;
import static androidx.core.app.ActivityCompat.startActivityForResult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;


import com.clj.fastble.BleManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.security.acl.Permission;

public class Connection extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private static final String TAG = "Connection";
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch ble_switch;
    ImageView ble_state;
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private static final int REQUEST_OPEN_BT_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        ble_switch = findViewById(R.id.switch1);
        ble_state = findViewById(R.id.imageView);
        BleManager.getInstance().init(getApplication());
        BleManager.getInstance()
                .enableLog(true)
                .setReConnectCount(1, 5000)
                .setSplitWriteNum(20)
                .setConnectOverTime(10000)
                .setOperateTimeout(5000);


        ble_state_refresh();
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
            @SuppressLint("MissingPermission")
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                BleManager bleManager = new BleManager();
                BluetoothAdapter adapter=BluetoothAdapter.getDefaultAdapter();
                if (ble_switch.isChecked()) {
//                    bleManager.enableBluetooth();

                    if (!adapter.isEnabled()) //未打开蓝牙，才需要打开蓝牙
                    {
                        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(intent, REQUEST_OPEN_BT_CODE);}

                } else {
//                    bleManager.disableBluetooth();
                    //adapter.disable()

                }

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

    public void ble_state_refresh() {
        if (bluetoothAdapter.isEnabled()) {
            ble_switch.setChecked(true);
            ble_state.setImageResource(R.drawable.ble_on);
        } else {
            ble_state.setImageResource(R.drawable.ble_off);
            ble_switch.setChecked(false);
        }
    }


}
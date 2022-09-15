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


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.inuker.bluetooth.library.BluetoothClient;
import com.inuker.bluetooth.library.search.SearchRequest;

import java.security.acl.Permission;

public class Connection extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private static final String TAG = "Connection";
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch ble_switch;
    ImageView ble_state;
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        ble_switch = findViewById(R.id.switch1);
        ble_state = findViewById(R.id.imageView);
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){

        }
        BluetoothClient client=new BluetoothClient(this);
        SearchRequest request = new SearchRequest.Builder()
                .searchBluetoothLeDevice(3000, 3)   // 先扫BLE设备3次，每次3s
                .searchBluetoothClassicDevice(5000) // 再扫经典蓝牙5s
                .searchBluetoothLeDevice(2000)      // 再扫BLE设备2s
                .build();


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
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (ble_switch.isChecked()) {
                    if (!bluetoothAdapter.isEnabled()) {
client.openBluetooth();
                    }
                } else {
                    client.closeBluetooth();

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
package com.example.blrlight_908_3;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Switch;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class BlueTooth_Set {
    private static final String TAG = "Main";
    private final static int REQUEST_ENABLE_BT = 1;

    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private BluetoothStateBroadcastReceive mReceive;

    public BlueTooth_Set() {
        // 获得蓝牙适配器对象
    }

    public boolean getBlueToothState() {
        // 获取蓝牙状态
        return bluetoothAdapter.isEnabled();
    }


    public boolean openBlueTooth() {
        if (getBlueToothState()) return true;
        // 打开蓝牙
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            //startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
//        return bluetoothAdapter.enable();
        return true;
    }


    @SuppressLint("MissingPermission")
    public boolean colseBlueTooth() {
        if (!getBlueToothState()) return true;
        // 关闭蓝牙
        if (bluetoothAdapter == null) {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        return bluetoothAdapter.disable();
    }

    // 调用系统的请求打开蓝牙
    public void gotoSystem(Context context) {
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        context.startActivity(intent);
    }

    public void registerBluetoothReceiver(Context context) {
        if (mReceive == null) {
            mReceive = new BluetoothStateBroadcastReceive();
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        intentFilter.addAction("android.bluetooth.BluetoothAdapter.STATE_OFF");
        intentFilter.addAction("android.bluetooth.BluetoothAdapter.STATE_ON");
        context.registerReceiver(mReceive, intentFilter);
    }

    public void unregisterBluetoothReceiver(Context context) {
        if (mReceive != null) {
            context.unregisterReceiver(mReceive);
            mReceive = null;
        }
    }

    class BluetoothStateBroadcastReceive extends BroadcastReceiver {

        @SuppressLint("MissingPermission")
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            switch (action) {
                case BluetoothDevice.ACTION_ACL_CONNECTED:
                    Toast.makeText(context, "蓝牙设备:" + device.getName() + "已连接", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "onReceive: " + "蓝牙设备:" + device.getName() + "已连接");
                    break;
                case BluetoothDevice.ACTION_ACL_DISCONNECTED:
                    Toast.makeText(context, "蓝牙设备:" + device.getName() + "已断开", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "onReceive: " + "蓝牙设备:" + device.getName() + "已断开");
                    break;
                case BluetoothAdapter.ACTION_STATE_CHANGED:
                    int blueState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
                    switch (blueState) {
                        case BluetoothAdapter.STATE_OFF:
                            Toast.makeText(context, "蓝牙已关闭", Toast.LENGTH_SHORT).show();
                            Log.i(TAG, "onReceive: " + "蓝牙已关闭:");
                            break;
                        case BluetoothAdapter.STATE_ON:
                            Toast.makeText(context, "蓝牙已开启", Toast.LENGTH_SHORT).show();
                            Log.i(TAG, "onReceive: " + "蓝牙已开启:");
                            break;
                    }

                    break;
            }
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String result = data.getExtras().getString("result");//得到新Activity 关闭后返回的数据
        Log.i(TAG, result);}
}

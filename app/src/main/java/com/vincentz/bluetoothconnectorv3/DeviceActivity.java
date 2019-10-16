package com.vincentz.bluetoothconnectorv3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DeviceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        BTDeviceModel btDevice = (BTDeviceModel)bundle.getSerializable("device");

        ((TextView)findViewById(R.id.DeviceName)).setText(btDevice.getName());

    }
}

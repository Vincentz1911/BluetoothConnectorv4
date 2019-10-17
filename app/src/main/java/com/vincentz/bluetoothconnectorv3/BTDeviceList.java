package com.vincentz.bluetoothconnectorv3;

import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

import static android.widget.Toast.makeText;

public class BTDeviceList extends AppCompatActivity {

    public ArrayList<BTDeviceModel> BTDevices = new ArrayList<>();

    private BTDeviceListAdapter mAdapter;

    ListView BTDevices_list;
    Context context;

    BTDeviceList(Context context){
        this.context = context;
        mAdapter = new BTDeviceListAdapter(context, BTDevices);
        BTDevices_list.setAdapter(mAdapter);
        BTDevices_list = findViewById(R.id.BTDevices_list);
    }

    private void UpdateListView() {
        mAdapter.notifyDataSetChanged();
    }

    public void AddToDeviceList(BluetoothDevice device){
        //if device is already in list break
        for (BTDeviceModel bt : BTDevices) {
            if (bt.getAddress().equals(device.getAddress())) return;
        }

        //Checks if Name is null and not already in deviceList
        String deviceName = device.getName();
        if (deviceName == null) deviceName = device.getAddress();
        BTDevices.add(new BTDeviceModel(deviceName, device.getAddress(), device, false, false, 0));
        //makeText(this, "Found device " + device.getName(), Toast.LENGTH_SHORT).show();

        mAdapter.notifyDataSetChanged();
        //UpdateListView();
    }
}

package com.vincentz.bluetoothconnectorv3;

import android.bluetooth.BluetoothDevice;

public class BTDeviceModel {

    private String name;
    private String address;
    private String type;
    private BluetoothDevice device;
    private boolean paired;
    private boolean connected;
    private int mImageDrawable;


    BTDeviceModel(String name, String address, String type, BluetoothDevice device, boolean paired, boolean connected, int mImageDrawable) {
        this.name = name;
        this.address = address;
        this.type = type;
        this.device = device;
        this.paired = paired;
        this.connected = connected;
        this.mImageDrawable = mImageDrawable;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    String getAddress() { return address; }
    public void setAddress(String address) {
        this.address = address;
    }
    String getType() { return type; }
    public void setType(String type) { this.type = type; }
    BluetoothDevice getDevice() { return device; }
    public void setDevice(BluetoothDevice device) {
        this.device = device;
    }
    boolean isPaired() { return paired; }
    public void setPaired(boolean paired) {
        this.paired = paired;
    }
    public boolean isConnected() {
        return connected;
    }
    public void setConnected(boolean connected) {
        this.connected = connected;
    }
    int getmImageDrawable() {return mImageDrawable; }
    public void setmImageDrawable(int mImageDrawable) {
        this.mImageDrawable = mImageDrawable;
    }
}
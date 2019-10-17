package com.vincentz.bluetoothconnectorv3;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;

public class BTDeviceModel {

    private String name;
    private String address;
    private BluetoothDevice device;
    private boolean paired;
    private boolean connected;
    private int mImageDrawable;

    // Constructor that is used to create an instance of the Bluetooth Device object
    public BTDeviceModel(String name, String address, BluetoothDevice device, boolean paired, boolean connected, int mImageDrawable) {
        this.name = name;
        this.address = address;
        this.device = device;
        this.paired = paired;
        this.connected = connected;
        this.mImageDrawable = mImageDrawable;
    }

    protected BTDeviceModel(Parcel in) {
        name = in.readString();
        address = in.readString();
        device = in.readParcelable(BluetoothDevice.class.getClassLoader());
        paired = in.readByte() != 0;
        connected = in.readByte() != 0;
        mImageDrawable = in.readInt();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public BluetoothDevice getDevice() {
        return device;
    }
    public void setDevice(BluetoothDevice device) {
        this.device = device;
    }
    public boolean isPaired() {
        return paired;
    }
    public void setPaired(boolean paired) {
        this.paired = paired;
    }
    public boolean isConnected() {
        return connected;
    }
    public void setConnected(boolean connected) {
        this.connected = connected;
    }
    public int getmImageDrawable() {
        return mImageDrawable;
    }
    public void setmImageDrawable(int mImageDrawable) {
        this.mImageDrawable = mImageDrawable;
    }

}

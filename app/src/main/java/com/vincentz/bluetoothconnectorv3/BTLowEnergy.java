package com.vincentz.bluetoothconnectorv3;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.util.Log;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BTLowEnergy {

    void BluetoothLEScanner(BluetoothAdapter BA){
        BA.getBluetoothLeScanner().startScan(new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);


                Log.d("Results", "onScanResult: " + result.getDevice());
                //new BTDeviceList().AddToDeviceList(result.getDevice());
            }

            @Override
            public void onBatchScanResults(List<ScanResult> results) {
                super.onBatchScanResults(results);
            }

            @Override
            public void onScanFailed(int errorCode) {
                super.onScanFailed(errorCode);
            }
            @Override
            public int hashCode() {
                return super.hashCode();
            }
            @Override
            public boolean equals(@Nullable Object obj) {
                return super.equals(obj);
            }
            @Override
            protected Object clone() throws CloneNotSupportedException { return super.clone(); }
            @NonNull
            @Override
            public String toString() {
                return super.toString();
            }
            @Override
            protected void finalize() throws Throwable { super.finalize(); }
        });
    }
}

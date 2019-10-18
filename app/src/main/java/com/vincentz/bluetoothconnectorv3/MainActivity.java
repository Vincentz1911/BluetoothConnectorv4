package com.vincentz.bluetoothconnectorv3;

import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.widget.*;
import android.view.*;
import android.content.*;
import android.bluetooth.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {

    ListView BTDevices_list;

    private BluetoothAdapter BA;
    private BTDeviceListAdapter mAdapter;

    public ArrayList<BTDeviceModel> BTDevices = new ArrayList<>();
    private boolean hideNoName = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BA.cancelDiscovery();
                BA.startDiscovery();
            }
        });

        InitGUI();
        InitBluetooth();

        //ASKING PERMISSION FOR ACCESSING LOCATION FOR BTLe
        new RequestPermissions(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothDevice.ACTION_PAIRING_REQUEST);

        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        registerReceiver(mReceiver, filter);

        //Search for new BT
        BluetoothLEScanner(BA);
        BA.startDiscovery();
    }

    void BluetoothLEScanner(BluetoothAdapter BA) {
        BA.getBluetoothLeScanner().startScan(new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);
                AddToDeviceList(result.getDevice());
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
            protected Object clone() throws CloneNotSupportedException {
                return super.clone();
            }

            @NonNull
            @Override
            public String toString() {
                return super.toString();
            }

            @Override
            protected void finalize() throws Throwable {
                super.finalize();
            }
        });
    }

    void Connect(String deviceAddress) {
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();

        BluetoothDevice device = btAdapter.getRemoteDevice(deviceAddress);

        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

        BluetoothSocket socket = null;
        try {
            socket = device.createInsecureRfcommSocketToServiceRecord(uuid);
            socket.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String GetType(BluetoothDevice device){
        int majorBtClass = device.getBluetoothClass().getMajorDeviceClass();
        switch (majorBtClass) {
            case BluetoothClass.Device.Major.AUDIO_VIDEO:
                return "Audio/Video";
            case BluetoothClass.Device.Major.COMPUTER:
                return "Computer";
            case BluetoothClass.Device.Major.HEALTH:
                return "Health";
            case BluetoothClass.Device.Major.IMAGING:
                return "Imaging";
            case BluetoothClass.Device.Major.MISC:
                return "Misc";
            case BluetoothClass.Device.Major.NETWORKING:
                return "Networking";
            case BluetoothClass.Device.Major.PERIPHERAL:
                return "Peripheral";
            case BluetoothClass.Device.Major.PHONE:
                return "Phone";
            case BluetoothClass.Device.Major.TOY:
                return "Toy";
            case BluetoothClass.Device.Major.UNCATEGORIZED:
                return "Uncategorized";
            case BluetoothClass.Device.Major.WEARABLE:
                return "Wearable";
            default:
                return "Unknown (" + majorBtClass + ")";
        }
    }

    void PairDevice(final BTDeviceModel device) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Pair bluetooth device?")
                .setMessage("Do you want to pair with " + device.getName())
                .setIcon(R.drawable.ic_action_bluetooth_connected)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                        if (BA.isDiscovering()) {
//                            BA.cancelDiscovery();
//                        }
                        device.getDevice().createBond();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) { }
                }).show();
    }

    void UnpairDevice(final BTDeviceModel device){
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("UnPair bluetooth device?")
                .setMessage("Do you want to unpair with " + device.getName())
                .setIcon(R.drawable.ic_action_bluetooth_unpair)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                        if (BA.isDiscovering()) {
//                            BA.cancelDiscovery();
//                        }

                        BluetoothDevice btDevice = device.getDevice();
                       // Method m = null;
                        try {
                            Method m = btDevice.getClass().getMethod("removeBond", (Class[]) null);
                            m.invoke(btDevice, (Object[]) null);
                        } catch (Exception e) { e.printStackTrace();}
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) { }
                }).show();
    }

    public BluetoothAdapter InitBluetooth() {
        //Checking if have bluetooth
        if (BluetoothAdapter.getDefaultAdapter() == null) {
            makeText(this, "Bluetooth not supported", Toast.LENGTH_LONG).show();
            finish();
        }
        //Enabling bluetooth if not turned on
        BA = BluetoothAdapter.getDefaultAdapter();
        if (!BA.isEnabled()) {
            makeText(this, "Turning on Bluetooth", Toast.LENGTH_LONG).show();
            BA.enable();
        }
        //Naming local bluetooth
        if (BA.getName() == null) getSupportActionBar().setTitle(BA.getAddress());
        else getSupportActionBar().setTitle(BA.getName());

        return BA;
    }

    private void BondedDevices() {
        Set<BluetoothDevice> pairedSet = BA.getBondedDevices();
        for (BluetoothDevice bt : pairedSet) {
            boolean bonded = false;
            boolean connected = false;
            if (bt.getBondState() == 12) bonded = true;

            BTDevices.add(new BTDeviceModel(bt.getName(), bt.getAddress(), BTDeviceType.GetBTDeviceType(bt), bt, bonded, connected, R.drawable.ic_action_bluetooth_paired));
        }
        UpdateListView();
    }

    private void UpdateListView() {
        mAdapter = new BTDeviceListAdapter(this, BTDevices);
        BTDevices_list.setAdapter(mAdapter);
    }

    public void AddToDeviceList(BluetoothDevice device) {
        //if device is already in list break
        for (BTDeviceModel bt : BTDevices) {
            if (bt.getAddress().equals(device.getAddress())) return;
        }

        //Checks if Name is null and not already in deviceList
        String deviceName = device.getName();
        if (deviceName == null) {
            if (hideNoName) return;
            else deviceName = device.getAddress();
        }

        makeText(MainActivity.this, "Found device " + device.getName(), Toast.LENGTH_SHORT).show();
        BTDevices.add(new BTDeviceModel(deviceName, device.getAddress(), BTDeviceType.GetBTDeviceType(device), device, false, false, 0));
        mAdapter.notifyDataSetChanged();
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                //discovery starts, we can show progress dialog or perform other tasks
                findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                makeText(MainActivity.this, "Scanning for bluetooth devices", Toast.LENGTH_LONG).show();
                BTDevices = new ArrayList<>();
                BondedDevices();

            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //discovery finishes, dismis progress dialog

                makeText(MainActivity.this, "Finished scanning", Toast.LENGTH_LONG).show();
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);

            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                AddToDeviceList(device);

            } else if (BluetoothDevice.ACTION_PAIRING_REQUEST.equals(action)) {
                Toast.makeText(MainActivity.this, "Trying to pair with " + device.getName(), Toast.LENGTH_LONG).show();
                setBluetoothPairingPin(device);
                mAdapter.notifyDataSetChanged();
            }
        }
    };

    public void setBluetoothPairingPin(BluetoothDevice device) {
        try {
            byte[] pin = (byte[]) BluetoothDevice.class.getMethod("convertPinToBytes", String.class).invoke(BluetoothDevice.class, "1234");
            Log.d("", "Try to set the PIN");
            Method m = device.getClass().getMethod("setPin", byte[].class);
            m.invoke(device, pin);
            Log.d("", "Success to add the PIN.");
            try {
                device.getClass().getMethod("setPairingConfirmation", boolean.class).invoke(device, true);
                Log.d("", "Success to setPairingConfirmation.");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.e("", e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception e) {
            Log.e("", e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_visible) {
            Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            startActivityForResult(getVisible, 0);
            makeText(MainActivity.this, "Visible for 2 min", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.action_hideNoName) {
            if (hideNoName) {
                hideNoName = false;
                item.setTitle("Hide devices with no name");
                makeText(MainActivity.this, "Showing devices with no name", Toast.LENGTH_SHORT).show();
            } else {
                hideNoName = true;
                item.setTitle("Show devices with no name");
                makeText(MainActivity.this, "Hiding devices with no name", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    private void InitGUI() {
        //Clicking the bluetooth Icon in header turns on and off bluetooth
        ImageView enable_bt = findViewById(R.id.enable_bt);
        enable_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BA.isEnabled()) {
                    BA.disable();
                    // makeText(MainActivity.this, "Turning Blutooth off", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intentOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intentOn, 0);
                    // makeText(MainActivity.this, "Turning Blutooth On", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Clicking item on list
        BTDevices_list = findViewById(R.id.BTDevices_list);
        BTDevices_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BTDeviceModel device = BTDevices.get(position);

                if (device.isPaired()) {

                } else PairDevice(device);
            }
        });

        //LONGPRESS on item on list
        BTDevices_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long l) {
                BTDeviceModel device = BTDevices.get(position);
                if (device.isPaired()) UnpairDevice(device);
                return true;
            }
        });
    }
}

//
//    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//
//            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
//           ... //Device found
//            }
//            else if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
//           ... //Device is now connected
//            }
//            else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
//           ... //Done searching
//            }
//            else if (BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED.equals(action)) {
//           ... //Device is about to disconnect
//            }
//            else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
//           ... //Device has disconnected
//            }
//        }
//    };
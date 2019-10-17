package com.vincentz.bluetoothconnectorv3;

import android.Manifest;
import android.app.AlertDialog;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.*;
import android.view.*;
import android.content.*;
import android.bluetooth.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {

    ListView BTDevices_list;

    private BluetoothAdapter BA;
    private BTDeviceListAdapter mAdapter;

    public ArrayList<BTDeviceModel> BTDevices = new ArrayList<>();

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
                BA.startDiscovery();
            }
        });

        InitGUI();
        InitBluetooth();

        //ASKING PERMISSION FOR ACCESSING LOCATION FOR BTLe
        new RequestPermissions(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        //Scanning BTLe
        new BTLowEnergy().BluetoothLEScanner(BA);

        //Search for new BT
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);

        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        registerReceiver(mReceiver, filter);



        BA.startDiscovery();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_visible) {
            Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            startActivityForResult(getVisible, 0);
            makeText(MainActivity.this, "Visible for 2 min", Toast.LENGTH_SHORT).show();
            return true;
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
                final BTDeviceModel device = BTDevices.get(position);

                if (device.isPaired()) {

                } else {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Pair bluetooth device?")
                            .setMessage("Do you want to pair with " + device.getName())
                            .setIcon(R.drawable.ic_action_bluetooth_connected)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (BA.isDiscovering()) {
                                        BA.cancelDiscovery();
                                    }
                                    device.getDevice().createBond();
                                    mAdapter.notifyDataSetChanged();

                                }
                            })

                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }
            }
        });

        //LONGPRESS on item on list
        BTDevices_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long l) {
                final BTDeviceModel device = BTDevices.get(position);

                if (device.isPaired()) {

                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("UnPair bluetooth device?")
                            .setMessage("Do you want to unpair with " + device.getName())
                            .setIcon(R.drawable.ic_action_bluetooth_unpair)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (BA.isDiscovering()) {
                                        BA.cancelDiscovery();
                                    }

                                    BluetoothDevice btDevice = device.getDevice();
                                    Method m = null;
                                    try {
                                        m = btDevice.getClass().getMethod("removeBond", (Class[]) null);
                                        m.invoke(btDevice, (Object[]) null);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    mAdapter.notifyDataSetChanged();
                                }
                            })

                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }
                return true;
            }
        });

    }


    void Connect(String deviceAddress){
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



    void PairDevice(BTDeviceModel device) {

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

            BTDevices.add(new BTDeviceModel(bt.getName(), bt.getAddress(), bt, bonded, connected, R.drawable.ic_action_bluetooth_paired));
        }
        UpdateListView();
    }

    private void UpdateListView() {
        mAdapter = new BTDeviceListAdapter(this, BTDevices);
        BTDevices_list.setAdapter(mAdapter);
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
        makeText(MainActivity.this, "Found device " + device.getName(), Toast.LENGTH_SHORT).show();

        mAdapter.notifyDataSetChanged();
        //UpdateListView();
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

//                //if device is already in list break
//                for (BTDeviceModel bt : BTDevices) {
//                    if (bt.getAddress().equals(device.getAddress())) return;
//                }
//
//                //Checks if Name is null and not already in deviceList
//                String deviceName = device.getName();
//                if (deviceName == null) deviceName = device.getAddress();
//                BTDevices.add(new BTDeviceModel(deviceName, device.getAddress(), device, false, false, 0));
//                makeText(MainActivity.this, "Found device " + device.getName(), Toast.LENGTH_SHORT).show();
//
//                mAdapter.notifyDataSetChanged();
//                //UpdateListView();
            }

        }
    };
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
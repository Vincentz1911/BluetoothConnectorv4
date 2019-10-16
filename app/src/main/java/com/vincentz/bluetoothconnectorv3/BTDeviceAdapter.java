package com.vincentz.bluetoothconnectorv3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BTDeviceAdapter extends ArrayAdapter<BTDeviceModel> {

        private Context mContext;
        private List<BTDeviceModel> btDeviceModels;

        public BTDeviceAdapter(@NonNull Context context, ArrayList<BTDeviceModel> list) {
            super(context, 0 , list);
            mContext = context;
            btDeviceModels = list;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View listItem = convertView;
            if(listItem == null)
                listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);

            BTDeviceModel currentDevice = btDeviceModels.get(position);

            TextView name = listItem.findViewById(R.id.textView_name);
            name.setText(currentDevice.getName());

            TextView address = listItem.findViewById(R.id.textView_address);
            address.setText(currentDevice.getAddress());

//            TextView status = listItem.findViewById(R.id.textView_status);
//            if (currentDevice.getConnected()) status.setText("Connected");

            ImageView image = listItem.findViewById(R.id.imageView_paired);
            image.setImageResource(currentDevice.getmImageDrawable());

            return listItem;
        }
}

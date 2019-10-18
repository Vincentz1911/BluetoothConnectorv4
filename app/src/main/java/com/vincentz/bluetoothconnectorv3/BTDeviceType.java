package com.vincentz.bluetoothconnectorv3;

import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.widget.Switch;

class BTDeviceType {

    static String GetBTDeviceType(BluetoothDevice device) {
        int deviceClass = device.getBluetoothClass().getDeviceClass();

        switch(deviceClass)
        {
            case BluetoothClass.Device.AUDIO_VIDEO_CAMCORDER: return "CAMCORDER";
            case BluetoothClass.Device.AUDIO_VIDEO_CAR_AUDIO: return "CAR_AUDIO";
            case BluetoothClass.Device.AUDIO_VIDEO_HANDSFREE : return "HANDSFREE";
            case BluetoothClass.Device.AUDIO_VIDEO_HEADPHONES : return "HEADPHONES";
            case BluetoothClass.Device.AUDIO_VIDEO_HIFI_AUDIO : return "HIFI_AUDIO";
            case BluetoothClass.Device.AUDIO_VIDEO_LOUDSPEAKER : return "LOUDSPEAKER";
            case BluetoothClass.Device.AUDIO_VIDEO_MICROPHONE : return "MICROPHONE";
            case BluetoothClass.Device.AUDIO_VIDEO_PORTABLE_AUDIO : return "PORTABLE_AUDIO";
            case BluetoothClass.Device.AUDIO_VIDEO_SET_TOP_BOX : return "SET_TOP_BOX";
            case BluetoothClass.Device.AUDIO_VIDEO_UNCATEGORIZED : return "AUDIO_VIDEO";
            case BluetoothClass.Device.AUDIO_VIDEO_VCR : return "VCR";
            case BluetoothClass.Device.AUDIO_VIDEO_VIDEO_CAMERA : return "VIDEO_CAMERA";
            case BluetoothClass.Device.AUDIO_VIDEO_VIDEO_CONFERENCING : return "VIDEO_CONFERENCING";
            case BluetoothClass.Device.AUDIO_VIDEO_VIDEO_DISPLAY_AND_LOUDSPEAKER : return "DISPLAY_AND_SPEAKER";
            case BluetoothClass.Device.AUDIO_VIDEO_VIDEO_GAMING_TOY : return "VIDEO_GAMING";
            case BluetoothClass.Device.AUDIO_VIDEO_VIDEO_MONITOR : return "VIDEO_MONITOR";
            case BluetoothClass.Device.AUDIO_VIDEO_WEARABLE_HEADSET : return "HEADSET";
            case BluetoothClass.Device.COMPUTER_DESKTOP : return "DESKTOP";
            case BluetoothClass.Device.COMPUTER_HANDHELD_PC_PDA : return "HANDHELD_PC";
            case BluetoothClass.Device.COMPUTER_LAPTOP : return "LAPTOP";
            case BluetoothClass.Device.COMPUTER_PALM_SIZE_PC_PDA : return "PALM_SIZE_PC";
            case BluetoothClass.Device.COMPUTER_SERVER : return "SERVER";
            case BluetoothClass.Device.COMPUTER_UNCATEGORIZED : return "COMPUTER";
            case BluetoothClass.Device.COMPUTER_WEARABLE : return "WEARABLE";
            case BluetoothClass.Device.HEALTH_BLOOD_PRESSURE : return "BLOOD_PRESSURE";
            case BluetoothClass.Device.HEALTH_DATA_DISPLAY : return "HEALTH_DATA_DISPLAY";
            case BluetoothClass.Device.HEALTH_GLUCOSE : return "GLUCOSE";
            case BluetoothClass.Device.HEALTH_PULSE_OXIMETER : return "PULSE_OXIMETER";
            case BluetoothClass.Device.HEALTH_PULSE_RATE : return "PULSE_RATE";
            case BluetoothClass.Device.HEALTH_THERMOMETER : return "THERMOMETER";
            case BluetoothClass.Device.HEALTH_UNCATEGORIZED : return "";
            case BluetoothClass.Device.HEALTH_WEIGHING : return "WEIGHING";
            case BluetoothClass.Device.PHONE_CELLULAR : return "CELLULAR";
            case BluetoothClass.Device.PHONE_CORDLESS : return "CORDLESS";
            case BluetoothClass.Device.PHONE_ISDN : return "ISDN";
            case BluetoothClass.Device.PHONE_MODEM_OR_GATEWAY : return "MODEM_OR_GATEWAY";
            case BluetoothClass.Device.PHONE_SMART : return "SMART";
            case BluetoothClass.Device.PHONE_UNCATEGORIZED : return "PHONE";
            case BluetoothClass.Device.TOY_CONTROLLER : return "TOY_CONTROLLER";
            case BluetoothClass.Device.TOY_DOLL_ACTION_FIGURE : return "DOLL_ACTION_FIGURE";
            case BluetoothClass.Device.TOY_GAME : return "GAME";
            case BluetoothClass.Device.TOY_ROBOT : return "ROBOT";
            case BluetoothClass.Device.TOY_UNCATEGORIZED : return "";
            case BluetoothClass.Device.TOY_VEHICLE : return "TOY_VEHICLE";
            case BluetoothClass.Device.WEARABLE_GLASSES : return "GLASSES";
            case BluetoothClass.Device.WEARABLE_HELMET : return "HELMET";
            case BluetoothClass.Device.WEARABLE_JACKET : return "JACKET";
            case BluetoothClass.Device.WEARABLE_PAGER : return "PAGER";
            case BluetoothClass.Device.WEARABLE_UNCATEGORIZED : return "WEARABLE_UNCATEGORIZED";
            case BluetoothClass.Device.WEARABLE_WRIST_WATCH : return "WRIST_WATCH";
            case BluetoothClass.Device.Major.PERIPHERAL : return "PERIPHERAL";
            case BluetoothClass.Device.Major.UNCATEGORIZED : return "UNCATEGORIZED";

            default:
                return "UNKNOWN (" + device.getBluetoothClass().getMajorDeviceClass() + ")";
        }
    }
}


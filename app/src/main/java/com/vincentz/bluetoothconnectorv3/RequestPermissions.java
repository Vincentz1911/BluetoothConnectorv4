package com.vincentz.bluetoothconnectorv3;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class RequestPermissions extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 0;
    Context context;
    String permission;

    RequestPermissions(Context context, String permission){
        this.context = context;
        this.permission = permission;
        RequestPermission();
    }

    private void RequestPermission() {

        if (ContextCompat.checkSelfPermission(context, permission)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{permission},
                    MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
        }
//            // Permission is not granted
//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission)) {
//                // Show an explanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//            } else {
//                // No explanation needed; request the permission
//
//
//                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
//                // app-defined int constant. The callback method gets the
//                // result of the request.
//            }
//        } else {
//            // Permission has already been granted
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
}

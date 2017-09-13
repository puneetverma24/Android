package com.alchemy.broadcastreceiverxml;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Alchemy on 13-09-2017.
 */

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Power Disconnected", Toast.LENGTH_SHORT).show();
    }
}

package com.alchemy.broadcastreceivercustom;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Alchemy on 13-09-2017.
 */

public class MyReceiverCustom extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Custom Broadcast", Toast.LENGTH_SHORT).show();
    }
}

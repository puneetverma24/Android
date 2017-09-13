package com.alchemy.broadcastreceiverdynamically;

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
        Toast.makeText(context, "Power connected", Toast.LENGTH_SHORT).show();
    }
}

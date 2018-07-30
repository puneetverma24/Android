package in.codebucket.serviceunbound;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
      //  return super.onStartCommand(intent, flags, startId);
        Toast.makeText(this, "Service Started "+Thread.currentThread().getName(), Toast.LENGTH_LONG).show();

       // return START_NOT_STICKY;
        //return START_STICKY;
        return START_REDELIVER_INTENT;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

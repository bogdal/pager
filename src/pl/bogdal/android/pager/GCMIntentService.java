package pl.bogdal.android.pager;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.provider.Settings.Secure;
import com.google.android.gcm.GCMBaseIntentService;
import pl.bogdal.android.pager.activity.DialogActivity;
import pl.bogdal.android.pager.config.Settings;


public class GCMIntentService extends GCMBaseIntentService {
    static final String LOG_TAG = "GCMIntentService";

    public GCMIntentService() {
        super(Settings.GCM_SENDER);
    }

    @Override
    public void onError(Context context, String errorId) {
        Log.d(LOG_TAG, "Messaging registration error: " + errorId);
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        Log.d(LOG_TAG, "Received recoverable error: " + errorId);
        return super.onRecoverableError(context, errorId);
    }

    @Override
    protected void onMessage(Context context, Intent intent) {
        String msg = intent.getExtras().getString( "msg" );

        Intent i = new Intent(context, DialogActivity.class);
        i.putExtra("msg", msg);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    @Override
    public void onRegistered(Context context, String registrationId) {
        Log.d(LOG_TAG, "onRegistered()");
        String deviceID = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        Device.register(deviceID, registrationId);
    }

    @Override
    protected void onUnregistered(Context context, String s) {
        Log.d(LOG_TAG, "onUnregistered()");
        String deviceID = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        Device.unregister(deviceID);
    }
}

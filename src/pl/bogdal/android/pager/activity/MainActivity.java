package pl.bogdal.android.pager.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gcm.GCMRegistrar;
import pl.bogdal.android.pager.R;
import pl.bogdal.android.pager.config.Settings;

public class MainActivity extends Activity {

    static final String LOG_TAG = "GCMMainActivity";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        GCMRegistrar.checkDevice(this);
        GCMRegistrar.checkManifest(this);

        final String regId = GCMRegistrar.getRegistrationId(this);

        if (regId.equals("")) {
            GCMRegistrar.register(this, Settings.GCM_SENDER);
        } else {
            Log.v(LOG_TAG, "Already registered");
            GCMRegistrar.unregister(this);
        }
    }
}

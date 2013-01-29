package pl.bogdal.android.pager.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gcm.GCMRegistrar;
import pl.bogdal.android.pager.R;
import pl.bogdal.android.pager.config.Settings;

public class MainActivity extends Activity {

    protected String regId;
    private Context context = null;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        context =  getApplicationContext();

        GCMRegistrar.checkDevice(this);
        GCMRegistrar.checkManifest(this);

        regId = GCMRegistrar.getRegistrationId(context);

        Button serviceButton = (Button) findViewById(R.id.bService);
        serviceButton.setOnClickListener(buttonListener);

        if (regId.equals("")) {
            serviceButton.setText("Register device");
        } else {
            serviceButton.setText("Unregister device");
        }
    }

    private final OnClickListener buttonListener = new OnClickListener() {
        public void onClick(View v) {
        if (regId.equals("")) {
            GCMRegistrar.register(context, Settings.GCM_SENDER);
            showMessage("Device was registered successfully");
        } else {
            GCMRegistrar.unregister(context);
            showMessage("Device was unregistered successfully ");
        }

        finish();
        }
    };

    private void showMessage(String message) {
        Toast myToast;

        myToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        myToast.show();
    }
}

package pl.bogdal.android.pager.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;
import pl.bogdal.android.pager.R;

public class DialogActivity extends Activity {
    
	TextView tvMsg;
	TextView tvTitle;
		
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog);

        getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        
        tvMsg = (TextView)findViewById(R.id.tvMsg);
        tvTitle = (TextView)findViewById(R.id.tvTitle);
        
        tvTitle.setText("GCM");
        
        Bundle extras = getIntent().getExtras();
        String msg = extras.getString("msg");
        tvMsg.setText(msg);
    }
}

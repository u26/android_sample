package jp.mknod.app.container;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class SettingActivity extends AppCompatActivity {

	Button bt_service;
	boolean st_service = false;
	BoxlensService mBoxlensService;
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		intent = new Intent(this, BoxlensService.class);

		bt_service = (Button)findViewById(R.id.bt_service);
		bt_service.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (st_service == false) {
					startForegroundService(intent);
				} else {

					if( mBoxlensService != null ){
						unbindService(mConnection);
						mBoxlensService = null;
					}
					stopService(intent);
				}
				refreshStatus();

			}
		});

	}

	ServiceConnection mConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName className, IBinder service) {
			Log.i("ServiceConnection", "onServiceConnected");
			mBoxlensService = ((BoxlensService.BoxlensServiceBinder) service).getService();
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			Log.i("ServiceConnection", "onServiceDisconnected");
		}
	};


	@Override
	protected void onResume(){
		super.onResume();

		refreshStatus();
	}

	@Override
	protected void onPause() {
		super.onPause();

		if( mBoxlensService != null ){
			unbindService(mConnection);
			mBoxlensService = null;
		}
	}

	void refreshStatus(){

		ActivityManager am = (ActivityManager)this.getSystemService(ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> listServiceInfo = am.getRunningServices(Integer.MAX_VALUE);

		for (ActivityManager.RunningServiceInfo curr : listServiceInfo) {
			if (curr.service.getClassName().equals(BoxlensService.class.getName())) {
				bt_service.setText("OFF");
				st_service = true;

				bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
				return;
			}
		}
		bt_service.setText("ON");
		st_service = false;
	}
}
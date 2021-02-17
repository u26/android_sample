package jp.mknod.app.sms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import jp.mknod.app.sms.sms.SMSBroadcastReceiver;
import jp.mknod.app.sms.sms.SMSUtil;

public class MainActivity extends AppCompatActivity {

	SMSBroadcastReceiver receiver = null;
	IntentFilter intentFilter;

	TextView tx_sms;
	Button bt_send;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getRuntimePermissions();

		tx_sms = (TextView)findViewById(R.id.tx_sms);
		bt_send = (Button)findViewById(R.id.bt_send);
		bt_send.setOnClickListener(v -> {
			SMSUtil.send(
				"09039392978",
				"xxxx0001,A-san,202002141123,35.5948047,139.3444435,START,3.25");
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(receiver);
	}

	@Override
	protected void onResume() {
		super.onResume();
		receiver = new SMSBroadcastReceiver();
		intentFilter = new IntentFilter();
		intentFilter.addAction(SMSUtil.ACTION_SMS_RECEIVED);
		registerReceiver(receiver, intentFilter);
	}

	class ReceivedBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {

			String s = intent.getStringExtra("txt");
			tx_sms.setText(s);

			String data[] = s.split(",",0);


			Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
		}
	}



/*
 * PERMISSION
 */
	private static final int PERMISSION_REQUESTS = 1;
	private String[] getRequiredPermissions() {
		try {
			PackageInfo info =
				this.getPackageManager()
					.getPackageInfo(this.getPackageName(), PackageManager.GET_PERMISSIONS);
			String[] ps = info.requestedPermissions;
			if (ps != null && ps.length > 0) {
				return ps;
			} else {
				return new String[0];
			}
		} catch (Exception e) {
			return new String[0];
		}
	}

	private boolean allPermissionsGranted() {
		for (String permission : getRequiredPermissions()) {
			if (!isPermissionGranted(this, permission)) {
				return false;
			}
		}
		return true;
	}

	private void getRuntimePermissions() {
		List<String> allNeededPermissions = new ArrayList<>();
		for (String permission : getRequiredPermissions()) {
			if (!isPermissionGranted(this, permission)) {
				allNeededPermissions.add(permission);
			}
		}

		if (!allNeededPermissions.isEmpty()) {
			ActivityCompat.requestPermissions(
				this, allNeededPermissions.toArray(new String[0]), PERMISSION_REQUESTS);
		}
	}

	@Override
	public void onRequestPermissionsResult(
		int requestCode, String[] permissions, int[] grantResults) {
		if (allPermissionsGranted()) {

			/* user action */
		}
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}

	private static boolean isPermissionGranted(Context context, String permission) {
		if (ContextCompat.checkSelfPermission(context, permission)
			== PackageManager.PERMISSION_GRANTED) {
			return true;
		}
		return false;
	}

}
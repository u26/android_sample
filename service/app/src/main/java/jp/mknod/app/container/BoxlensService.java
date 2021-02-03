package jp.mknod.app.container;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class BoxlensService  extends Service{

	final static String TAG = "GwService";
	protected  final IBinder mBinder = new BoxlensServiceBinder();
	BoxlensService mGwService;

	public class BoxlensServiceBinder extends Binder {

		public BoxlensService getService(){
			return BoxlensService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate");
		mGwService = this;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "onStartCommand");

		String channelId = "service";
		String title = "CONTAINER";

		// 通知設定
		NotificationManager notificationManager =
			(NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
		NotificationChannel channel =
			new NotificationChannel(channelId, title, NotificationManager.IMPORTANCE_DEFAULT);

		if(notificationManager != null) {

			notificationManager.createNotificationChannel(channel);
			Notification notification = new Notification.Builder(getApplicationContext(), channelId)
				.setContentTitle(title)
				.setSmallIcon(R.drawable.notice)
				.setContentText("Get Temperature data from Boxlens.")
				.build();

			// フォアグラウンドで実行
			startForeground(1, notification);
		}

		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

	}
}

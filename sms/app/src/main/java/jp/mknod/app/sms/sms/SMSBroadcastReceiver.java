package jp.mknod.app.sms.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SMSBroadcastReceiver extends BroadcastReceiver {

	private static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";

	@Override
	public void onReceive(Context context, Intent intent) {

			Bundle bundle = intent.getExtras();
			SmsMessage[] msgs = null;

			if (bundle == null) {
				return;
			}



//			StringBuilder sb = new StringBuilder();
//			Object[] pdus = (Object[]) bundle.get("pdus");
//			String formt = (String) bundle.get("format");
//
//			msgs = new SmsMessage[pdus.length];
//			for (int i = 0; i < msgs.length; i++) {
//
//				msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i], formt);
//
//				sb.append("--------" + i + "--------\n");
//				String s = msgs[i].getOriginatingAddress();
//				sb.append("OriginatingAddress: " +
//					msgs[i].getOriginatingAddress() + "\n");
//				sb.append("MessageBody: " +
//					msgs[i].getMessageBody() + "\n");
//			}

			// Send Broadcast
			Intent broadcastIntent = new Intent();
			broadcastIntent.putExtra("txt", sb.toString());
			broadcastIntent.setAction(SMSUtil.ACTION_SMS_RECEIVED);
			context.sendBroadcast(broadcastIntent);
		}
}

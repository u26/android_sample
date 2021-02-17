package jp.mknod.app.sms.sms;

import android.telephony.SmsManager;

public class SMSUtil {

	public final static String ACTION_SMS_RECEIVED = "jp.mknod.app.container.GwService.action_sms_received";

	public static void send(String tel_number, String msg_text) {

		SmsManager smsManager = SmsManager.getDefault();
		smsManager.sendTextMessage(
			tel_number,
			null,
			msg_text
			, null, null);
	}
}

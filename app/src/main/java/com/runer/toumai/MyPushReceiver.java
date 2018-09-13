package com.runer.toumai;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.orhanobut.logger.Logger;
import com.runer.toumai.base.ToumaiApplication;
import com.runer.toumai.bean.HomeMessageBean;
import com.runer.toumai.ui.activity.MessageActivity;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;


import cn.jpush.android.api.JPushInterface;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * 自定义接收器
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 * 加上自定义的消息铃声；
 */
public class MyPushReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";
	private static final int NOTIFICATION_SHOW_SHOW_AT_MOST = 3;   //推送通知最多显示条数
	@Override
	public void onReceive(Context context, Intent intent) {
		//playSound(context);
		EventBus.getDefault().post(new HomeMessageBean());
		Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " );

		if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
		Intent	action =new Intent(context, MessageActivity.class) ;
		Bundle  data =new Bundle() ;
		action.putExtras(data);
		action.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		action.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
		context.startActivity(action);
		Bundle bundle =intent.getExtras();
		processCustomMessage(context, bundle);
	}else if(intent.getAction().equals(JPushInterface.ACTION_MESSAGE_RECEIVED)||intent.getAction().equals(JPushInterface.ACTION_NOTIFICATION_RECEIVED)){
		Log.i(TAG, "接收到了消息");
		Bundle bundle =intent.getExtras();
		String message =bundle.getString(JPushInterface.EXTRA_MESSAGE);
		processCustomMessage(context, bundle);
		Log.i(TAG, "接收到的消息是:【"+message+"】");
	}
	}

	private static Ringtone mRingtone;
	public synchronized void playSound(Context context) {

		if (mRingtone == null) {
			Logger.d("----------初始化铃声----------");
			String uri = "android.resource://" + context.getPackageName() + "/" + R.raw.music;
			Uri no = Uri.parse(uri);
			mRingtone = RingtoneManager.getRingtone(context.getApplicationContext(), no);
		}
		if (!mRingtone.isPlaying()) {
			Logger.d("--------------播放铃声---------------" + mRingtone.isPlaying());
			mRingtone.play();
		}
	}

	/**
	 * 实现自定义推送声音
	 * @param context
	 * @param bundle
	 */
	private void processCustomMessage(Context context, Bundle bundle) {
		NotificationCompat.Builder notification = new NotificationCompat.Builder(context);

		String title = bundle.getString(JPushInterface.EXTRA_TITLE);
		String msg = bundle.getString(JPushInterface.EXTRA_MESSAGE);
		String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo);

		Intent mIntent = new Intent(context,MessageActivity.class);
		mIntent.putExtras(bundle);
		mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mIntent, 0);

		notification.setContentIntent(pendingIntent)
				.setAutoCancel(true)
				.setContentText(msg)
				.setContentTitle("投买网")
				.setSmallIcon(R.drawable.logo)
				.setLargeIcon(bitmap)
				.setNumber(NOTIFICATION_SHOW_SHOW_AT_MOST);

		notification.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" +R.raw.music));


		NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
		notificationManager.notify(NOTIFICATION_SHOW_SHOW_AT_MOST, notification.build());  //id随意，正好使用定义的常量做id，0除外，0为默认的Notification
	}
}

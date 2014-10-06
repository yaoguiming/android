package cn.why.notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NotifactionActivity extends Activity {
	private EditText shorttitleText;
    private EditText titleText;
    private EditText contentText;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        shorttitleText = (EditText) this.findViewById(R.id.shorttitle);
        titleText = (EditText) this.findViewById(R.id.title);
        contentText = (EditText) this.findViewById(R.id.content);
    }
    /**
     * 发送短信、在状态栏进行通知
     * @param v
     */
    public void send(View v){
    	String tickerText = shorttitleText.getText().toString();
    	String title = titleText.getText().toString();
    	String content = contentText.getText().toString();
    	int icon = android.R.drawable.stat_notify_chat;//系统自带的图标
    	Notification notification = new Notification(icon, tickerText, System.currentTimeMillis());
    	Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:194949494"));
    	PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 10, intent, 0);
    	notification.setLatestEventInfo(getApplicationContext(), title, content, pendingIntent);
    	notification.defaults = Notification.DEFAULT_SOUND;//设置通知到声音
    	notification.flags = Notification.FLAG_AUTO_CANCEL;//设置读完通知，图标自动消失
    	NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    	manager.notify(100,notification);
    	
    }
}
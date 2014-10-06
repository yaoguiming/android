package cn.why.mp3player;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import cn.why.model.Mp3Info;
import cn.why.mp3player.service.PlayerService;

public class PlayerActivity extends Activity {
	ImageButton beginButton = null;
	ImageButton pauseButton = null;
	ImageButton stopButton = null;
	MediaPlayer mediaPlayer = null;

	private TextView lrcTextView = null;
	private Mp3Info mp3Info = null;
	private boolean isPlaying = false;
	private IntentFilter intentFilter=null;
	private BroadcastReceiver receiver=null;
	
	protected void onPause() {
		super.onPause();
		unregisterReceiver(receiver);
	}

	protected void onResume() {
		super.onResume();
		receiver=new LrcMessageBroadcastReceiver();
		registerReceiver(receiver,getIntentFilter());
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.player);
		Intent intent = getIntent();
		mp3Info = (Mp3Info) intent.getSerializableExtra("mp3Info");
		beginButton = (ImageButton) findViewById(R.id.begin);
		pauseButton = (ImageButton) findViewById(R.id.pause);
		stopButton = (ImageButton) findViewById(R.id.stop);
		beginButton.setOnClickListener(new BeginButtonListener());
		pauseButton.setOnClickListener(new PauseButtonListener());
		stopButton.setOnClickListener(new StopButtonListener());
		lrcTextView = (TextView)findViewById(R.id.lrcText);
	}


	class BeginButtonListener implements OnClickListener {
		public void onClick(View v) {
			//创建一个Intent对象，用于同时Service开始播放MP3
			Intent intent = new Intent();
			intent.setClass(PlayerActivity.this, PlayerService.class);
			intent.putExtra("mp3Info", mp3Info);
			intent.putExtra("MSG", AppConstant.PlayerMsg.PLAY_MSG);
			startService(intent);
		}
	}

	class PauseButtonListener implements OnClickListener {
		public void onClick(View v) {
			//通知Service暂停播放MP3
			Intent intent = new Intent();
			intent.setClass(PlayerActivity.this, PlayerService.class);
			intent.putExtra("MSG", AppConstant.PlayerMsg.PAUSE_MSG);
			startService(intent);
		}
	}

	class StopButtonListener implements OnClickListener {
		public void onClick(View v) {
			//通知Service停止播放MP3文件
			Intent intent = new Intent();
			intent.setClass(PlayerActivity.this, PlayerService.class);
			intent.putExtra("MSG", AppConstant.PlayerMsg.STOP_MSG);
			startService(intent);
		}
	}
	
    private IntentFilter getIntentFilter(){
    	if(intentFilter==null){
    		intentFilter=new IntentFilter();
    		intentFilter.addAction(AppConstant.LRC_MESSAGE_ACTION);
    	}
    	return intentFilter;
    }
    /*
     * 广播接收器，主要作用是接受service所发送的广播，并且更新ui，也就是放置歌词的TextView
     */
    class LrcMessageBroadcastReceiver extends BroadcastReceiver{
		public void onReceive(Context context, Intent intent) {
    		String lrcMessage=intent.getStringExtra("lrcMessage");
    		lrcTextView.setText(lrcMessage);
		}
    }
}

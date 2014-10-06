package cn.why.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HandlerTestActivity extends Activity {
	private Button button1;
	private Button button2;
	Handler handler = new Handler();
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        button1 = (Button) this.findViewById(R.id.button1);
        button2 = (Button) this.findViewById(R.id.button2);
        button1.setOnClickListener(new StartButtonListener());
        button2.setOnClickListener(new EndButtonListener());
    }
    
    Runnable thread = new Runnable() {
		public void run() {
			System.out.println("thread");
			handler.postDelayed(thread, 3000);
		}
	};
	/**
	 * 匿名内部类实现监听事件
	 * @author Administrator
	 */
    class StartButtonListener implements OnClickListener{
		public void onClick(View v) {
			handler.post(thread);
		}
    }
    
    class EndButtonListener implements OnClickListener{
		public void onClick(View v) {
			handler.removeCallbacks(thread);
		}
    }
}
package cn.why.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
/**
 * 1、同线程内不同组件间的消息传递
 * Looper类用来管理特定线程内对象之间的消息交换(Message Exchange)。
 * 你的应用程序可以产生许多个线程。而一个线程可以有许多个组件，这些组件之间常常需要互相交换讯息。
 * 如果有这种需要，您可以替线程构造一个Looper对象，来担任讯息交换的管理工作。
 * Looper对象会建立一个MessageQueue数据结构来存放各对象传来的消息(包括UI事件或System事件等)
 * @author Administrator
 */
public class HandlerActivity extends Activity implements Runnable{
	private static final int my_key=0x123;
    private int length=0;
    //图片数组
    private int[] myImage={
            R.drawable.aa,
            R.drawable.bb,
            R.drawable.cc,
            R.drawable.dd
    };
    private Handler myHandler;
    private Thread myThread; 
    private ImageView myImageView;
    private Animation myAnimationAlpha;
	private Animation myAnimationScale;
	private Animation myAnimationTranslate;
	private Animation myAnimationRotate;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        myImageView=(ImageView)findViewById(R.id.image); 
        myAnimationAlpha=new AlphaAnimation(0.1f, 1.0f);
        myAnimationAlpha.setDuration(3000);
        myAnimationScale=new ScaleAnimation(0.0f, 1.4f, 0.0f, 1.4f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        myAnimationScale.setDuration(3000);
        
        myAnimationTranslate=new TranslateAnimation(30.0f, -80.0f, 30.0f, 300.0f);
        myAnimationTranslate.setDuration(3000);
        
       myAnimationRotate=new RotateAnimation(0.0f, +350.0f,
               Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
       myAnimationRotate.setDuration(3000);
       // myLinearLayout.setBackgroundColor(Color.WHITE);

    //用于消息传递   
    myHandler=new Handler()
    {
        public void handleMessage(Message msg) {
            switch (msg.what) {
				case HandlerActivity.my_key:
					switch (length) {
						case 0:
							myImageView.startAnimation(myAnimationAlpha);
							break;
						case 1:
							myImageView.startAnimation(myAnimationScale);
							break;
						case 2:
							myImageView.startAnimation(myAnimationTranslate);
							break;
						case 3:
							myImageView.startAnimation(myAnimationRotate);
							break;
						default:
							break;
					}
					myImageView.setImageResource(myImage[length - 1]);
					myImageView.setScaleType(ImageView.ScaleType.FIT_XY);
					// myImageView.setLayoutParams(new LayoutParams(300, 200));

					if (length == myImage.length) {
						length = 0;
					}
					break;
            default:
                break;
            }
            super.handleMessage(msg);
        }
    };
    
    myThread=new Thread(this);
    myThread.start();
}
	public void run() {
		try {
            do
            {
                length++;
               Thread.sleep(4000);
                Message msg=new Message();
                msg.what=HandlerActivity.my_key;
                myHandler.sendMessage(msg);
            }
            while(Thread.interrupted()==false);
       } catch (Exception e) {
           e.printStackTrace();
       }
	}
}
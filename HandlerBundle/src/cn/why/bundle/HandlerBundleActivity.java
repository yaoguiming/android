package cn.why.bundle;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

public class HandlerBundleActivity extends Activity {
	Bundle bundle = new Bundle();
	//生成一个HandlerThread对象，实现了使用Looper来处理消息队列的功能，这个类由Android应用程序框架提供
	HandlerThread handlerThread = new HandlerThread("handler_thread");
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        System.out.println("Main----->"+Thread.currentThread().getId());
        System.out.println("Main----->"+Thread.currentThread().getName());
        handlerThread.start();
      //在使用HandlerThread的getLooper()方法之前，必须先调用该类的start();
        MyHandler myHandler = new MyHandler(handlerThread.getLooper());
        Message msg = myHandler.obtainMessage();
//        myHandler.handleMessage(msg);
      //将msg发送到目标对象，所谓的目标对象，就是生成该msg对象的handler对象
        bundle.putInt("age", 1);
        bundle.putString("name", "www");
        msg.setData(bundle);
        msg.sendToTarget();
    }
    
    class MyHandler extends Handler{
    	public MyHandler() {	}
    	public MyHandler(Looper looper) {
    		super(looper);
		}
		public void handleMessage(Message msg) {
			System.out.println("MyHandler----->" + Thread.currentThread().getId());
			System.out.println("MyHandler----->" + Thread.currentThread().getName());
			System.out.println("handlerMessage");
			Bundle bundle = msg.getData();
			int age = bundle.getInt("age");
			String name = bundle.getString("name");
			System.out.println("age is " + age + ", name is " + name);
		}
    }
    
}
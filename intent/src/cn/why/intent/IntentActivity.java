package cn.why.intent;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class IntentActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    /**
     * 使用显式意图打开activity
     * @param v
     */
    public void openActivity(View v){
    	//打开新的Activity
    	Intent intent = new Intent(this, OtherActivity.class);//激活组件,显示意图:明确指定了组件名称的意图叫显示意图
    	//关于指定组件名称，还有很多写法
    	//1> intent.setClass(this, OtherActivity.class);//指定要激活的组件名称
    	//2> intent.setClassName(this, "cn.itcast.activitys.OtherActivity");
    	//3> intent.setComponent(new ComponentName(this, OtherActivity.class));
    	//4>Intent intent = new Intent(this, OtherActivity.class);
    	
    	//参数传递
    	//第一中方法
    	/*
    	intent.putExtra("company", "传智播客");
    	intent.putExtra("age", 5);
    	*/
    	//第二种参数传递的方法
    	Bundle bundle = new Bundle();
    	bundle.putString("company", "CSDN");
    	bundle.putInt("age", 11);
    	intent.putExtras(bundle);
    	
//        startActivity(intent);
    	startActivityForResult(intent, 100);
    }	
    /**
     * 使用隐式意图打开activity
     * @param v
     */
    public void openIntentActivity(View v){
    	/**
    	 * (没设数据参数的情况下)只要Intent中的Action和Category都出现在Intent-Filter中，就能与之匹配，否则匹配失败
    	 */
    	Intent intent = new Intent();
    	intent.setAction("cn.why.intent");
    	intent.addCategory("cn.why.intent.name");
//    	intent.setData(Uri.parse("why://www.why.cn/why"));
    	intent.setDataAndType(Uri.parse("why://www.why.cn/why"), "image/jpeg");
    	startActivity(intent);//方法内部为Intent添加了android.intent.category.DEFAULT类别
    }
    
    /**
     * 当另一个activity返回数据给这个IntentActivity时，调用这个方法
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	String result = data.getStringExtra("result");
		Toast.makeText(getApplicationContext(),result, 1).show();
    	super.onActivityResult(requestCode, resultCode, data);
    }
}
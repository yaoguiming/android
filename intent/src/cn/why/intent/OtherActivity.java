package cn.why.intent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class OtherActivity extends Activity{

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.other);
		Intent intent = getIntent();//获取意图对象
		//取得参数的第一种方法
//		String param = intent.getStringExtra("param");//获取字符串类型参数
//		int a = intent.getIntExtra("int", 0);//获取整数类型参数
		//取得参数的第二种方法
		Bundle bundle = intent.getExtras();
		String company = bundle.getString("company");
		int age = bundle.getInt("age");
		
		Toast.makeText(getApplicationContext(), company+age, 1).show();
	}
	/**
	 * 关闭activity，返回数据给IntentActivity
	 */
	public void closeActivity(View v) {
		Intent intent = new Intent();
		intent.putExtra("result", "老方与丽丽的故事，后面省略2000字");
		setResult(20, intent);//设置返回数据
		finish();//关闭Activity(关闭窗口)
	}
}

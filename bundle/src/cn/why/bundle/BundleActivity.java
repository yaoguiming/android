package cn.why.bundle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class BundleActivity extends Activity {

	private OnClickListener listener;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button button = (Button) this.findViewById(R.id.forward);
        Button alphaButton = (Button) this.findViewById(R.id.alpha);
        Button scaleButton = (Button) this.findViewById(R.id.scale);
        Button rotateButton = (Button) this.findViewById(R.id.rotate);
        Button translateButton = (Button) this.findViewById(R.id.translate);
        
      //使用xml文件的动画
		Animation alpha = AnimationUtils.loadAnimation(
				BundleActivity.this, R.anim.alpha);
		Animation scale = AnimationUtils.loadAnimation(
				BundleActivity.this, R.anim.scale);
		Animation rotate = AnimationUtils.loadAnimation(
				BundleActivity.this, R.anim.rotate);
		Animation translate = AnimationUtils.loadAnimation(
				BundleActivity.this, R.anim.translate);
		//启动动画
		alphaButton.startAnimation(alpha);
		scaleButton.startAnimation(scale);
		rotateButton.startAnimation(rotate);
		translateButton.startAnimation(translate);
		
        listener = new OnClickListener() {
			public void onClick(View v) {
				//实现页面跳转
				Intent intent=new Intent();
				intent.setClass(BundleActivity.this, ResultActivity.class);
				Bundle bun=new Bundle();
				bun.putBoolean("boolean", true);
				bun.putInt("int", 1);
				bun.putChar("char", 'c');
				bun.putString("Str", "abcd");
				intent.putExtras(bun);//设置参数，用于传递给其他页面
//				intent.putExtra("bun", bun);//设置参数，用于传递给其他页面
//				intent.putExtra("param", "why");//设置参数，用于传递给其他页面
				startActivity(intent);
				BundleActivity.this.finish();
			}
		};
		button.setOnClickListener(listener);
    }
}
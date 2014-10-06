package cn.why.spiner;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SpinnerActivity extends Activity {
	private Spinner spinner;
	private TextView textview;
	private Button addButton;
	private Button removeButton;
	private EditText editText;
	private ArrayAdapter<String> adapter; 
	ArrayList<String> list=new ArrayList<String>();
	private SoundPool pool;
	private int click;
	private static final String bloodGroup[]={"A型","B型","O型","AB型","其他"};
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        textview = (TextView)findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText);
        addButton = (Button) findViewById(R.id.addButton);
        removeButton = (Button) findViewById(R.id.removeButton);
        spinner=(Spinner)findViewById(R.id.spinner);
        
        pool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        //加载音效
    	click = pool.load(this, R.raw.about, 1);
        //设置下拉框的数据源
//        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bloodGroup);
      //获取相应对象
        String[] ls=getResources().getStringArray(R.array.cities);
        //获取XML中定义的数组
        for(int i=0;i<ls.length;i++){
        	list.add(ls[i]);
        }
        //把数组导入到ArrayList中
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
        //设置下拉框弹出样式，使用安卓系统定义的样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		//设置标题栏
		spinner.setPrompt("标题栏");
		//设置下拉框点击事件监听
		spinner.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			public void onItemSelected(AdapterView<?> arg0, View arg1,	int arg2, long arg3) {
				textview.setText("您的选择的是："+list.get(arg2));
			}

			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		
		spinner.setOnTouchListener(new OnTouchListener() {
			Animation scale = AnimationUtils.loadAnimation(SpinnerActivity.this, R.anim.scale);
            public boolean onTouch(View v, MotionEvent event) {
                v.startAnimation(scale);
                return false;
            }
        });
		
		//添加下拉选项按钮监听
		addButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	//获取文本输入值
                String item=editText.getText().toString();
				for (int i = 0; i < adapter.getCount(); i++) {
					if (item.equals(adapter.getItem(i))) {
						Toast.makeText(getApplicationContext(), "存在相同的值", 1)
								.show();
						return;
					}
				}
				if (item.length() > 0) {
					Toast.makeText(getApplicationContext(), "不存在相同的值", 1).show();
					adapter.add(item);
					int position = adapter.getPosition(item);
					spinner.setSelection(position);
					//在标题输出添加后list的大小
					setTitle(String.valueOf(list.size()));
				}
				//播放音效
				playSound();
            }
        });
		//移除下拉选项按钮监听
		removeButton.setOnClickListener(new OnClickListener(){//删除按钮监听器
			public void onClick(View v) {
						 if(spinner.getSelectedItem().toString()!=adapter.getItem(0))
		                 {
							new AlertDialog.Builder(SpinnerActivity.this)
									.setTitle("提示")
									.setMessage("确定删除吗？")
									.setPositiveButton(
											"确定",
											new DialogInterface.OnClickListener() {
												public void onClick(DialogInterface dialog,int which) {
													adapter.remove(spinner.getSelectedItem()	.toString());
												}
											}).setNeutralButton("取消", null)
									.show();
		                 }
		                 if(adapter.getCount()==1)
		                 {
		                	 Toast.makeText(getApplicationContext(), "已经没有可以删除的数据了", 1)
								.show();
		                 }
		                 // 删除当前选中项,remove后自动调用notifyDataSetChanged()
//						adapter.remove(spinner.getSelectedItem().toString());
						// 在标题输出添加后list的大小
						setTitle(String.valueOf(list.size()));
					}
			});
    }

    /**
     * 播放音效
     */
    private void playSound(){
    	pool.play(click, 1, 1, 0, 0, 1);
    }
    /**
     * 返回退出程序
     */
	@Override
	public void onBackPressed() {
		showDialog();
	}
    /**
     * 显示dialog提示框
     */
	public void showDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("温馨提示");
		builder.setMessage("确定退出？");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				SpinnerActivity.this.finish();
			}
		});
		
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		
	}
}
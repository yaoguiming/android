package cn.why.listviewpage;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import cn.why.adapter.ApkAdapter;
import cn.why.domian.ApkEntity;
import cn.why.listviewpage.LoadListView.ILoadListener;

public class MainActivity extends Activity implements ILoadListener {

	private LoadListView listView;
	private List<ApkEntity> apkList = new ArrayList<ApkEntity>();

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (LoadListView) findViewById(R.id.listview);
        listView.setInterface(this);
        
        apkList = getData();
        ApkAdapter apkAdapter = new ApkAdapter(this, apkList);
        listView.setAdapter(apkAdapter);
    }

	private List<ApkEntity> getData() {
		for (int i = 0; i < 10; i++) {
			 ApkEntity entity = new ApkEntity();
			 entity.setName("测试程序");
			 entity.setInfo("50w用户");
			 entity.setDes("这是一个神奇的应用！");
			 apkList.add(entity);
		}
		return apkList;
	}
	
	private List<ApkEntity> getLoadData() {
		for (int i = 0; i < 2; i++) {
			 ApkEntity entity = new ApkEntity();
			 entity.setName("更多程序");
			 entity.setInfo("50w用户");
			 entity.setDes("这是一个神奇的应用！");
			 apkList.add(entity);
		}
		return apkList;
	}
	private List<ApkEntity> getReflashData() {
		for (int i = 0; i < 2; i++) {
			 ApkEntity entity = new ApkEntity();
			 entity.setName("刷新程序");
			 entity.setInfo("50w用户");
			 entity.setDes("这是一个神奇的应用！");
//			 apkList.add(entity);
			 apkList.add(0,entity);
		}
		return apkList;
	}

	/**
	 * 上滑加载更多
	 */
	public void onLoad() {

		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				//获取更多数据
				apkList = getLoadData();
				//通知更新listview界面
		        ApkAdapter apkAdapter = new ApkAdapter(MainActivity.this, apkList);
		        listView.setAdapter(apkAdapter);
		        apkAdapter.notifyDataSetChanged();
		        listView.loadComplete();
			}
		}, 2000);
	}

	/**
	 * 下拉刷新
	 */
	public void onReflash() {
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				//获取最新数据
				//获取更多数据
				apkList = getReflashData();
				//通知更新listview界面
		        ApkAdapter apkAdapter = new ApkAdapter(MainActivity.this, apkList);
		        listView.setAdapter(apkAdapter);
		        apkAdapter.notifyDataSetChanged();
				//通知listview 刷新数据完毕；
		        listView.reflashComplete();
			}
		}, 2000);
	}
	
	
}

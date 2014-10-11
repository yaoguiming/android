package cn.why.listviewdemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity implements OnScrollListener, OnItemClickListener {

	private ListView listView;
	private ArrayAdapter<String> arrayAdapter;
	private SimpleAdapter simpleAdapter;
	private List<Map<String, Object>> dataList = null;
	int i = 0;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        listView = (ListView) findViewById(R.id.listView);
        //创建适配器，并为之添加数据源
//        String[] array = new String[]{"why1","why2","why3","why4","why5","why6"};
        //第一个参数的意思是上下文，第二个参数的意思是每一个listview项对应的布局文件，第三个参数是数据源（字符串数组）
//        arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, array);
        //为listView视图添加载适配器
//        listView.setAdapter(arrayAdapter);
        dataList = new ArrayList<Map<String,Object>>();
        /**
         * context：上下文
         * data：数据源 List<? extends Map<String, ?>> data，map对象所组成的list集合
         * 每个map都会去对应ListView列表中的一行
         * 每个map中的键必须包含所有在from中指定的键
         * resource：列表项对应的布局文件的ID
         * from：map中的键名
         * to：绑定数据视图的Id
         */
        simpleAdapter = new SimpleAdapter(MainActivity.this, getData(), R.layout.list_item, 
        		new String[]{"image", "text"}, new int[]{R.id.image, R.id.text});
        listView.setAdapter(simpleAdapter);
        
        listView.setOnItemClickListener(this);//设置listView项点击监听
        listView.setOnScrollListener(this);//设置listview滚动监听
    }
    /**
     * simpleAdapter的数据源
     * @return
     */
    private List<Map<String, Object>> getData(){
    	for (int i = 0; i < 20; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image", R.drawable.ic_launcher);
			map.put("text", "why"+i);
			dataList.add(map);
		}
    	return dataList;
    }
	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		
	}
	@Override
	public void onScrollStateChanged(AbsListView arg0, int scrollState) {

		switch (scrollState) {
		case SCROLL_STATE_FLING:
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("text", "滚动添加 "+i++);
			map.put("image", R.drawable.ic_launcher);
			dataList.add(map);
			listView.setAdapter(simpleAdapter);
			simpleAdapter.notifyDataSetChanged();
			break;

		case SCROLL_STATE_IDLE:
			break;
		case SCROLL_STATE_TOUCH_SCROLL:
			break;
		}
		// 手指离开屏幕前，用力滑了一下
//		if (scrollState == SCROLL_STATE_FLING) {
//			Toast.makeText(MainActivity.this, "用力滑一下", 0).show();
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("text", "滚动添加 " + i++);
//			map.put("image", R.drawable.ic_launcher);
//			dataList.add(map);
//			listView.setAdapter(simpleAdapter);
//			simpleAdapter.notifyDataSetChanged();
//		} else
//		// 停止滚动
//		if (scrollState == SCROLL_STATE_IDLE) {
//
//		} else
//		// 正在滚动
//		if (scrollState == SCROLL_STATE_TOUCH_SCROLL) {
//
//		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// 获取点击ListView item中的内容信息
				String text = listView.getItemAtPosition(position) + "";
				// 弹出Toast信息显示点击位置和内容
				Toast.makeText(MainActivity.this,
						"position=" + position + " content=" + text, 0).show();
	}
}

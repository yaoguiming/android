package cn.why.spinnerdiy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * 自定义下拉列表的风格
 * @author Administrator
 *
 */
public class MainActivity extends Activity implements OnItemSelectedListener{

	private TextView textView;
	private Spinner spinner;
	private ArrayAdapter<String> arrayAdapter;
	private SimpleAdapter simpleAdapter;
	private List<Map<String, Object>> dataList;
	private List<String> list;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     // 第一步：添加一个下拉列表项的list，这里添加的项就是下拉列表的菜单项
        textView = (TextView) findViewById(R.id.textView);
        spinner = (Spinner) findViewById(R.id.spinner);
        dataList = new ArrayList<Map<String,Object>>();
        list = new ArrayList<String>();
        list.add("北京");
		list.add("上海");
		list.add("广州");
		list.add("深圳");
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);//设置适配器
        
        
     // 第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list
        simpleAdapter = new SimpleAdapter(this, getData(), R.layout.item_layout, new String[]{"image", "text"}, new int[]{R.id.image, R.id.text});
//     // 第三步：为适配器设置下拉列表下拉时的菜单样式。
//        simpleAdapter.setDropDownViewResource(R.layout.item_layout);
//     // 第四步：将适配器添加到下拉列表上
//        spinner.setAdapter(simpleAdapter);//设置适配器
//     // 第五步：为下拉列表设置各种事件的响应，这个事响应菜单被选中
        spinner.setOnItemSelectedListener(this);//添加监听
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
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
//		textView.setText("您选择的是："+simpleAdapter.getItem(arg2));
		textView.setText("您选择的是："+arrayAdapter.getItem(arg2));
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
	}
}

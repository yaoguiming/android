package cn.why.listview;

import java.util.ArrayList;
import java.util.HashMap;

import cn.why.listviwe.R;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ListViewActivity extends ListActivity {
	ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
	HashMap<String, String> map1 = new HashMap<String, String>();
	HashMap<String, String> map2 = new HashMap<String, String>();
	HashMap<String, String> map3 = new HashMap<String, String>();

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        map1.put("user_name", "zhangsan");
    	map1.put("user_ip", "192.168.0.1");
    	map2.put("user_name", "zhangsan");
    	map2.put("user_ip", "192.168.0.2");
    	map3.put("user_name", "wangwu");
    	map3.put("user_ip", "192.168.0.3");
    	list.add(map1);
    	list.add(map2);
    	list.add(map3);
    	SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.user, 
    			new String[] { "user_name", "user_ip" }, new int[] { R.id.user_name,R.id.user_ip});
    	setListAdapter(adapter);
    }

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		System.out.println("id----------------" + id);
		System.out.println("position----------" + position);
	}

	
}
package cn.why.mp3player;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import cn.why.download.HttpDownloader;
import cn.why.model.Mp3Info;
import cn.why.mp3player.service.DownloadService;
import cn.why.xml.Mp3ListContentHandler;

public class Mp3ListActivity extends ListActivity {
	private static final int UPDATE = 1;
	private static final int ABOUT = 2;
	private List<Mp3Info> mp3Infos = null;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remote_mp3_list);
        updateListView();
    }
    /**
     * 在用户点击menu按钮之后调用该方法，我们可以在该方法中加入自己的按钮控件
     */
	public boolean onCreateOptionsMenu(Menu menu) {
		//添加菜单选项按钮
		menu.add(0, UPDATE, 1, R.string.mpelist_update);
		menu.add(0, ABOUT, 1, R.string.mp3list_about);
		
		return super.onCreateOptionsMenu(menu);
	}
	/**
	 * 菜单选项按钮点击事件
	 */
	public boolean onOptionsItemSelected(MenuItem item) {
		System.out.println("item-id--->"+item.getItemId());
		if (item.getItemId() == UPDATE) {
			//用户点击了更新选项按钮
			updateListView();
		} else if (item.getItemId() == ABOUT) {
			// 用户点击了关于选项按钮
			Toast.makeText(getApplicationContext(), "WHY", 1).show();
		}
		return super.onOptionsItemSelected(item);
	}
	/**
	 * 下载XML文件
	 * @param urlStr
	 * @return String xml文件
	 */
	private String downloadXML(String urlStr) {
		HttpDownloader httpDownloader = new HttpDownloader();
		String xml = httpDownloader.download(urlStr);
		return xml;
	}
    /**
     * 更新音乐列表
     */
	private void updateListView() {
		// 用户点击了更新列表按钮
		// 下载包含所有Mp3基本信息的xml文件
		String xml = downloadXML("http://169.254.68.73:8080/mp3/resources.xml");
		// 对xml文件进行解析，并将解析的结果放置到Mp3Info对象当中，最后将这些Mp3Info对象放置到List当中
		mp3Infos = parse(xml);
		SimpleAdapter simpleAdapter = buildSimpleAdapter(mp3Infos);
		setListAdapter(simpleAdapter);
	}
	/**
	 * 解析xml文件
	 * @param xmlStr
	 * @return
	 */
	private List<Mp3Info> parse(String xmlStr) {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		List<Mp3Info> infos = new ArrayList<Mp3Info>();
		try {
			XMLReader xmlReader = saxParserFactory.newSAXParser()
					.getXMLReader();
			Mp3ListContentHandler mp3ListContentHandler = new Mp3ListContentHandler(
					infos);
			xmlReader.setContentHandler(mp3ListContentHandler);
			xmlReader.parse(new InputSource(new StringReader(xmlStr)));
			for (Iterator iterator = infos.iterator(); iterator.hasNext();) {
				Mp3Info mp3Info = (Mp3Info) iterator.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return infos;
	}
	private SimpleAdapter buildSimpleAdapter(List<Mp3Info> mp3Infos) {
		// 生成一个List对象，并按照SimpleAdapter的标准，将mp3Infos当中的数据添加到List当中去
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		for (Iterator iterator = mp3Infos.iterator(); iterator.hasNext();) {
			Mp3Info mp3Info = (Mp3Info) iterator.next();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("mp3_name", mp3Info.getMp3Name());
			map.put("mp3_size", mp3Info.getMp3Size());
			list.add(map);
		}
		// 创建一个SimpleAdapter对象
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, list,
				R.layout.mp3info_item, new String[] { "mp3_name", "mp3_size" },
				new int[] { R.id.mp3_name, R.id.mp3_size });
		// 将这个SimpleAdapter对象设置到ListActivity当中
		return simpleAdapter;
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Mp3Info mp3Info = mp3Infos.get(position);
		// 生成Intent对象
		Intent intent = new Intent();
		// 将Mp3Info对象存入到Intent对象当中
		intent.putExtra("mp3Info", mp3Info);
		intent.setClass(this, DownloadService.class);
		// 启动Service
		startService(intent);
		super.onListItemClick(l, v, position, id);
	}
	
}
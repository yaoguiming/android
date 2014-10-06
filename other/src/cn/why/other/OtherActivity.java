package cn.why.other;

import android.app.Activity;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class OtherActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Uri uri = Uri.parse("content://cn.why.providers.personprovider/person");
        //监听内容提供者变化，注册内容监听
        this.getContentResolver().registerContentObserver(uri, true, new PersonContentObserver(new Handler()));
    }
    /**
     * person数据内容监听器
     * @author Administrator
     */
    private class PersonContentObserver extends ContentObserver{

		public PersonContentObserver(Handler handler) {
			super(handler);
		}

		@Override
		public void onChange(boolean selfChange) {
			// select * from person order by personid desc limit 1
			Uri uri = Uri.parse("content://cn.why.providers.personprovider/person");
			Cursor cursor = getContentResolver().query(uri, null, null, null, "personid desc limit 1");
			if(cursor.moveToFirst()){
				String name = cursor.getString(cursor.getColumnIndex("name"));
				Log.i("OtherActivity", name);
			}
		}
    	
    }
}
package cn.why.newsmanage;

import java.io.File;

import cn.why.service.NewsService;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewsmanageActivity extends Activity {
	private EditText titleText;
    private EditText lengthText;
    private EditText nameText;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        titleText = (EditText) this.findViewById(R.id.title);
        lengthText = (EditText) this.findViewById(R.id.timelength);
        nameText = (EditText) this.findViewById(R.id.filename);
    }
    /**
     *±£´æ
     * @param v
     */
    public void save(View v){
    	String title = titleText.getText().toString();
    	String length = lengthText.getText().toString();
    	boolean result = NewsService.save(title, length);
    	if(result){
    		Toast.makeText(getApplicationContext(), R.string.success, 1).show();
    	}else{
    		Toast.makeText(getApplicationContext(), R.string.error, 1).show();
    	}
    }
}
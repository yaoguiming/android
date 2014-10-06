package cn.why.json;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.jsonutils.R;


public class MainActivity extends Activity {
	
	private String jsonData1 = "[{\"name\":\"json\",\"age\":12},{\"name\":\"why\",\"age\":15}]";
	private String jsonData2 = "{\"name\":\"json\",\"age\":12}";
	
	private Button button ;
	
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new ButtonListener());
    }
    
    private class ButtonListener implements OnClickListener {

		public void onClick(View v) {
			JsonUtils jsonUtils = new JsonUtils();
//			jsonUtils.parseJsonArray(jsonData1);
//			jsonUtils.parseJsonObjectToUser(jsonData2);
			jsonUtils.parseJsonArrayToUsers(jsonData1);
		}
    	
    }

}

package cn.why.file;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.why.R;
import cn.why.service.FileService;

public class FileActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button button =(Button) this.findViewById(R.id.button);
        button.setOnClickListener(new ButtonClickListener());
    }
    /**
     * 类不需要被继承，使用final
     * @author Administrator
     *
     */
    private final class ButtonClickListener implements View.OnClickListener{

		public void onClick(View v) {
			EditText fileNameText = (EditText) findViewById(R.id.filename);
			EditText  fileContentText= (EditText) findViewById(R.id.filecontent);
			String fileName = fileNameText.getText().toString();
			String fileContent = fileContentText.getText().toString();
			FileService service = new FileService(getApplicationContext());
			try {
				//判断sdcard是否存在，是否可读写
				if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
//					service.save(fileName,fileContent);
					//写入sdcard
					service.saveToSDCard(fileName,fileContent);
					Toast.makeText(getApplicationContext(), R.string.success, 1).show();
				}
			} catch (Exception e) {
//				Toast.makeText(getApplicationContext(), R.string.fail, 1).show();
				Toast.makeText(getApplicationContext(), R.string.sdcardError, 1).show();
				e.printStackTrace();
			}
		}
    	
    }
}
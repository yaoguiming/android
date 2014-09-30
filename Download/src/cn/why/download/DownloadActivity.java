package cn.why.download;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cn.why.utils.HttpDownloader;

public class DownloadActivity extends Activity {
	private Button downloadTxtButton;
	private Button downloadMp3Button;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        downloadTxtButton = (Button)findViewById(R.id.downloadTxt);
        downloadTxtButton.setOnClickListener(new DownloadTxtListener());
        downloadMp3Button = (Button)findViewById(R.id.downloadMp3);
        downloadMp3Button.setOnClickListener(new DownloadMp3Listener());
    }
    class DownloadTxtListener implements OnClickListener{

		public void onClick(View v) {
			HttpDownloader httpDownloader = new HttpDownloader();
			String lrc = httpDownloader.download("http://192.168.191.1:8080/mp3/aa.lrc");
			System.out.println(lrc);
		}
    	
    }
    class DownloadMp3Listener implements OnClickListener{

		public void onClick(View v) {
			HttpDownloader httpDownloader = new HttpDownloader();
			int result = httpDownloader.downFile("http://192.168.191.1:8080/mp3/aa.mp3", "mp3/", "aa.mp3");
			System.out.println(result);
		}
    	
    }
}
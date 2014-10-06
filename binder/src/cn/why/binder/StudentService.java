package cn.why.binder;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class StudentService extends Service{
	private String[] names = {"ÕÅ·É","ÀîÐ¡Áú","ÕÔÞ±"};
	private IBinder binder = new StudentBinder();
	
	public String query(int no){
		if(no>0 && no<4){
			return names[no - 1];
		}
		return null;
	}
	
	public IBinder onBind(Intent intent) {
		return binder;
	}

	private final class StudentBinder extends Binder implements IStundent {
		public String queryStudent(int no) {
			return query(no);
		}
		
		
	}
}

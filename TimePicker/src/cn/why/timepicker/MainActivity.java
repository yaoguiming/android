package cn.why.timepicker;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

/**
 * 测试TimePicker和DatePicker使用
 * @author Administrator
 */
public class MainActivity extends Activity {

	private TimePicker timePicker;
	private DatePicker datePicker;
	private Calendar calendar;
	private int year, month, day, hour, minute, second;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
       
        calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));//设置时区，否则小时会少8小时
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;//月份需要加1，才是当前月份
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        second = calendar.get(Calendar.SECOND);
        setTitle(year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second);
        //日历选择器，必须使用init方法进行初始化使用
        datePicker.init(year, Calendar.MONTH, Calendar.DAY_OF_MONTH, new OnDateChangedListener() {
			
			public void onDateChanged(DatePicker arg0, int year, int month, int day) {
				setTitle(year+"-"+(month+1)+"-"+day+" "+hour+":"+minute);
			}
		});
        //时间选择器可以直接使用setOnTimeChangedListener进行初始化使用
        timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {
			
			public void onTimeChanged(TimePicker arg0, int hourOfDay, int minute) {
				setTitle(year+"-"+month+"-"+day+" "+hourOfDay+":"+minute);
			}
		});
        //日历选择器对话框
//        new DatePickerDialog(this, new OnDateSetListener() {
//			public void onDateSet(DatePicker arg0, int year, int month, int day) {
//				setTitle(year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second);
//			}
//		}, year, (month+1), day).show();
        //时间选择器对话框
        new TimePickerDialog(this, new OnTimeSetListener() {
			
			@Override
			public void onTimeSet(TimePicker arg0, int hourOfDay, int minute) {
				setTitle(year+"-"+month+"-"+day+" "+hourOfDay+":"+minute);
			}
		}, hour, minute, true).show();
        
    }
    
}

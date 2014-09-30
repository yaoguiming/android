package cn.why.caculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CaculatorproActivity extends Activity {
	// 数字键
	private Button zero;
	private Button one;
	private Button two;
	private Button three;
	private Button four;
	private Button five;
	private Button six;
	private Button seven;
	private Button eight;
	private Button nine;
	
	private String input;
	private String temp = "";
	private Float tempResult;
	private String operation = "flag";//+-*/
	
	//操作运算符
	private Button add;
	private Button minus;
	private Button multiply;
	private Button equal;
	
	private TextView textView;
	private OnClickListener listener;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        zero = (Button) this.findViewById(R.id.zero);
        one = (Button) this.findViewById(R.id.one);
        two = (Button) this.findViewById(R.id.two);
        three = (Button) this.findViewById(R.id.three);
        four = (Button) this.findViewById(R.id.four);
        five = (Button) this.findViewById(R.id.five);
        six = (Button) this.findViewById(R.id.six);
        seven = (Button) this.findViewById(R.id.seven);
        eight = (Button) this.findViewById(R.id.eight);
        nine = (Button) this.findViewById(R.id.nine);
        //输入、结果显示区域
        textView = (TextView) this.findViewById(R.id.textView);
        
        add = (Button) this.findViewById(R.id.add);
        minus = (Button) this.findViewById(R.id.minus);
        multiply = (Button) this.findViewById(R.id.multiply);
        equal = (Button) this.findViewById(R.id.equal);
        
        listener= new OnClickListener(){
			public void onClick(View v) {
				if(v == zero){
					input = "0";
					temp = temp+input;
					textView.setText(temp);
				}
				if(v == one){
					input = "1";
					temp = temp+input;
					textView.setText(temp);
				}
				if(v == two){
					input = "2";
					temp = temp+input;
					textView.setText(temp);
				}
				if(v == three){
					input = "3";
					temp = temp+input;
					textView.setText(temp);
				}
				if(v == four){
					input = "4";
					temp = temp+input;
					textView.setText(temp);
				}
				if(v == five){
					input = "5";
					temp = temp+input;
					textView.setText(temp);
				}
				if(v == six){
					input = "6";
					temp = temp+input;
					textView.setText(temp);
				}
				if(v == seven){
					input = "7";
					temp = temp+input;
					textView.setText(temp);
				}
				if(v == eight){
					input = "8";
					temp = temp+input;
					textView.setText(temp);
				}
				if(v == nine){
					input = "9";
					temp = temp+input;
					textView.setText(temp);
				}
			}
        };
        //设置监听
        zero.setOnClickListener(listener);
        one.setOnClickListener(listener);
        two.setOnClickListener(listener);
        three.setOnClickListener(listener);
        four.setOnClickListener(listener);
        five.setOnClickListener(listener);
        six.setOnClickListener(listener);
        seven.setOnClickListener(listener);
        eight.setOnClickListener(listener);
        nine.setOnClickListener(listener);
        //加法运算
        add.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
				if(operation == "flag"){
					tempResult = Float.parseFloat(textView.getText().toString().trim());
					temp = "";
				}else{
					equal();
					tempResult = Float.parseFloat(textView.getText().toString().trim());
					temp = "";
				}
				operation = "+";
			}
        });
        //减法运算
        minus.setOnClickListener(new OnClickListener(){
        	public void onClick(View arg0) {
        		if(operation == "flag"){
					tempResult = Float.parseFloat(textView.getText().toString().trim());
					temp = "";
				}else{
					equal();
					tempResult = Float.parseFloat(textView.getText().toString().trim());
					temp = "";
				}
				operation = "-";
        	}
        });
        //乘法运算
        multiply.setOnClickListener(new OnClickListener(){
        	public void onClick(View arg0) {
        		if(operation == "flag"){
					tempResult = Float.parseFloat(textView.getText().toString().trim());
					temp = "";
				}else{
					equal();
					tempResult = Float.parseFloat(textView.getText().toString().trim());
					temp = "";
				}
				operation = "*";
        	}
        });
        //计算结果
        equal.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				if(operation == "+"){
					Float result = Float.parseFloat(textView.getText().toString().trim())+tempResult;
					textView.setText(result.toString());
				}else
				if(operation == "-"){
					Float result = tempResult-Float.parseFloat(textView.getText().toString().trim());
					textView.setText(result.toString());
				}else
				if(operation == "*"){
					Float result = Float.parseFloat(textView.getText().toString().trim())*tempResult;
					textView.setText(result.toString());
				}
				operation = "flag";
				temp = "";
			}
        });
    }
	/**
	 * 计算结果
	 */
	private void equal(){
		if(operation == "+"){
			Float result = Float.parseFloat(textView.getText().toString().trim())+tempResult;
			textView.setText(result.toString());
		}else
		if(operation == "-"){
			Float result = tempResult-Float.parseFloat(textView.getText().toString().trim());
			textView.setText(result.toString());
		}else
		if(operation == "*"){
			Float result = Float.parseFloat(textView.getText().toString().trim())*tempResult;
			textView.setText(result.toString());
		}
		operation = "flag";
		temp = "";
	}
}
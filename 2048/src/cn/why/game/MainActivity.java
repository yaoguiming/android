package cn.why.game;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView tvScore;
	private int score = 0;
	private static MainActivity mainActivity = null;
	
	public MainActivity() {
		mainActivity = this;
	}
	
	public static MainActivity getMainActivity() {
		return mainActivity;
	}
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        tvScore = (TextView) findViewById(R.id.tvScore);
        tvScore.setGravity(Gravity.CENTER);
    }
    /**
     * 清零
     */
    public void clearScore(){
		score = 0;
		showScore();
	}
    /**
     * 显示得分
     */
    public void showScore(){
		tvScore.setText(score+"");
	}
    /**
     * 加分数
     * @param s
     */
    public void addScore(int s){
		score+=s;
		showScore();
	}
}
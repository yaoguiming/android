package cn.why.listviewpage;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoadListView extends ListView implements OnScrollListener{
	
	private View footer;//底部布局
	private View header;//顶部布局
	private int headerHeight;// 顶部布局文件的高度；
	private int firstVisibleItem;// 当前第一个可见的item的位置；
	private int scrollState;// listview 当前滚动状态；
	private boolean isRemark;// 标记，当前是在listview最顶端摁下的；
	private int startY;// 摁下时的Y值；

	private int state;// 当前的状态；
	private final int NONE = 0;// 正常状态；
	private final int PULL = 1;// 提示下拉状态；
	private final int RELESE = 2;// 提示释放状态；
	private final int REFLASHING = 3;// 刷新状态；
	
	private int lastVisibleItem;//最后一个可见的item
	private int totalItemCount;//总共的item数量
	private boolean isLoading;// 正在加载；
	private ILoadListener iLoadListener;

	public LoadListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	public LoadListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public LoadListView(Context context) {
		super(context);
		initView(context);
	}

	/**
	 * 添加底部加载提示布局到listview
	 */
	private void initView(Context context){
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		//获取底部布局
		footer = layoutInflater.inflate(R.layout.footer_layout, null);
		//获取顶部布局
		header = layoutInflater.inflate(R.layout.header_layout, null);
		//设置底部布局默认不显示，当上滑到底部的时候显示
		footer.findViewById(R.id.load_layout).setVisibility(View.GONE);
		//设置顶部布局默认不显示，当从顶部下滑的时候显示
//		header.findViewById(R.id.head_layout).setVisibility(View.GONE);
		this.addFooterView(footer);
		measureView(header);
		headerHeight = header.getMeasuredHeight();
		topPadding(-headerHeight);
		this.addHeaderView(header);
		this.setOnScrollListener(this);//设置滚动监听
	}

	/**
	 * 通知父布局，占用的宽，高；此处是“估计”headView的width以及height  
	 * @param view
	 */
	private void measureView(View view) {
		ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
		if (layoutParams == null) {
			layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,	ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int width = ViewGroup.getChildMeasureSpec(0, 0, layoutParams.width);
		int height;
		int tempHeight = layoutParams.height;
		if (tempHeight > 0) {
			height = MeasureSpec.makeMeasureSpec(tempHeight, MeasureSpec.EXACTLY);
		} else {
			height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
		view.measure(width, height);
	}

	/**
	 * 设置header布局上边距；
	 * @param topPadding
	 */
	private void topPadding(int topPadding) {
		header.setPadding(header.getPaddingLeft(), topPadding, header.getPaddingRight(), header.getPaddingBottom());
		header.invalidate();// 重绘一下 
	}
	
	@Override
	public void onScroll(AbsListView arg0, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		this.firstVisibleItem = firstVisibleItem;
		this.lastVisibleItem = firstVisibleItem + visibleItemCount;
		this.totalItemCount = totalItemCount;
	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int scrollState) {
		this.scrollState = scrollState;
		if(scrollState==SCROLL_STATE_IDLE&&lastVisibleItem==totalItemCount){
			if (!isLoading) {
				isLoading = true;
				footer.findViewById(R.id.load_layout).setVisibility(View.VISIBLE);//显示底部布局
				//加载更多
				iLoadListener.onLoad();
			}
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (firstVisibleItem == 0) {
				isRemark = true;
				startY = (int) ev.getY();
			}
			break;

		case MotionEvent.ACTION_MOVE:
			onMove(ev);
			break;
		case MotionEvent.ACTION_UP:
			if (state == RELESE) {
				state = REFLASHING;
				// 加载最新数据；
				reflashViewByState();
				iLoadListener.onReflash();
			} else if (state == PULL) {
				state = NONE;
				isRemark = false;
				reflashViewByState();
			}
			break;
		}
		return super.onTouchEvent(ev);
	}/**
	 * 判断移动过程操作；
	 * 
	 * @param ev
	 */
	private void onMove(MotionEvent ev) {
		if (!isRemark) {
			return;
		}
		int tempY = (int) ev.getY();
		int space = tempY - startY;
		int topPadding = space - headerHeight;
		switch (state) {
		case NONE:
			if (space > 0) {
				state = PULL;
				reflashViewByState();
			}
			break;
		case PULL:
			topPadding(topPadding);
			if (space > headerHeight + 30
					&& scrollState == SCROLL_STATE_TOUCH_SCROLL) {
				state = RELESE;
				reflashViewByState();
			}
			break;
		case RELESE:
			topPadding(topPadding);
			if (space < headerHeight + 30) {
				state = PULL;
				reflashViewByState();
			} else if (space <= 0) {
				state = NONE;
				isRemark = false;
				reflashViewByState();
			}
			break;
		}
	}
	
	/**
	 * 根据当前状态，改变界面显示；
	 */
	private void reflashViewByState() {
		TextView tip = (TextView) header.findViewById(R.id.tip);
		ImageView arrow = (ImageView) header.findViewById(R.id.arrow);
		ProgressBar progress = (ProgressBar) header.findViewById(R.id.progress);
		RotateAnimation anim = new RotateAnimation(0, 180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		anim.setDuration(500);
		anim.setFillAfter(true);
		RotateAnimation anim1 = new RotateAnimation(180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		anim1.setDuration(500);
		anim1.setFillAfter(true);
		switch (state) {
		case NONE:
			arrow.clearAnimation();
			topPadding(-headerHeight);
			break;

		case PULL:
			arrow.setVisibility(View.VISIBLE);
			progress.setVisibility(View.GONE);
			tip.setText("下拉可以刷新！");
			arrow.clearAnimation();
			arrow.setAnimation(anim1);
			break;
		case RELESE:
			arrow.setVisibility(View.VISIBLE);
			progress.setVisibility(View.GONE);
			tip.setText("松开可以刷新！");
			arrow.clearAnimation();
			arrow.setAnimation(anim);
			break;
		case REFLASHING:
			topPadding(50);
			arrow.setVisibility(View.GONE);
			progress.setVisibility(View.VISIBLE);
			tip.setText("正在刷新...");
			arrow.clearAnimation();
			break;
		}
	}
	
	/**
	 * 获取完数据；
	 */
	public void reflashComplete() {
		state = NONE;
		isRemark = false;
		reflashViewByState();
		TextView lastupdatetime = (TextView) header
				.findViewById(R.id.lastupdate_time);
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		String time = format.format(date);
		lastupdatetime.setText(time);
	}
	
	/**
	 * 加载更多数据的回调接口
	 * @author Administrator
	 */
	public interface ILoadListener{
		public void onLoad();

		public void onReflash();
	}
	
	public void setInterface(ILoadListener iLoadListener){
		this.iLoadListener = iLoadListener;
	}
	
	/**
	 * 加载完毕
	 */
	public void loadComplete(){
		isLoading = false;
		footer.findViewById(R.id.load_layout).setVisibility(View.GONE);//加载完毕，隐藏底部布局
	}
}

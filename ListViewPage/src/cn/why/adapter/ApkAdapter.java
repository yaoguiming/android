package cn.why.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.why.domian.ApkEntity;
import cn.why.listviewpage.R;
/**
 * 自定义适配器
 * @author Administrator
 */
public class ApkAdapter extends BaseAdapter {

	private Context context;
	private List<ApkEntity> apkList;
	
	public ApkAdapter(Context context, List<ApkEntity> apkList) {
		this.context = context;
		this.apkList = apkList;
	}
	@Override
	public int getCount() {
		return apkList.size();
	}

	@Override
	public Object getItem(int position) {
		return apkList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View contentView, ViewGroup group) {
		ApkEntity apk = new ApkEntity();
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		//实例获取item控件
		contentView = layoutInflater.inflate(R.layout.item_layout, null);
		//为该控件，关联好布局并填充数据
		if (contentView != null) {
			ImageView apkImage = (ImageView) contentView.findViewById(R.id.apkImage);
			TextView apkName = (TextView) contentView.findViewById(R.id.apkName);
			TextView apkInfo = (TextView) contentView.findViewById(R.id.apkInfo);
			TextView apkDes = (TextView) contentView.findViewById(R.id.apkDes);
			
			apkName.setText(apkList.get(position).getName());
			apkInfo.setText(apkList.get(position).getInfo());
			apkDes.setText(apkList.get(position).getDes());
			
		}
		return contentView;
	}

}

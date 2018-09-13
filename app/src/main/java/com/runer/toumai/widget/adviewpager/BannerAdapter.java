package com.runer.toumai.widget.adviewpager;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.runer.toumai.R;
import com.runer.toumai.base.BaseActivity;
import com.runer.toumai.base.BaseWebAcitivity;
import com.runer.toumai.net.NetConfig;
import com.runer.toumai.widget.RatioImageView;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Pcdd
 * Create   2017/3/15 13:30;
 * https://github.com/szhua
 *
 * @author sz.hua
 */
public final class BannerAdapter extends PagerAdapter  {

	double ratio ;
	private List<BannerBean> data;
	public void setRatio(double ratio){
		this.ratio =ratio ;
	}

public void setData(List<BannerBean> data){
	this.data=data;
	notifyDataSetChanged();
}

	private Context context;

	public BannerAdapter(Context context){
		this.context =context ;
	}
	@Override
	public int getCount() {
		return data==null?0:data.size();
	}
	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}
	@Override
	public Object instantiateItem(final ViewGroup container, final int position) {

		View view =View.inflate(container.getContext(), R.layout.banner_layout, null);
		RatioImageView imageView =(RatioImageView) view.findViewById(R.id.img);
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		container.addView(view);
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(container.getContext(), BaseWebAcitivity.class);
				intent.putExtra("url", data.get(position).getLink());
				intent.putExtra("title", data.get(position).getTitle());
				container.getContext().startActivity(intent);
 			}
		});
	Picasso.with(context).load(NetConfig.ADV_IMG+data.get(position).getImg()).placeholder(R.drawable.banner_loading).into(imageView);
	return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}
}

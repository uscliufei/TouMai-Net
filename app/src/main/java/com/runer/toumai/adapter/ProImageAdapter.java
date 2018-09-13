package com.runer.toumai.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.runer.toumai.R;
import com.runer.toumai.bean.ImgsBean;
import com.runer.toumai.net.NetConfig;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by szhua on 2017/8/31/031.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * ProImageAdapter
 * 商品图片
 */
public class ProImageAdapter extends PagerAdapter {
    private  List<ImgsBean> paths ;
    private Context mContext ;
    public ProImageAdapter (List<ImgsBean> paths , Context context ){
            this.paths =paths ;
            this.mContext =context ;
    }
    @Override
    public int getCount() {
        return paths ==null?0:paths.size();
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView =new ImageView(mContext) ;
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        Picasso.with(mContext).load(NetConfig.GOODS_IMG+paths.get(position).getImg()).placeholder(R.drawable.empty_img).into(imageView);
        container.addView(imageView);
        return imageView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       // super.destroyItem(container, position, object);
        container.removeView((View) object);
    }
}

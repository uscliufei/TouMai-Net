package com.runer.toumai.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.runer.liabary.util.UiUtil;
import com.runer.liabary.widget.NoScrollGridView;
import com.runer.toumai.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by szhua on 2017/7/19/019.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * SelectPhotoView
 * 选择图片View;
 */

public class SelectPhotoView extends FrameLayout {

    private List<String> currentPathList ;
    private SelectPhotoAdapter selectPhotoAdapter ;
    public SelectPhotoView(Context context) {
        this(context,null,0);
    }
    public SelectPhotoView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }
    public SelectPhotoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.select_photo_view,this) ;
        NoScrollGridView dataListView = (NoScrollGridView) findViewById(R.id.recycler_view);
        selectPhotoAdapter =new SelectPhotoAdapter();
        selectPhotoAdapter.setPath(currentPathList);
        dataListView.setAdapter(selectPhotoAdapter);
    }
    public static   int MAX_NUM = 8;
    public static void setMaxNum(int maxNum) {
        MAX_NUM = maxNum;
    }


    public void clearData(){
        currentPathList =new ArrayList<>() ;
    }

    public void addImgPath(List<String> imgPath){
           if(currentPathList==null){
               currentPathList =new ArrayList<>() ;
           }
           int currentSize =currentPathList.size();
           int leftSize =MAX_NUM-currentSize;

          if(imgPath==null){
              selectPhotoAdapter.setPath(new ArrayList<String>());
            return;
           }
           if(imgPath!=null||!imgPath.isEmpty()){
               if(leftSize<imgPath.size()){
                   UiUtil.showLongToast(getContext(),"最多传入"+MAX_NUM+"张图片");
                   return;
               }
               currentPathList.addAll(imgPath) ;
               selectPhotoAdapter.setPath(currentPathList);
           }
    }

    private class SelectPhotoAdapter extends BaseAdapter{
        public List<String > path ;
        public void setPath(List<String> path) {
            this.path = path;
            notifyDataSetChanged();
        }
        @Override
        public int getCount() {
            if(path==null){
                return  1 ;
            }else{
                if(path.size()<MAX_NUM){
                    return  path.size()+1 ;
                }else{
                    return  path.size();
                }
            }
        }
        @Override
        public Object getItem(int position) {
            return path.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView =View.inflate(getContext(),R.layout.item_select_pic,null) ;
            final ImageView imgageView = (ImageView) convertView.findViewById(R.id.header);
            View deleteBt =convertView.findViewById(R.id.delete_bt);
            if(path==null||path.isEmpty()){
                //设置加号
                imgageView.setImageResource(R.drawable.host_img_add);
                imgageView.setTag("add");
                deleteBt.setVisibility(GONE);
            }else{
                //未达到最大图片数的时候
                if(path.size()<MAX_NUM){
                    //图片资源的设置
                    if(position<this.path.size()) {
                        String path = this.path.get(position);
                        Picasso.with(getContext()).load(new File(path)).placeholder(R.drawable.empty_img).resize(100,100).into(imgageView);
                        imgageView.setTag("pic");
                        deleteBt.setVisibility(VISIBLE);
                    //加号的设置
                    }else{
                        imgageView.setImageResource(R.drawable.host_img_add);
                        imgageView.setTag("add");
                        deleteBt.setVisibility(GONE);
                    }
                    //最大图片数的时候
                }else{
                    String path = this.path.get(position);
                    Picasso.with(getContext()).load(new File(path)).placeholder(R.drawable.empty_img).resize(100,100).into(imgageView);
                    imgageView.setTag("pic");
                    deleteBt.setVisibility(VISIBLE);
                }
            }
            deleteBt.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    path.remove(position);
                    notifyDataSetChanged();
                }
            });
            //ImageView 的点击事件
            imgageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if("add".equals(imgageView.getTag())){
                        if(onItemSelectPicClickListener!=null){
                            onItemSelectPicClickListener.onAddPic(MAX_NUM-(path==null?0:path.size()));
                        }
                    }else{
                        onItemSelectPicClickListener.onPictureClick(path.get(position));
                    }
                }
            });
            return convertView;
        }
    }
    private OnItemSelectPicClickListener onItemSelectPicClickListener ;
    public void setOnItemSelectPicClickListener(OnItemSelectPicClickListener onItemSelectPicClickListener) {
        this.onItemSelectPicClickListener = onItemSelectPicClickListener;
    }

    public interface  OnItemSelectPicClickListener{
        void onAddPic(int leftNum);
        void onPictureClick(String path);
    }


    public List<String> getSelectedPaht(){
        return  this.currentPathList;
    }


}

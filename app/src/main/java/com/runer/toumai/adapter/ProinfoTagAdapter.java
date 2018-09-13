package com.runer.toumai.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.runer.liabary.flowlayout.FlowLayout;
import com.runer.liabary.flowlayout.TagAdapter;
import com.runer.toumai.R;
import com.runer.toumai.ui.activity.TpyesProListActivity;

import java.util.List;

/**
 * Created by szhua on 2017/7/25/025.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * ProinfoTagAdapter
 */

public class ProinfoTagAdapter extends TagAdapter<String> {

    public ProinfoTagAdapter(List<String> datas) {
        super(datas);
    }

    @Override
    public View getView(FlowLayout parent, int position, final String s) {

        View view =View.inflate(parent.getContext(),R.layout.item_pro_tag_layout,null);
        TextView bt = (TextView) view.findViewById(R.id.bt);
        bt.setText(s);
        bt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(v.getContext(), TpyesProListActivity.class);
                intent.putExtra("type", "1");
                intent.putExtra("lable", s);
                v.getContext().startActivity(intent);
            }
        });
        return view;
    }
}

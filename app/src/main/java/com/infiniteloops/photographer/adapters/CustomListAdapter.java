package com.infiniteloops.photographer.adapters;
 

import java.util.List;
 
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.etiennelawlor.quickreturn.library.utils.QuickReturnUtils;
import com.infiniteloops.photographer.R;
import com.infiniteloops.photographer.model.Home_Model;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Home_Model> Home_ModelItems;
    private Transformation mTransformation;

    public CustomListAdapter(Activity activity, List<Home_Model> Home_ModelItems) {
        this.activity = activity;
        this.Home_ModelItems = Home_ModelItems;

        mTransformation = new RoundedTransformationBuilder()
//                .borderColor(getContext().getResources().getColor(R.color.white))
                .cornerRadius(QuickReturnUtils.dp2px(activity, 50))
                .build();
    }
 
    @Override
    public int getCount() {
        return Home_ModelItems.size();
    }
 
    @Override
    public Object getItem(int location) {
        return Home_ModelItems.get(location);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
 
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.photograph_single, null);

        TextView txt = (TextView) convertView.findViewById(R.id.display_name_tv);
        ImageView img = (ImageView) convertView.findViewById(R.id.post_iv);

        // getting Home_Model data for the row
        Home_Model m = Home_ModelItems.get(position);
        setUpText(txt,m.getAuthor());
        setUpImage(img,m.getOriginal());
        Log.d("image",m.getOriginal());
        // thumbnail image


        return convertView;
    }

    private void setUpText(TextView tv, String text) {
        if (!TextUtils.isEmpty(text)) {
            tv.setText(text);
        }
    }
    private void setUpImage(ImageView iv, String url) {
        if (!TextUtils.isEmpty(url)) {
            Picasso.with(iv.getContext())
                    .load(url)
                    .transform(mTransformation)
                    .centerCrop()
                    .resize(QuickReturnUtils.dp2px(activity, 50),
                            QuickReturnUtils.dp2px(activity, 50))
//                    .placeholder(R.drawable.ic_facebook)
                    .error(android.R.drawable.stat_notify_error)
                    .into(iv);
        }
    }
}
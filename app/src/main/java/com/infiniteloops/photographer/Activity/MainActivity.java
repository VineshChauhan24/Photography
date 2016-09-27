package com.infiniteloops.photographer.Activity;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.infiniteloops.photographer.R;
import com.infiniteloops.photographer.ReUseablity;
import com.infiniteloops.photographer.demo.Data;
import com.infiniteloops.photographer.utils.SessionManager;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
 
    public static MyAppAdapter myAppAdapter;
    public static ViewHolder viewHolder;
    private ArrayList<Data> al;
    private SwipeFlingAdapterView flingContainer;
    private ProgressDialog pDialog;
    private static final String TAG = MainActivity.class.getSimpleName();

    public static void removeBackground() {
 
 
        viewHolder.background.setVisibility(View.GONE);
        myAppAdapter.notifyDataSetChanged();
 
    }
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photograph_home);
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setCancelable(false);

        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);
 
        al = new ArrayList<>();
        al.add(new Data("http://i.ytimg.com/vi/PnxsTxV8y3g/maxresdefault.jpg", "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness."));
        al.add(new Data("http://switchboard.nrdc.org/blogs/dlashof/mission_impossible_4-1.jpg", "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness."));
        al.add(new Data("http://i.ytimg.com/vi/PnxsTxV8y3g/maxresdefault.jpg", "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness."));
        al.add(new Data("http://switchboard.nrdc.org/blogs/dlashof/mission_impossible_4-1.jpg", "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness."));
        al.add(new Data("http://i.ytimg.com/vi/PnxsTxV8y3g/maxresdefault.jpg", "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness."));
        getP();
        myAppAdapter = new MyAppAdapter(al, MainActivity.this);
        flingContainer.setAdapter(myAppAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {

            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                al.remove(0);
                myAppAdapter.notifyDataSetChanged();
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject

            }

            @Override
            public void onRightCardExit(Object dataObject) {

                al.remove(0);
                myAppAdapter.notifyDataSetChanged();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            @Override
            public void onScroll(float scrollProgressPercent) {

                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.background).setAlpha(0);
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0); view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {

                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.background).setAlpha(0);

                myAppAdapter.notifyDataSetChanged();
            }
        });

    }

    private void getP() {
        // Tag used to cancel the request

        pDialog.setMessage("Please wait ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                "http://infiniteloops.info/blog/volley/get_all_photograph.php?page=2", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Fetching Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    int error = jObj.getInt("err-code");
                    JSONArray arr = jObj.getJSONArray("photographs");

                    if (error == 0) {
                        for(int i=0;i<arr.length();i++) {

                            String id = arr.getJSONObject(i).getString("Id");
                            String image = arr.getJSONObject(i).getString("original");
                            String author = arr.getJSONObject(i).getString("author");
                            al.add(new Data(image, author));
                            System.out.print("Count loop"+i);
                        }
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("message");
                        ReUseablity.showToast(MainActivity.this,
                                errorMsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", "Fetching  Error: " + error.getMessage());
                ReUseablity.showToast(MainActivity.this,
                        error.getMessage());
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                SharedPreferences spf = ReUseablity.getSharedPrefValue(MainActivity.this);
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", spf.getString(ReUseablity.LOGIN_TOKEN, "asna"));
                return params;
            }
        };

        // Adding request to request queue
        //Idea_Application.getInstance().addToRequestQueue(strReq, tag_string_req);
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(strReq);
    }

    public static class ViewHolder {
        public static FrameLayout background;
        public TextView DataText;
        public ImageView cardImage;
 
 
    }
    
    public class MyAppAdapter extends BaseAdapter {

        ImageLoader imageLoader;
        public List<Data> parkingList;
        public Context context;
        private DisplayImageOptions displayImageOptions = null,displayImageOptionsDrawer = null;

        private MyAppAdapter(List<Data> apps, Context context) {
            this.parkingList = apps;
            this.context = context;
            displayImageOptions = new DisplayImageOptions.Builder().cacheInMemory(true)
                    .cacheOnDisc(true).resetViewBeforeLoading(true)
                    .build();
            imageLoader = ImageLoader.getInstance();
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));


        }
 
        @Override
        public int getCount() {
            return parkingList.size();
        }
 
        @Override
        public Object getItem(int position) {
            return position;
        }
 
        @Override
        public long getItemId(int position) {
            return position;
        }
 
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
 
            View rowView = convertView;
 
 
            if (rowView == null) {

                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.item, parent, false);
                // configure view holder
                viewHolder = new ViewHolder();
                viewHolder.DataText = (TextView) rowView.findViewById(R.id.bookText);
                viewHolder.background = (FrameLayout) rowView.findViewById(R.id.background);
                viewHolder.cardImage = (ImageView) rowView.findViewById(R.id.cardImage);
                rowView.setTag(viewHolder);
 
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.DataText.setText(parkingList.get(position).getDescription() + "");
            //Picasso.with(context).load(parkingList.get(position).getImagePath()).resize(50, 50).into(viewHolder.cardImage);
            imageLoader.displayImage(parkingList.get(position).getImagePath(),viewHolder.cardImage,displayImageOptions);

            return rowView;
        }
    }
    @Override
    public void onBackPressed(){

        show_alert();
    }
    public void show_alert(){
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                MainActivity.this);
// Setting Dialog Message
        alertDialog2.setMessage("Are you sure you want logout?");

// Setting Positive "Yes" Btn
        alertDialog2.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        Toast.makeText(getApplicationContext(),
                                "Successfully Logged Out", Toast.LENGTH_SHORT)
                                .show();
                        finish();
                        SessionManager sm = new SessionManager(MainActivity.this);
                        sm.setLogin(false);
                    }
                });

// Setting Negative "NO" Btn
        alertDialog2.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

// Showing Alert Dialog
        alertDialog2.show();
    }

    private void showDialog() {
        if (!isFinishing()) {
            //show dialog
            if (!pDialog.isShowing())
                pDialog.show();
        }
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
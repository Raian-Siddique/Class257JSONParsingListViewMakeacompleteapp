package com.example.class257jsonparsinglistviewmakeacompleteapp;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    ListView listView;
    ArrayList <HashMap<String,String>> arrayList = new ArrayList<>();
    HashMap<String,String > hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar= findViewById(R.id.progressBar);
        listView= findViewById(R.id.listView);


            //======Sending Volley Json array Request=========


            String url = "https://testbdraian.000webhostapp.com/apps/video.json";
            JsonArrayRequest arrayRequest= new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    progressBar.setVisibility(View.GONE);

                    try {


                        for (int i=0; i<response.length();i++){
                            JSONObject jsonObject = response.getJSONObject(i);
                            String title = jsonObject.getString("title");
                            String video_id = jsonObject.getString("video_id");


                            hashMap = new HashMap<>();
                            hashMap.put("title", title);
                            hashMap.put("video_id", video_id);
                            arrayList.add(hashMap);

                        }
                        // =======calling adapter=====
                        MyAdapter myAdapter= new MyAdapter();
                        listView.setAdapter(myAdapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    progressBar.setVisibility(View.GONE);

                }
            });
            // calling array request
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(arrayRequest);

            //================== Jason array request finished==========

    }
    //================================================================
                //Creating adapter

            private class MyAdapter extends BaseAdapter{

                @Override
                public int getCount() {
                    return arrayList.size();
                }

                @Override
                public Object getItem(int position) {
                    return null;
                }

                @Override
                public long getItemId(int position) {
                    return 0;
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {

                    LayoutInflater layoutInflater = getLayoutInflater();
                    View myView = layoutInflater.inflate(R.layout.item, null);
                    TextView tvTitle = myView.findViewById(R.id.tvTitle);
                    TextView imageThumb = myView.findViewById(R.id.imageThumb);

                    HashMap<String,String> hashMap = arrayList.get(position);
                    String title = hashMap.get("title");
                    String video_id = hashMap.get("video_id");

                    tvTitle.setText(title);
                    //https://img.youtube.com/vi/<insert-youtube-video-id-here>/0.jpg
                    //====== loading Youtube thumbnail with Picasso Library =====

                    String image_url = "https://img.youtube.com/vi/"+video_id+ "/0.jpg";

                    Picasso.get().load(image_url).into((Target) imageThumb);


                    return myView;
                }
            }


        //========== adapter finished ==============
}
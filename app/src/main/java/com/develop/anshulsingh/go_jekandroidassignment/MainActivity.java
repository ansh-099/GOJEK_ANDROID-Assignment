package com.develop.anshulsingh.go_jekandroidassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView ivNoInternet;
    LinearLayout llWhenNoDataFetch;
    TextView tv1NoInternet,tv2NoInternet;
    Button btnNoInternet;
    ArrayList<InformationView> infos=new ArrayList<>();
    ArrayList<ArrayList<SummaryView>> summs=new ArrayList<>();
    private SwipeRefreshLayout swipeContainer;
    SummaryAdapter adapter;
    UpdateFeed feed;
    SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.main_menu);

        sharedPrefs = getSharedPreferences("preff", Context.MODE_PRIVATE);

        ivNoInternet = findViewById(R.id.ivNoInternet);
        tv1NoInternet = findViewById(R.id.tv1NoInternet);
        tv2NoInternet = findViewById(R.id.tv2NoInternet);
        btnNoInternet = findViewById(R.id.btnNoInternet);
        llWhenNoDataFetch = findViewById(R.id.llWhenNoDataFetch);
        swipeContainer = findViewById(R.id.swipeContainer);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        boolean trig = retriveFromDB();
        
        if(trig) {
            adapter = new SummaryAdapter(infos);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }

        feed = new UpdateFeed();
        feed.execute();

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                fetchNew();
            }
        });

        if(!trig) {
            ConnectivityManager mgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = mgr.getActiveNetworkInfo();

            if (netInfo != null) {
                if (netInfo.isConnected()) {
                    // Internet Available
                } else {
                    notAvailable();
                    //No internet
                }
            } else {
                notAvailable();
                //No internet
            }

        }


        btnNoInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isInternetAvailable()){
                    Available();

                }
            }
        });




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    public boolean isInternetAvailable() {
        ConnectivityManager mgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = mgr.getActiveNetworkInfo();
        if (netInfo != null) {
            if (netInfo.isConnected()) {
                // Internet Available
                return true;
            }else {
                notAvailable();
                return false;
            }
        } else {
            notAvailable();
            return false;
        }



    }
    public void notAvailable(){
        btnNoInternet.setVisibility(View.VISIBLE);
        tv2NoInternet.setVisibility(View.VISIBLE);
        tv1NoInternet.setVisibility(View.VISIBLE);
        ivNoInternet.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }
    public void Available(){
        btnNoInternet.setVisibility(View.GONE);
        tv2NoInternet.setVisibility(View.GONE);
        tv1NoInternet.setVisibility(View.GONE);
        ivNoInternet.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }


    class UpdateFeed extends AsyncTask<Void, Void, Void>{

        // Get Request to update feed in async
        @Override
        protected Void doInBackground(Void... voids) {
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            String url ="https://github-trending-api.now.sh/repositories?language=&since=daily";
            Log.d("hey","stage1");
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            try {
                                JSONArray array = new JSONArray(response);

                                Log.d("hey","working");
                                //Parser
                                for(int i=0;i<array.length();i++){
                                    JSONObject obj = (JSONObject) array.get(i);
                                    String username = obj.getString("name");
                                    String lang = "";
                                    if(obj.has("language")){
                                        lang = obj.getString("language");
                                    }

                                    String intro = obj.getString("description");
                                    String imageURL = obj.getString("avatar");
                                    String title = obj.getString("author");
                                    Integer stars = obj.getInt("stars");
                                    Integer folks = obj.getInt("forks");
                                    summs.add(new ArrayList<SummaryView>());
                                    summs.get(i).add(new SummaryView(intro,lang,username,imageURL,stars,folks));
                                    infos.add(new InformationView(title,summs.get(i)));
                                    llWhenNoDataFetch.setVisibility(View.GONE);
                                    recyclerView.setVisibility(View.VISIBLE);
                                        adapter = new SummaryAdapter(infos);
                                        recyclerView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                }

                                storeInDB(infos,summs);




                            } catch (JSONException e) {
                                notAvailable();
                                Log.d("hey", String.valueOf(e));
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("hey","error");
                }
            });

// Add the request to the RequestQueue.
            queue.add(stringRequest);
            return null;
        }
    }

    public void fetchNew() {

        infos.clear();
        summs.clear();
        feed.doInBackground();
        adapter.notifyDataSetChanged();

        swipeContainer.setRefreshing(false);
    }



    public void storeInDB(ArrayList<InformationView> info,ArrayList<ArrayList<SummaryView>> summ) {


        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();

        String json = gson.toJson(info);

        editor.putString("infos", json);

        json = gson.toJson(summ);
        editor.putString("summs", json);


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String dateTime = dateFormat.format(date);

        editor.putString("time",dateTime);
        editor.commit();

    }

    public boolean retriveFromDB(){

        boolean trigger = false;
        Gson gson = new Gson();
        String infoString = sharedPrefs.getString("infos", "");
        String summString = sharedPrefs.getString("summs","");
        if(infoString.equals("") || summString.equals("")){

        }else{
            trigger = true;
            TypeToken<ArrayList<InformationView>> tokenInfo = new TypeToken<ArrayList<InformationView>>() {
            };
            infos = gson.fromJson(infoString, tokenInfo.getType());

            TypeToken<ArrayList<ArrayList<SummaryView>>> tokenSumm = new TypeToken<ArrayList<ArrayList<SummaryView>>>() {
            };
            summs = gson.fromJson(summString, tokenSumm.getType());



        }
        return trigger;


    }




}

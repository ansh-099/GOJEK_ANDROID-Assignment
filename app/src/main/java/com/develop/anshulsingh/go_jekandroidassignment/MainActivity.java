package com.develop.anshulsingh.go_jekandroidassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.InetAddress;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView ivNoInternet;
    TextView tv1NoInternet,tv2NoInternet;
    Button btnNoInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.main_menu);

        ivNoInternet = findViewById(R.id.ivNoInternet);
        tv1NoInternet = findViewById(R.id.tv1NoInternet);
        tv2NoInternet = findViewById(R.id.tv2NoInternet);
        btnNoInternet = findViewById(R.id.btnNoInternet);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if(isInternetAvailable()){

        }else{
            notAvailable();
        }

        btnNoInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isInternetAvailable()){
                    Available();
                }
            }
        });

        ArrayList<InformationView> infos=new ArrayList<>();
        ArrayList<SummaryView> summs=new ArrayList<>();

        summs.add(new SummaryView("ehfe","sd","vs","vds",2,2));


        infos.add(new InformationView("dcs",summs));

        SummaryAdapter adapter = new SummaryAdapter(infos);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }
    public void notAvailable(){
//        btnNoInternet.setVisibility(View.VISIBLE);
//        tv2NoInternet.setVisibility(View.VISIBLE);
//        tv1NoInternet.setVisibility(View.VISIBLE);
//        ivNoInternet.setVisibility(View.VISIBLE);
//        recyclerView.setVisibility(View.GONE);
    }
    public void Available(){
        btnNoInternet.setVisibility(View.GONE);
        tv2NoInternet.setVisibility(View.GONE);
        tv1NoInternet.setVisibility(View.GONE);
        ivNoInternet.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }
}

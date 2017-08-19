package com.fakhrus.bootcamppariwisata.main;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.fakhrus.bootcamppariwisata.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout srl_refreshList;
    private RecyclerView rv_listPariwisata;

    private List<PariwisataModel> itemDataPariwisata = new ArrayList<>();

    private static final String TAG = "List Pariwisata";
    private PariwisataAdapter adapter;
    private RelativeLayout rl_info_state_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        srl_refreshList.setOnRefreshListener(this);

        srl_refreshList.post(new Runnable() {
            @Override
            public void run() {
                getLayoutInflater().inflate(R.layout.state_layout_loading, rl_info_state_layout);
                srl_refreshList.setRefreshing(true);
                networkPariwisata();
            }
        });


    }

    private void networkPariwisata() {

        new Network(new Network.DataCallback() {
            @Override
            public void onResponse(String json) {
                Log.d(TAG, "onResponse: " + json);

                if (json == null){
                    srl_refreshList.setRefreshing(false);
                    rl_info_state_layout.removeAllViews();
                    getLayoutInflater().inflate(R.layout.state_layout_no_connection, rl_info_state_layout);
                }

                try {
                    if (json != null) {


                        JSONObject jsonObj = new JSONObject(json);


                        if (jsonObj.getString("result").equals("true")) {

                            JSONArray data = jsonObj.getJSONArray("data");


                            for (int i = 0; i < data.length(); i++) {
                                JSONObject jo = data.getJSONObject(i);

                                PariwisataModel pm = new PariwisataModel();

                                pm.setNamaPariwisata(jo.getString("nama_pariwisata"));
                                pm.setAlamatPariwisata(jo.getString("alamat_pariwisata"));
                                pm.setDetailPariwisata(jo.getString("detail_pariwisata"));
                                pm.setGambarPariwisata(jo.getString("gambar_pariwisata"));

                                itemDataPariwisata.add(pm);
                            }

                            setRecyclerViewAdapter();

                            rl_info_state_layout.removeAllViews();
                            rl_info_state_layout.setVisibility(View.GONE);

                            srl_refreshList.setRefreshing(false);
                        }
                    }


                } catch (final JSONException e) {

                    Log.e(TAG, "Json parsing error: " + e.getMessage());

                }

            }

            @Override
            public void onError(Throwable throwable) {
                Log.e(TAG, "onError: ", throwable);


            }
        }).process();

    }

    private void setRecyclerViewAdapter() {
        adapter = new PariwisataAdapter(MainActivity.this, itemDataPariwisata);
        rv_listPariwisata.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        rv_listPariwisata.setLayoutManager(layoutManager);
        rv_listPariwisata.setHasFixedSize(true);
    }

    private void initViews() {
        srl_refreshList = (SwipeRefreshLayout) findViewById(R.id.srl_swipe_list_pariwisata);
        rv_listPariwisata = (RecyclerView) findViewById(R.id.rv_list_pariwisata);
        rl_info_state_layout = (RelativeLayout) findViewById(R.id.rl_info_state_layout);
    }

    @Override
    public void onRefresh() {

        srl_refreshList.setRefreshing(true);
        networkPariwisata();
    }


}

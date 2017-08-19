package com.fakhrus.bootcamppariwisata.main.detail_pariwisata;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fakhrus.bootcamppariwisata.R;

import java.io.InputStream;

public class DetailPariwisataActivity extends AppCompatActivity {

    private String name, address, detail, image;
    private TextView tv_namePariwisata, tv_addressPariwisata, tv_detailPariwisata;
    private ProgressBar pb_progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pariwisata);

        initActionBar();
        getIntentExtra();
        initViews();
        bindViews();
    }

    private void bindViews() {
        tv_namePariwisata.setText(name);
        tv_addressPariwisata.setText(address);
        tv_detailPariwisata.setText(detail);

        new DownloadImageTask((ImageView) findViewById(R.id.iv_image_detail_pariwisata))
                .execute(image);
    }

    private void initViews() {

        tv_namePariwisata = (TextView) findViewById(R.id.tv_name_detail_pariwisata);
        tv_addressPariwisata = (TextView) findViewById(R.id.tv_address_detail_pariwisata);
        tv_detailPariwisata = (TextView) findViewById(R.id.tv_detail_detail_pariwisata);
        pb_progressBar = (ProgressBar) findViewById(R.id.pb_progress_image_detail_pariwisata);

    }

    private void getIntentExtra() {

        name = getIntent().getExtras().getString("name");
        address = getIntent().getExtras().getString("address");
        detail = getIntent().getExtras().getString("detail");

        image = getIntent().getExtras().getString("image");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();    //Call the back button's method
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
            pb_progressBar.setVisibility(View.GONE);
        }
    }
}

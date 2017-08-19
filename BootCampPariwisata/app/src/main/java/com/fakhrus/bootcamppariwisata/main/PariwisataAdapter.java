package com.fakhrus.bootcamppariwisata.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fakhrus.bootcamppariwisata.R;
import com.fakhrus.bootcamppariwisata.main.detail_pariwisata.DetailPariwisataActivity;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Fakhrus on 8/9/17.
 */

public class PariwisataAdapter extends RecyclerView.Adapter<PariwisataAdapter.PariwisataViewHolder> {

    Context context;
    List<PariwisataModel> itemPariwisata;

    public PariwisataAdapter(Context context, List<PariwisataModel> itemPariwisata) {
        this.context = context;
        this.itemPariwisata = itemPariwisata;
    }

    @Override
    public PariwisataAdapter.PariwisataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_pariwisata, parent, false);

        PariwisataViewHolder pariwisataViewHolder = new PariwisataViewHolder(view);

        return pariwisataViewHolder;
    }

    @Override
    public void onBindViewHolder(PariwisataAdapter.PariwisataViewHolder holder, int position) {

        holder.bindView(itemPariwisata.get(position).getNamaPariwisata(),
                        itemPariwisata.get(position).getAlamatPariwisata(),
                        itemPariwisata.get(position).getDetailPariwisata(),
                        itemPariwisata.get(position).getGambarPariwisata());

    }

    @Override
    public int getItemCount() {
        return itemPariwisata.size();
    }


    public class PariwisataViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_titlePariwisata, tv_addressPariwisata, tv_detailPariwisata;
        private ProgressBar pb_progressImage;
        private LinearLayout ll_itemPariwisata;

        public PariwisataViewHolder(View itemView) {
            super(itemView);

            initView(itemView);

        }

        private void initView(View itemView) {

            tv_titlePariwisata = (TextView) itemView.findViewById(R.id.tv_title_list_pariwisata);
            tv_addressPariwisata = (TextView) itemView.findViewById(R.id.tv_address_list_pariwisata);
            tv_detailPariwisata = (TextView) itemView.findViewById(R.id.tv_detail_list_pariwisata);
            ll_itemPariwisata = (LinearLayout) itemView.findViewById(R.id.ll_item_list_pariwisata);

            pb_progressImage = (ProgressBar) itemView.findViewById(R.id.pb_progress_image_list_pariwisata);

        }

        private void bindView(final String namaPariwisata, final String alamatPariwisata, final String detailPariwisata, final String gambarPariwisata) {

            tv_titlePariwisata.setText(namaPariwisata);
            tv_addressPariwisata.setText(alamatPariwisata);
            tv_detailPariwisata.setText(detailPariwisata);

            pb_progressImage.setVisibility(View.VISIBLE);

            new DownloadImageTask((ImageView) itemView.findViewById(R.id.iv_image_list_pariwisata))
                    .execute(gambarPariwisata);

            ll_itemPariwisata.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent toDetailPariwisata = new Intent(context, DetailPariwisataActivity.class);
                    toDetailPariwisata.putExtra("name", namaPariwisata);
                    toDetailPariwisata.putExtra("address", alamatPariwisata);
                    toDetailPariwisata.putExtra("detail", detailPariwisata);
                    toDetailPariwisata.putExtra("image", gambarPariwisata);
                    context.startActivity(toDetailPariwisata);
                }
            });

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
                pb_progressImage.setVisibility(View.GONE);
            }
        }


    }
}

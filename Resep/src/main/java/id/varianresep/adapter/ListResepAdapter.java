package id.varianresep.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.startapp.android.publish.adsCommon.StartAppAd;

import id.varianresep.R;
import id.varianresep.activity.PembukaResep;
import id.varianresep.bantu.Constant;
import id.varianresep.bantu.IklanChache;
import id.varianresep.iklan.ViewWrapper;
import id.varianresep.model.ModelListTemp;

public class ListResepAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private ModelListTemp[] modelListTemps;
    private String[] judulResep;

    public ListResepAdapter(Context context, ModelListTemp[] modelListTemps, String[] judulResep) {
        this.context = context;
        this.modelListTemps = modelListTemps;
        this.judulResep = judulResep;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewWrapper<>(new RecyPerItem(context));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holders, final int position) {
        final RecyPerItem holder = (RecyPerItem) holders.itemView;
        holder.penampunglist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) context;
                final IklanChache iklanChache = (IklanChache) activity.getApplication();
                String status = iklanChache.getStatusIklan();
                if(status.equals(Constant.berhasilLoadIklan)) {
                    if (!iklanChache.isBolehMenampilkanIklanHitung() || !iklanChache.isBolehMenampilkanIklanWaktu()
                            || !iklanChache.getInterstitial().isLoaded()) {
                        Intent intent = new Intent(context, PembukaResep.class);
                        intent.putExtra(Constant.kodeIndexResep, String.valueOf(position));
                        context.startActivity(intent);
                    }
                    iklanChache.getInterstitial().setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            Intent intent = new Intent(context, PembukaResep.class);
                            intent.putExtra(Constant.kodeIndexResep, String.valueOf(position));
                            context.startActivity(intent);

                            iklanChache.loadIntersTitial();
                            super.onAdClosed();
                        }
                        @Override
                        public void onAdFailedToLoad(int i) {
                            if(iklanChache.getHitungFailed() < 2 ) {
                                iklanChache.loadIntersTitial();
                                iklanChache.setHitungFailed();
                            }
                            super.onAdFailedToLoad(i);
                        }

                        @Override
                        public void onAdLoaded() {
                            iklanChache.setHitungFailed(0);
                            super.onAdLoaded();
                        }
                    });
                    iklanChache.tampilkanInterstitial();
                }else{
                    if(iklanChache.getPenghitungStartApp() == 0) {
                        Log.d("iklan", "startApp inters tampil " + iklanChache.getPenghitungStartApp());
                        iklanChache.setPenghitungStartApp(1);
                        Intent intent = new Intent(context, PembukaResep.class);
                        intent.putExtra(Constant.kodeIndexResep, String.valueOf(position));
                        context.startActivity(intent);
                        StartAppAd.showAd(context);
                    }else{
                        Log.d("iklan", "startApp tidak tampil " + iklanChache.getPenghitungStartApp());
                        iklanChache.setPenghitungStartApp(0);
                        Intent intent = new Intent(context, PembukaResep.class);
                        intent.putExtra(Constant.kodeIndexResep, String.valueOf(position));
                        context.startActivity(intent);
                    }
                }

            }
        });

        Log.e("eror", judulResep[position].substring(0, judulResep[position].length()-4));

        holder.judulResep.setText(judulResep[position].substring(0, judulResep[position].length()-4));
        holder.judulWaktu.setText(modelListTemps[position].getWaktu()+ " Menit");
        holder.judulPorsi.setText(modelListTemps[position].getPorsi()+ " PORSI");
        holder.judulBiaya.setText(modelListTemps[position].getBiaya()+ " Rupiah");
        holder.previewResep.setImageBitmap(modelListTemps[position].getBitmap());
    }

    @Override
    public int getItemCount() {
        return modelListTemps.length;
    }

    class RecyPerItem extends FrameLayout {
        private RelativeLayout penampunglist;
        private TextView judulResep;
        private TextView judulWaktu;
        private TextView judulPorsi;
        private TextView judulBiaya;
        private ImageView previewResep;
        public RecyPerItem(Context context) {
            super(context);
            inflate(context, R.layout.design_tiap_list, this);
            penampunglist = (RelativeLayout) findViewById(R.id.penampunglist);
            judulResep = (TextView) findViewById(R.id.jResep);
            judulWaktu = (TextView) findViewById(R.id.jWaktu);
            judulPorsi = (TextView) findViewById(R.id.jPorsi);
            judulBiaya = (TextView) findViewById(R.id.jBiaya);
            previewResep = (ImageView) findViewById(R.id.previewResep);
        }
    }
}

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

import java.util.List;

import id.varianresep.R;
import id.varianresep.activity.BookmarkedResep;
import id.varianresep.activity.PembukaResep;
import id.varianresep.bantu.Constant;
import id.varianresep.bantu.IklanChache;
import id.varianresep.iklan.ViewWrapper;
import id.varianresep.model.BookmarkModel;

public class BookmarkedResepAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<BookmarkModel> bookmarkModel;
    private int kodeRequestResult = 367;

    public BookmarkedResepAdapter(Context context, List<BookmarkModel> bookmarkModel) {
        this.context = context;
        this.bookmarkModel = bookmarkModel;
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

                ((BookmarkedResep)context).setPosisiTemp(position);

                Activity activity = (Activity) context;
                final IklanChache iklanChache = (IklanChache) activity.getApplication();
                String status = iklanChache.getStatusIklan();
                if(status.equals(Constant.berhasilLoadIklan)) {
                    if (!iklanChache.isBolehMenampilkanIklanHitung() || !iklanChache.isBolehMenampilkanIklanWaktu()
                            || !iklanChache.getInterstitial().isLoaded()) {
                        Intent intent = new Intent(context, PembukaResep.class);
                        intent.putExtra(Constant.kodeIndexResep, bookmarkModel.get(position).getRealIndexBookmark());
                        ((BookmarkedResep)context).startActivityForResult(intent, kodeRequestResult);
                    }
                    iklanChache.getInterstitial().setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            Intent intent = new Intent(context, PembukaResep.class);
                            intent.putExtra(Constant.kodeIndexResep,  bookmarkModel.get(position).getRealIndexBookmark());
                            ((BookmarkedResep)context).startActivityForResult(intent, kodeRequestResult);

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
                        intent.putExtra(Constant.kodeIndexResep,  bookmarkModel.get(position).getRealIndexBookmark());
                        ((BookmarkedResep)context).startActivityForResult(intent, kodeRequestResult);
                        StartAppAd.showAd(context);
                    }else{
                        Log.d("iklan", "startApp tidak tampil " + iklanChache.getPenghitungStartApp());
                        iklanChache.setPenghitungStartApp(0);
                        Intent intent = new Intent(context, PembukaResep.class);
                        intent.putExtra(Constant.kodeIndexResep,  bookmarkModel.get(position).getRealIndexBookmark());
                        ((BookmarkedResep)context).startActivityForResult(intent, kodeRequestResult);
                    }
                }

            }
        });

        holder.judulResep.setText(bookmarkModel.get(position).getJudulResep().substring(0, bookmarkModel.get(position).getJudulResep().length()-4));
        holder.judulWaktu.setText(bookmarkModel.get(position).getModelListTemp().getWaktu());
        holder.judulPorsi.setText(bookmarkModel.get(position).getModelListTemp().getPorsi()+" PORSI");
        holder.judulBiaya.setText(bookmarkModel.get(position).getModelListTemp().getBiaya());
        holder.previewResep.setImageBitmap(bookmarkModel.get(position).getModelListTemp().getBitmap());
    }

    @Override
    public int getItemCount() {
        return bookmarkModel.size();
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

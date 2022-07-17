package id.varianresep.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdSize;
import com.startapp.android.publish.ads.banner.Banner;

import java.io.IOException;

import id.varianresep.R;
import id.varianresep.adapter.ListResepAdapter;
import id.varianresep.bantu.Constant;
import id.varianresep.bantu.DekorAja;
import id.varianresep.bantu.IklanChache;
import id.varianresep.bantu.KontakNative;
import id.varianresep.dekrip.assets.DekripResepList;
import id.varianresep.iklan.WrapAdapter;
import id.varianresep.model.PenampungListModelSerial;

public class ListResep extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListResepAdapter listResepAdapter;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_resep);
        prepareDekrip();

        findViewById(R.id.tombolKembali).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void prepareDekrip(){
        DekripResepList dekripResepList = new DekripResepList(this, new KontakNative(this).getKeyAssets());
        dekripResepList.setListenerDecrypt(new DekripResepList.ListenerDecrypt() {
            @Override
            public void onSelesaiDecrypt(PenampungListModelSerial resepModels) {
                recyclerView = (RecyclerView) findViewById(R.id.recResep);
                try {
                    listResepAdapter = new ListResepAdapter(context, resepModels.getListModelList(), Constant.listSemuaResep(context));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.addItemDecoration(new DekorAja(5, context));
                urusanIklan();
                listResepAdapter.notifyDataSetChanged();
            }
        });
        dekripResepList.execute();
    }

    private void urusanIklan(){
        final IklanChache iklanChache = (IklanChache) getApplication();
        String status = iklanChache.getStatusIklan();
        if(status.equals(Constant.berhasilLoadIklan)) {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
            int width = (int) dpWidth;
            WrapAdapter adapterWrapper = new WrapAdapter(this,
                    new KontakNative(this).getAdNativeList(),
                    new AdSize(width, 100));
            adapterWrapper.setAdapter(listResepAdapter);
            adapterWrapper.setLimitOfAds(3);
            adapterWrapper.setNoOfDataBetweenAds(8);
            adapterWrapper.setFirstAdIndex(0);
            recyclerView.setAdapter(adapterWrapper);
        }else {
            recyclerView.setAdapter(listResepAdapter);
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.iklanStartApp);
            linearLayout.setVisibility(View.VISIBLE);
            Banner startAppBanner = new Banner(this);
            linearLayout.addView(startAppBanner);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

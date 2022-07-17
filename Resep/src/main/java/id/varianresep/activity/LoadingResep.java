package id.varianresep.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.ads.AdListener;

import id.varianresep.R;
import id.varianresep.bantu.Constant;
import id.varianresep.bantu.IklanChache;

public class LoadingResep extends AppCompatActivity {

    private boolean statusIklan = true;
    int hitung = 0;
    int loadIklanBerapaKali = 5;
    private IklanChache iklanChache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_resep);
        iklanChache = (IklanChache) getApplication();
        jalankanAppNormal();
    }


    private void jalankanAppNormal(){
        urusanIklan();
    }


    private void urusanIklan(){
        iklanChache.initInterstitial();
        iklanChache.loadIntersTitial();
        iklanChache.getInterstitial().setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int i) {
                hitung++;
                Log.d("iklan", "gagal "+ String.valueOf(hitung));
                if(hitung<loadIklanBerapaKali){
                    if(statusIklan) {
                        iklanChache.loadIntersTitial();
                    }
                }
                if(hitung == loadIklanBerapaKali){
                    if(statusIklan) {
                        statusIklan = false;
                        iklanChache.setStatusIklan(Constant.gagalLoadIklan);
                        Intent intent = new Intent(LoadingResep.this, MenuUtama.class);
                        startActivity(intent);
                        finish();
                    }
                }
                super.onAdFailedToLoad(i);
            }

            @Override
            public void onAdLoaded() {
                if(statusIklan) {
                    Log.d("iklan", "berhasil");
                    statusIklan = false;
                    iklanChache.setStatusIklan(Constant.berhasilLoadIklan);
                    Intent intent = new Intent(LoadingResep.this, MenuUtama.class);
                    startActivity(intent);
                    finish();
                }
                super.onAdLoaded();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(statusIklan) {
                    statusIklan = false;
                    iklanChache.setStatusIklan(Constant.gagalLoadIklan);
                    Intent intent = new Intent(LoadingResep.this, MenuUtama.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 15000);
    }
}

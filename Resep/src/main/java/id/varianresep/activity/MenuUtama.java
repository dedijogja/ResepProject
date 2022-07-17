package id.varianresep.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;
import com.startapp.android.publish.ads.splash.SplashConfig;
import com.startapp.android.publish.adsCommon.SDKAdPreferences;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;

import id.varianresep.R;
import id.varianresep.bantu.Constant;
import id.varianresep.bantu.IklanChache;
import id.varianresep.bantu.KontakNative;

public class MenuUtama extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final IklanChache iklanChache = (IklanChache) getApplication();
        String status = iklanChache.getStatusIklan();
        if(status.equals(Constant.gagalLoadIklan)) {
            StartAppSDK.init(this, new KontakNative(this).getStartAppId(),
                    new SDKAdPreferences()
                    .setAge(35)
                    .setGender(SDKAdPreferences.Gender.FEMALE), false);
            if (!isNetworkConnected()) {
                StartAppAd.disableSplash();
            }else{
                StartAppAd.showSplash(this, savedInstanceState,
                        new SplashConfig()
                                .setTheme(SplashConfig.Theme.USER_DEFINED)
                                .setCustomScreen(R.layout.activity_loading_resep)
                );
            }
        }

        setContentView(R.layout.activity_menu_utama);

        if(status.equals(Constant.berhasilLoadIklan)){
            NativeExpressAdView adViewAtas = new NativeExpressAdView(this);
            adViewAtas.setAdSize(new AdSize(AdSize.FULL_WIDTH, 300));
            adViewAtas.setAdUnitId(new KontakNative(this).getAdNativeMenu());
            adViewAtas.loadAd(new AdRequest.Builder().build());
            ((LinearLayout) findViewById(R.id.iklanMenu)).addView(adViewAtas);
        }

        if(status.equals(Constant.gagalLoadIklan)){
            ImageView imageView = new ImageView(this);
            imageView.setMaxWidth(300);
            imageView.setMaxHeight(300);
            imageView.setImageResource(R.drawable.logo);
            ((LinearLayout)findViewById(R.id.iklanMenu)).addView(imageView);
        }
        klikListenerMas();

    }


    private void klikListenerMas(){
        findViewById(R.id.btnListResep).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuUtama.this, ListResep.class));
            }
        });
        findViewById(R.id.btnListBookmark).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuUtama.this, BookmarkedResep.class));
            }
        });
        findViewById(R.id.btnHelp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuUtama.this, HelpActivity.class));
            }
        });
        findViewById(R.id.btnAbout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuUtama.this, AboutActivity.class));
            }
        });
        findViewById(R.id.btnOther).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("market://search?q=pub:"+ Constant.namaDevId));
                    startActivity(intent);
                }catch (Exception e){
                    Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                    i.setData(Uri.parse("https://play.google.com/store/apps/developer?id="+ Constant.namaDevId));
                    startActivity(i);
                }
            }
        });
        findViewById(R.id.btnRate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("market://details?id=" + getApplicationContext().getPackageName()));
                    startActivity(intent);
                }catch (Exception e){
                    Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                    i.setData(Uri.parse("https://play.google.com/store/apps/details?id="+ getApplicationContext().getPackageName()));
                    startActivity(i);
                }
            }
        });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void onBackPressed() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Apakah anda ingin menutup aplikasi ini?");
        alertDialog.setMessage("Alert message to be shown");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YA",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "BATAL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}

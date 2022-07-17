package id.varianresep.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.startapp.android.publish.ads.banner.Banner;

import java.io.IOException;

import id.varianresep.R;
import id.varianresep.adapter.TabAdapter;
import id.varianresep.bantu.Constant;
import id.varianresep.bantu.IklanChache;
import id.varianresep.bantu.KontakNative;
import id.varianresep.bantu.ManageBookmarkResep;
import id.varianresep.bantu.ShareEngine;
import id.varianresep.dekrip.assets.DekripResepPembukaResep;
import id.varianresep.fragment.FragmentKanan;
import id.varianresep.fragment.FragmentKiri;
import id.varianresep.fragment.FragmentTengah;
import id.varianresep.model.EngineModel;
import id.varianresep.model.ResepModel;

public class PembukaResep extends AppCompatActivity {

    private ResepModel resepModel;
    private String[] listResep;
    private Bitmap bitmap;
    private Context context = this;
    private int posisi = 0;
    private boolean unBookmark = false;
    int posisiTabAktif = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembuka_resep);
        if(getIntent().getStringExtra(Constant.kodeIndexResep)!=null) {
            posisi = Integer.valueOf(getIntent().getStringExtra(Constant.kodeIndexResep));
        }
        try {
            listResep = Constant.listSemuaResep(this);
            loadResep(listResep[posisi]);
        } catch (IOException e) {
            e.printStackTrace();
        }


        if(getIntent().getStringExtra("posisitab") != null && !getIntent().getStringExtra("posisitab").equals("")){
            posisiTabAktif = Integer.valueOf(getIntent().getStringExtra("posisitab"));
        }
        jalankanAppNormal();
    }
    private void jalankanAppNormal(){
        ManageBookmarkResep manageBookmarkResep = new ManageBookmarkResep(this);
        if(manageBookmarkResep.dataBelumAda(posisi)){
            ((ImageView)findViewById(R.id.gambarHati)).setImageResource(R.drawable.offbookmark);
        }else {
            ((ImageView)findViewById(R.id.gambarHati)).setImageResource(R.drawable.onbookmark);
        }

        findViewById(R.id.tombolBookmark).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManageBookmarkResep manageBookmarkResep1 = new ManageBookmarkResep(context);
                if(manageBookmarkResep1.dataBelumAda(posisi)){
                    unBookmark = false;
                    manageBookmarkResep1.simpanKeFavorit(posisi);
                    ((ImageView)findViewById(R.id.gambarHati)).setImageResource(R.drawable.onbookmark);
                }else {
                    unBookmark = true;
                    manageBookmarkResep1.hapusDariFavorit(posisi);
                    ((ImageView)findViewById(R.id.gambarHati)).setImageResource(R.drawable.offbookmark);
                }
            }
        });

        findViewById(R.id.tombolShare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShareEngine(context, resepModel, getJudulResep()).bagikanResep();
            }
        });

        ((TextView) findViewById(R.id.judulresepPembuka)).setText(getJudulResep());
    }

    private void loadResep(String namaResep){
        DekripResepPembukaResep dekripResepPembukaResep = new DekripResepPembukaResep(this, new KontakNative(this).getKeyAssets());
        dekripResepPembukaResep.setListenerDecrypt(new DekripResepPembukaResep.ListenerDecrypt() {
            @Override
            public void onSelesaiDecrypt(byte[] resep, byte[] gambar) {
                bitmap = BitmapFactory.decodeByteArray(gambar, 0, gambar.length);
                String[] lines = new String(resep).split(System.getProperty("line.separator"));
                resepModel = new EngineModel(lines).getResepModel();
                ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
                TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
                adapter.addFragment(new FragmentKiri(), "BAHAN BUMBU");
                adapter.addFragment(new FragmentTengah(), "SUMMARY");
                adapter.addFragment(new FragmentKanan(), "CARA MASAK");
                viewPager.setAdapter(adapter);

                TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                tabLayout.setupWithViewPager(viewPager);
                viewPager.setCurrentItem(posisiTabAktif);

                ((KenBurnsView) findViewById(R.id.gambarAnimasi)).setImageBitmap(bitmap);

            }
        });

        String[] daftarEksekusi = {namaResep};
        dekripResepPembukaResep.execute(daftarEksekusi);
    }

    public ResepModel getResepModel(){
        return  resepModel;
    }

    public String getJudulResep(){
        return listResep[posisi].substring(0,
                listResep[posisi].length()-4);
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public String getStatuSiklan(){
        IklanChache iklanChache = (IklanChache) getApplication();
        return iklanChache.getStatusIklan();
    }

    @Override
    public void onBackPressed() {
        Intent data = new Intent();
        Bundle bundle = new Bundle();

        bundle.putBoolean("kodeResult", unBookmark);
        data.putExtras(bundle);

        if (getParent() == null) {
            setResult(Activity.RESULT_OK, data);
        } else {
            getParent().setResult(Activity.RESULT_OK, data);
        }

        super.onBackPressed();
    }

}

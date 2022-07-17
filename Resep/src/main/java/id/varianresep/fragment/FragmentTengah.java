package id.varianresep.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;

import id.varianresep.R;
import id.varianresep.activity.PembukaResep;
import id.varianresep.bantu.Constant;
import id.varianresep.bantu.KontakNative;
import id.varianresep.model.ResepModel;

public class FragmentTengah extends Fragment {

    public FragmentTengah() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View penampung = inflater.inflate(R.layout.fragment_tengah, container, false);

        ResepModel resepModel = ((PembukaResep) getActivity()).getResepModel();
        ((TextView) penampung.findViewById(R.id.textJudul)).setText(((PembukaResep) getActivity()).getJudulResep());
        ((TextView) penampung.findViewById(R.id.textPorsi)).setText("Porsi : " + resepModel.getHasilModel().getJumlahPorsi() + " Orang");
        ((TextView) penampung.findViewById(R.id.textBiaya)).setText("BIAYA " + resepModel.getHasilModel().getJumlahBiaya() + " K");
        ((TextView) penampung.findViewById(R.id.textWaktu)).setText("Waktu : " + resepModel.getHasilModel().getJumlahWaktu() + " Menit");

        if(((PembukaResep) getActivity()).getStatuSiklan().equals(Constant.berhasilLoadIklan)){
            final NativeExpressAdView adViewAtas = new NativeExpressAdView(container.getContext());
            adViewAtas.setAdSize(new AdSize(AdSize.FULL_WIDTH, 300));
            adViewAtas.setAdUnitId(new KontakNative(container.getContext()).getAdNativePembuka());
            AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
            adViewAtas.loadAd(adRequestBuilder.build());
            ((LinearLayout)penampung.findViewById(R.id.iklanPembukaResep)).addView(adViewAtas);
            adViewAtas.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                    adViewAtas.setVisibility(View.GONE);

                }
            });
        }else{
            ImageView imageView = new ImageView(container.getContext());
            imageView.setImageResource(R.drawable.logo);
            imageView.setMaxHeight(300);
            imageView.setMaxWidth(300);
            ((LinearLayout)penampung.findViewById(R.id.iklanPembukaResep)).addView(imageView);
        }

        Bitmap bitmap = ((PembukaResep) getActivity()).getBitmap();
        Bitmap mbitmap =  ThumbnailUtils.extractThumbnail(bitmap, 75, 75);
        Bitmap imageRounded = Bitmap.createBitmap(mbitmap.getWidth(), mbitmap.getHeight(), mbitmap.getConfig());
        Canvas canvas = new Canvas(imageRounded);
        Paint mpaint = new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setShader(new BitmapShader(mbitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect((new RectF(0, 0, mbitmap.getWidth(), mbitmap.getHeight())), 10, 10, mpaint);
        ((ImageView) penampung.findViewById(R.id.gambarSummary)).setImageBitmap(imageRounded);

        return penampung;
    }

}

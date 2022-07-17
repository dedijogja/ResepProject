package id.varianresep.bantu;


import android.content.Context;
import android.util.Log;

import id.varianresep.dekrip.text.deskriptor.DeskripsiText;

public class KontakNative {
    static {
        System.loadLibrary("native-lib");
    }

    public KontakNative(Context context){
        if(!context.getPackageName().equals(packageName())){
            throw new RuntimeException(new DeskripsiText(keyDesText(), smesek()).dapatkanTextAsli());
        }
    }

    public String getKeyAssets() {
        return keyDesAssets();
    }


    public String getAdInterstitial() {
        Log.d("enskripsi getAdInter", new DeskripsiText(keyDesText(), adInterstitial()).dapatkanTextAsli());
        return new DeskripsiText(keyDesText(), adInterstitial()).dapatkanTextAsli();
    }

    public String getAdNativeList() {
        Log.d("enskripsi nativeList", new DeskripsiText(keyDesText(), adNativeList()).dapatkanTextAsli());
        return new DeskripsiText(keyDesText(), adNativeList()).dapatkanTextAsli();
    }

    public String getAdNativePembuka() {
        Log.d("enskripsi nativePembuka", new DeskripsiText(keyDesText(), adNativePembuka()).dapatkanTextAsli());
        return new DeskripsiText(keyDesText(), adNativePembuka()).dapatkanTextAsli();
    }

    public String getAdNativeMenu() {
        Log.d("enskripsi nativeMenu", new DeskripsiText(keyDesText(), adNativeMenu()).dapatkanTextAsli());
        return new DeskripsiText(keyDesText(), adNativeMenu()).dapatkanTextAsli();
    }

    public String getStartAppId() {
        Log.d("enskripsi startAppId", new DeskripsiText(keyDesText(), startAppId()).dapatkanTextAsli());
        return new DeskripsiText(keyDesText(), startAppId()).dapatkanTextAsli();
    }

    public native String packageName();
    public native String keyDesText();
    public native String keyDesAssets();
    public native String adInterstitial();
    public native String adNativeList();
    public native String adNativePembuka();
    public native String adNativeMenu();
    public native String startAppId();
    public native String smesek();

}



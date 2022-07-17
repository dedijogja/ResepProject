package id.varianresep.bantu;


import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManageBookmarkResep {
    private Context context;
    private int kodeUnik = 1999;  //maksimum item yang bisa di bookmark
    public ManageBookmarkResep(Context context){
        this.context = context;
    }

    public void simpanKeFavorit(int index){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt("index_"+index, index).apply();
        Toast.makeText(context, "Succesfully added to bookmark list", Toast.LENGTH_SHORT).show();
    }

    public void hapusDariFavorit(int index){
        PreferenceManager.getDefaultSharedPreferences(context).edit().remove("index_"+index).apply();
        Toast.makeText(context, "Removed from bookmark list", Toast.LENGTH_SHORT).show();

    }

    public boolean dataBelumAda(int index){
        int hasil = PreferenceManager.getDefaultSharedPreferences(context).getInt("index_"+index, kodeUnik);
        return hasil == kodeUnik;
    }

    public List<Integer> getDataFavorit() throws IOException {
        List<Integer> semuaDataFavorit = new ArrayList<>();
        for(int i = 0; i< Constant.listSemuaResep(context).length; i++) {
            int data = PreferenceManager.getDefaultSharedPreferences(context).getInt("index_"+i, kodeUnik);
            if(data != kodeUnik){
                semuaDataFavorit.add(data);
            }
        }
        return semuaDataFavorit;
    }
}

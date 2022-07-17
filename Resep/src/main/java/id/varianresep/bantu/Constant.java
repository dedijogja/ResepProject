package id.varianresep.bantu;


import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Constant {

    public static String namaDevId = "Varian+Resep";

    public static String[] listSemuaResep(Context context) throws IOException {
        List<String> modelist = new ArrayList<>();
        String[] fileNames = context.getAssets().list(Constant.namaFolderResep);
        Collections.addAll(modelist, fileNames);
        String[] dap = new String[modelist.size()];
        for(int i = 0; i<modelist.size(); i++){
            dap[i] = modelist.get(i);
        }
        return dap;
    }

    public static String gagalLoadIklan = "gagalload";
    public static String berhasilLoadIklan = "berhasilload";
    public static String kodeIndexResep = "posisi";
    public static String namaFolderResep = "resep";
    public static String namaFolderGambar = "gambar";

}

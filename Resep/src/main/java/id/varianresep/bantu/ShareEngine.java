package id.varianresep.bantu;


import android.content.Context;
import android.content.Intent;

import id.varianresep.R;
import id.varianresep.model.ResepModel;

public class ShareEngine {
    private String textPromosi;
    private ResepModel resepModel;
    private Context context;
    private String judulResep;

    public ShareEngine(Context context, ResepModel resepModel, String judulResep){
        this.resepModel = resepModel;
        this.context = context;
        this.judulResep = judulResep;

        textPromosi = "\nResep masakan ini dibagikan melalui aplikasi \""
                + context.getResources().getString(R.string.app_name)
                + "\" miliki aplikasi ini secara gratis melalui link berikut " + "https://play.google.com/store/apps/details?id="
                + context.getPackageName();
    }

    private String getTextShare(){
        String retunan = "";
        retunan += judulResep + "\n";
        retunan += "\n";
        retunan += "Perkiraan waktu : "+ resepModel.getHasilModel().getJumlahWaktu()+ " Menit" +"\n";
        retunan += "Perkiraan Biaya : "+ resepModel.getHasilModel().getJumlahBiaya()+ " Rupiah"+"\n";
        retunan += "Jumlah Porsi    : "+ resepModel.getHasilModel().getJumlahPorsi()+ " Orang" +"\n";
        retunan += "\n";
        retunan += resepModel.getBahanModels().get(0).getJudulBahan()+"\n";
        for(int i = 0; i<resepModel.getBahanModels().get(0).getDaftarBahan().size(); i++){
            retunan += (i+1) + ". " + resepModel.getBahanModels().get(0).getDaftarBahan().get(i) + "\n";
        }
        retunan += "\n";
        retunan += "*Note: Untuk resep dan bahan selengkapnya silakan download aplikasi melalui link dibawah" + "\n";
        retunan += "\n";
        retunan += resepModel.getCaraMasakModels().get(0).getJudulCaraMasak() + "\n";
        for(int i = 0; i<resepModel.getCaraMasakModels().get(0).getDaftarCaramasak().size(); i++){
            retunan += (i+1) + ". " + resepModel.getCaraMasakModels().get(0).getDaftarCaramasak().get(i) + "\n";
        }
        retunan += "\n";
        retunan += textPromosi;

        return retunan;
    }

    public void bagikanResep(){
        String shareBody = getTextShare();
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
//        /sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, judulResep);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        context.startActivity(Intent.createChooser(sharingIntent, "Bagikan melalui..."));
    }

}

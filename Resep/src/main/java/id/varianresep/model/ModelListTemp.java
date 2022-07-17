package id.varianresep.model;


import android.graphics.Bitmap;

public class ModelListTemp{
    private String waktu;
    private String porsi;
    private String biaya;
    private Bitmap bitmap;

    public ModelListTemp(String waktu, String porsi, String biaya, Bitmap bitmap) {
        this.waktu = waktu;
        this.porsi = porsi;
        this.biaya = biaya;
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getWaktu() {
        return waktu;
    }

    public String getPorsi() {
        return porsi;
    }

    public String getBiaya() {
        return biaya;
    }
}

package id.varianresep.model;


import java.util.ArrayList;

public class BahanModel{
    private String judulBahan;
    private ArrayList<String> daftarBahan = new ArrayList<>();

    public BahanModel() {
    }

    public void setJudulBahan(String judulBahan) {
        this.judulBahan = judulBahan;
    }

    public void setDaftarBahan(ArrayList<String> daftarBahan) {
        this.daftarBahan = daftarBahan;
    }

    public String getJudulBahan() {
        return judulBahan;
    }

    public ArrayList<String> getDaftarBahan() {
        return daftarBahan;
    }
}

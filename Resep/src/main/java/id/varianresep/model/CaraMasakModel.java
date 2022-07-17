package id.varianresep.model;


import java.util.ArrayList;

public class CaraMasakModel {
    private String judulCaraMasak;
    private ArrayList<String> daftarCaramasak = new ArrayList<>();

    public CaraMasakModel() {
    }

    public void setJudulCaraMasak(String judulCaraMasak) {
        this.judulCaraMasak = judulCaraMasak;
    }

    public void setDaftarCaramasak(ArrayList<String> daftarCaramasak) {
        this.daftarCaramasak = daftarCaramasak;
    }

    public String getJudulCaraMasak() {
        return judulCaraMasak;
    }

    public ArrayList<String> getDaftarCaramasak() {
        return daftarCaramasak;
    }
}

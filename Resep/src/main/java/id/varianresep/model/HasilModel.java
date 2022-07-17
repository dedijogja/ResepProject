package id.varianresep.model;


public class HasilModel{
    private String jumlahPorsi;
    private String jumlahWaktu;
    private String jumlahBiaya;

    public HasilModel() {
    }

    public void setJumlahPorsi(String jumlahPorsi) {
        this.jumlahPorsi = jumlahPorsi;
    }

    public void setJumlahWaktu(String jumlahWaktu) {
        this.jumlahWaktu = jumlahWaktu;
    }

    public void setJumlahBiaya(String jumlahBiaya) {
        this.jumlahBiaya = jumlahBiaya;
    }

    public String getJumlahPorsi() {
        return jumlahPorsi;
    }

    public String getJumlahWaktu() {
        return jumlahWaktu;
    }

    public String getJumlahBiaya() {
        return jumlahBiaya;
    }
}

package id.varianresep.model;


import java.util.ArrayList;

public class EngineModel {
    private String[] resep;
    private static final String GAMBAR = "#gambar:";
    private static final String KIRI = "#kiri:";
    private static final String TENGAH = "#tengah:";
    private static final String KANAN = "#kanan:";

    private static final String WAKTU = "*waktu:";
    private static final String BIAYA = "*biaya:";
    private static final String PORSI = "*porsi:";

    private String aktifSaatIni = "";

    public EngineModel(String[] resep) {
        this.resep = resep;
    }

    public ResepModel getResepModel(){
        ResepModel resepModel = new ResepModel();
        //hasil
        HasilModel hasilModel = new HasilModel();
        //bahan
        BahanModel bahanModel = null;
        ArrayList<String> listBahan = null;
        //cara
        CaraMasakModel caraMasakModel = null;
        ArrayList<String> listCaraMasak = null;

        for(String r : resep){
            if(r.startsWith(GAMBAR)){
                aktifSaatIni = GAMBAR;
            }else if(r.startsWith(KIRI)){
                aktifSaatIni = KIRI;
            }else if(r.startsWith(TENGAH)){
                aktifSaatIni = TENGAH;
            }else if(r.startsWith(KANAN)){
                aktifSaatIni = KANAN;
            }

            if(r.length()>1) {
                if(aktifSaatIni.equals(GAMBAR)) {
                    resepModel.setJudulResep(r.substring(GAMBAR.length(), r.length()-1));
                }else if(aktifSaatIni.equals(KIRI)){
                    if(r.startsWith(KIRI)){
                        bahanModel = new BahanModel();
                        listBahan = new ArrayList<>();
                        bahanModel.setJudulBahan(r.substring(KIRI.length(), r.length()-1));
                    }else{
                        if(listBahan!=null) {
                            listBahan.add(r);
                        }
                    }
                    //set bahan
                    if(bahanModel!=null) {
                        bahanModel.setDaftarBahan(listBahan);
                        resepModel.tambahkanBahan(bahanModel);
                    }
                }else if(aktifSaatIni.equals(TENGAH)){
                    if(!r.startsWith(TENGAH)){
                        if(r.startsWith(WAKTU)){
                            hasilModel.setJumlahWaktu(r.substring(WAKTU.length(), r.length()-1));
                        }else if(r.startsWith(PORSI)){
                            hasilModel.setJumlahPorsi(r.substring(PORSI.length(), r.length()-1));
                        }else if(r.startsWith(BIAYA)){
                            hasilModel.setJumlahBiaya(r.substring(BIAYA.length(), r.length()-1));
                        }
                    }
                }else if(aktifSaatIni.equals(KANAN)){
                    if(r.startsWith(KANAN)){
                        caraMasakModel= new CaraMasakModel();
                        listCaraMasak = new ArrayList<>();
                        caraMasakModel.setJudulCaraMasak(r.substring(KANAN.length(), r.length()-1));
                    }else{
                        if(listCaraMasak!=null) {
                            listCaraMasak.add(r);
                        }
                    }
                    //set cara masak
                    if(caraMasakModel!=null) {
                        caraMasakModel.setDaftarCaramasak(listCaraMasak);
                        resepModel.tambahkanCara(caraMasakModel);
                    }
                }
            }
        }

        //set Judul
        resepModel.setHasilModel(hasilModel);

        return resepModel;
    }

   
}

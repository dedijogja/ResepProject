package id.varianresep.model;

import java.util.ArrayList;
import java.util.List;

public class ResepModel{
    private String gambarResep;
    private List<BahanModel> bahanModels = new ArrayList<>();
    private List<CaraMasakModel> caraMasakModels = new ArrayList<>();
    private HasilModel hasilModel;

    public ResepModel(){

    }

    public void setJudulResep(String gambarResep) {
        this.gambarResep = gambarResep;
    }

    public void tambahkanBahan(BahanModel bahanModel) {
        if(bahanModels.size()==0) {
            bahanModels.add(bahanModel);
        }else if(!bahanModels.get(bahanModels.size()-1).getJudulBahan().equals(bahanModel.getJudulBahan())){
            bahanModels.add(bahanModel);
        }
    }

    public void tambahkanCara(CaraMasakModel caraMasakModel){
        if(caraMasakModels.size()==0){
            caraMasakModels.add(caraMasakModel);
        }else if(!caraMasakModels.get(caraMasakModels.size()-1).getJudulCaraMasak().equals(caraMasakModel.getJudulCaraMasak())){
            caraMasakModels.add(caraMasakModel);
        }
    }

    public void setHasilModel(HasilModel hasilModel) {
        this.hasilModel = hasilModel;
    }

    public String getGambarResep() {
        return gambarResep;
    }

    public List<BahanModel> getBahanModels() {
        return bahanModels;
    }

    public List<CaraMasakModel> getCaraMasakModels() {
        return caraMasakModels;
    }

    public HasilModel getHasilModel() {
        return hasilModel;
    }
}

package id.varianresep.model;


public class PenampungListModelSerial{
    private ModelListTemp[] listModelList;

    public PenampungListModelSerial(ModelListTemp[] listModelList) {
        this.listModelList = listModelList;
    }

    public ModelListTemp[] getListModelList() {
        return listModelList;
    }
}

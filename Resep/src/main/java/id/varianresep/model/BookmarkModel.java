package id.varianresep.model;


public class BookmarkModel {
    private ModelListTemp modelListTemp;
    private String judulResep;
    private int realIndexBookmark;

    public BookmarkModel(ModelListTemp modelListTemp, String judulResep, int realIndexBookmark) {
        this.modelListTemp = modelListTemp;
        this.judulResep = judulResep;
        this.realIndexBookmark = realIndexBookmark;
    }

    public ModelListTemp getModelListTemp() {
        return modelListTemp;
    }

    public String getJudulResep() {
        return judulResep;
    }

    public int getRealIndexBookmark() {
        return realIndexBookmark;
    }
}

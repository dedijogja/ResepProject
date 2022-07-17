package id.varianresep.dekrip.assets;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.util.Base64;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import id.varianresep.bantu.Constant;
import id.varianresep.model.EngineModel;
import id.varianresep.model.ModelListTemp;
import id.varianresep.model.PenampungListModelSerial;

public class DekripResepList extends AsyncTask<Void, Void, ModelListTemp[]>{

    private static Context context;
    private static String key = "";

    private ListenerDecrypt listenerDecrypt;
    private String formatGambar = ".sep";

    private ProgressDialog dialog;

    public DekripResepList(Context context, String key){
        DekripResepList.context = context;
        DekripResepList.key = key;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, "Reading recipe",
                "Please wait ...", true);
        dialog.show();
    }



    @Override
    protected ModelListTemp[] doInBackground(Void... params) {
        ModelListTemp[] resepModel = null;
        String[] lines = null;
        String gambar = "";
        try {
            String[] semuaResep = Constant.listSemuaResep(context);
            resepModel = new ModelListTemp[semuaResep.length];
            for(int i = 0 ; i<semuaResep.length; i++){
                SecretKey baru =  new SecretKeySpec(Base64.decode(key, Base64.DEFAULT),
                        0, Base64.decode(key, Base64.DEFAULT).length, "AES");
                try {
                    InputStream inputStream = context.getAssets().open(Constant.namaFolderResep + "/" + semuaResep[i]);
                    byte[] bytes = new byte[inputStream.available()];
                    inputStream.read(bytes);
                    inputStream.close();

                    Cipher AesCipher = Cipher.getInstance("AES");
                    AesCipher.init(Cipher.DECRYPT_MODE, baru);
                    byte[] byteResult = AesCipher.doFinal(bytes);

                    lines = new String(byteResult).split(System.getProperty("line.separator"));
                    gambar = new EngineModel(lines).getResepModel().getGambarResep();

                } catch (NoSuchAlgorithmException | IllegalBlockSizeException | InvalidKeyException | IOException | BadPaddingException | NoSuchPaddingException e) {
                    e.printStackTrace();
                }
                try {
                    InputStream inputStream = context.getAssets().open(Constant.namaFolderGambar+"/"+gambar+formatGambar);
                    byte[] bytes = new byte[inputStream.available()];
                    inputStream.read(bytes);
                    inputStream.close();

                    Cipher AesCipher = Cipher.getInstance("AES");
                    AesCipher.init(Cipher.DECRYPT_MODE, baru);
                    byte[] bytes1 = AesCipher.doFinal(bytes);
                    Bitmap mbitmap =  ThumbnailUtils.extractThumbnail(BitmapFactory.decodeByteArray(bytes1, 0, bytes1.length), 75, 75);
                    Bitmap imageRounded = Bitmap.createBitmap(mbitmap.getWidth(), mbitmap.getHeight(), mbitmap.getConfig());
                    Canvas canvas = new Canvas(imageRounded);
                    Paint mpaint = new Paint();
                    mpaint.setAntiAlias(true);
                    mpaint.setShader(new BitmapShader(mbitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
                    canvas.drawRoundRect((new RectF(0, 0, mbitmap.getWidth(), mbitmap.getHeight())), 10, 10, mpaint);

                    ModelListTemp modelListTemp = new ModelListTemp(
                            new EngineModel(lines).getResepModel().getHasilModel().getJumlahWaktu(),
                            new EngineModel(lines).getResepModel().getHasilModel().getJumlahPorsi(),
                            new EngineModel(lines).getResepModel().getHasilModel().getJumlahBiaya(),
                            imageRounded);
                    resepModel[i] = modelListTemp;

                } catch (NoSuchAlgorithmException | IllegalBlockSizeException | InvalidKeyException | IOException | BadPaddingException | NoSuchPaddingException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resepModel;
    }


    @Override
    protected void onPostExecute(ModelListTemp[] resepModels) {
        dialog.dismiss();
        PenampungListModelSerial penampungListModelSerial = new PenampungListModelSerial(resepModels);
        listenerDecrypt.onSelesaiDecrypt(penampungListModelSerial);
    }

    public void setListenerDecrypt(ListenerDecrypt listenerDecrypts){
        if(listenerDecrypt == null){
            this.listenerDecrypt = listenerDecrypts;
        }
    }

    public interface ListenerDecrypt{
        void onSelesaiDecrypt(PenampungListModelSerial resepModels);
    }
}

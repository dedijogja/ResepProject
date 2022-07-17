package id.varianresep.dekrip.assets;

import android.app.ProgressDialog;
import android.content.Context;
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

public class DekripResepPembukaResep extends AsyncTask<String, Void, byte[][]>{
    private static Context context;
    private static String key = "";
    private byte[][] byteResult = new byte[2][];

    private ProgressDialog dialog;
    private ListenerDecrypt listenerDecrypt;

    private String formatGambar = ".sep";

    public DekripResepPembukaResep(Context context, String key){
        DekripResepPembukaResep.context = context;
        DekripResepPembukaResep.key = key;
    }


    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, "Reading recipe",
                "Please wait ...", true);
        dialog.show();
    }

    @Override
    protected byte[][] doInBackground(String... params) {
        SecretKey baru =  new SecretKeySpec(Base64.decode(key, Base64.DEFAULT),
                0, Base64.decode(key, Base64.DEFAULT).length, "AES");

        String gambar = "";
        try {
            InputStream inputStream = context.getAssets().open(Constant.namaFolderResep + "/" + params[0]);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            inputStream.close();

            Cipher AesCipher = Cipher.getInstance("AES");
            AesCipher.init(Cipher.DECRYPT_MODE, baru);
            byteResult[0] = AesCipher.doFinal(bytes);

            String[] lines = new String(byteResult[0]).split(System.getProperty("line.separator"));
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
            byteResult[1] = AesCipher.doFinal(bytes);

        } catch (NoSuchAlgorithmException | IllegalBlockSizeException | InvalidKeyException | IOException | BadPaddingException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        return byteResult;
    }

    @Override
    protected void onPostExecute(byte[][] bytes) {
        dialog.dismiss();
        listenerDecrypt.onSelesaiDecrypt(bytes[0], bytes[1]);
    }

    public void setListenerDecrypt(ListenerDecrypt listenerDecrypts){
        if(listenerDecrypt == null){
            this.listenerDecrypt = listenerDecrypts;
        }
    }

    public interface ListenerDecrypt{
        void onSelesaiDecrypt(byte[] resep, byte[] gambar);
    }

}

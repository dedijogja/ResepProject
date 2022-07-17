package id.varianresep.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import id.varianresep.R;
import id.varianresep.adapter.BookmarkedResepAdapter;
import id.varianresep.bantu.Constant;
import id.varianresep.bantu.DekorAja;
import id.varianresep.bantu.KontakNative;
import id.varianresep.bantu.ManageBookmarkResep;
import id.varianresep.dekrip.assets.DekripResepBookmarked;
import id.varianresep.model.BookmarkModel;
import id.varianresep.model.PenampungListModelSerial;


public class BookmarkedResep extends AppCompatActivity{

    List<Integer> listDataBookmark = new ArrayList<>();
    private Context context = this;
    String[] listAlamatResepBookmark = null;
    RecyclerView recyclerView;
    BookmarkedResepAdapter bookmarkedResepAdapter;
    private int posisiTemp = 0;
    private List<BookmarkModel> bookmarkModel = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarked_resep);

        recyclerView = (RecyclerView) findViewById(R.id.listBookmarResep);
        bacaFavorit();
        final DekripResepBookmarked dekripResepBookmarked = new DekripResepBookmarked(this, new KontakNative(this).getKeyAssets(), listAlamatResepBookmark);
        final String[] finalListAlamatResepBookmark = listAlamatResepBookmark;
        dekripResepBookmarked.setListenerDecrypt(new DekripResepBookmarked.ListenerDecrypt() {
            @Override
            public void onSelesaiDecrypt(PenampungListModelSerial resepModels) {
                for(int i = 0; i<listDataBookmark.size(); i++){
                    bookmarkModel.add(new BookmarkModel(resepModels.getListModelList()[i], finalListAlamatResepBookmark[i], listDataBookmark.get(i)));
                }
                bookmarkedResepAdapter = new BookmarkedResepAdapter(context, bookmarkModel);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.addItemDecoration(new DekorAja(5, context));
                recyclerView.setAdapter(bookmarkedResepAdapter);
                bookmarkedResepAdapter.notifyDataSetChanged();
            }
        });
        dekripResepBookmarked.execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int kodeRequestResult = 367;
        if(requestCode == kodeRequestResult){
           if(resultCode == Activity.RESULT_OK) {
              if(data.getBooleanExtra("kodeResult", false)){
                  bookmarkModel.remove(posisiTemp);
                  bookmarkedResepAdapter.notifyItemRemoved(posisiTemp);
                  try {
                      bookmarkedResepAdapter.notifyItemRangeChanged(posisiTemp, new ManageBookmarkResep(BookmarkedResep.this).getDataFavorit().size());
                  } catch (IOException e) {
                      e.printStackTrace();
                  }
              }
           }
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void bacaFavorit(){
        ManageBookmarkResep manageBookmarkResep = new ManageBookmarkResep(this);
        try {
            listDataBookmark = manageBookmarkResep.getDataFavorit();
            listAlamatResepBookmark = new String[listDataBookmark.size()];
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] semuaAlamat = null;
        try {
            semuaAlamat = Constant.listSemuaResep(context);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i=0; i<listDataBookmark.size(); i++){
            if (listAlamatResepBookmark != null) {
                if (semuaAlamat != null) {
                    listAlamatResepBookmark[i] = semuaAlamat[listDataBookmark.get(i)];
                }
            }
        }
    }

    public void setPosisiTemp(int posisi){
        posisiTemp = posisi;
    }
}

package id.varianresep.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.varianresep.R;
import id.varianresep.activity.PembukaResep;
import id.varianresep.adapter.BumbuBahanAdapter;
import id.varianresep.adapter.CaraMasakAdapter;
import id.varianresep.bantu.DekorAja;

public class FragmentKanan extends Fragment{
    private Context context;

    public FragmentKanan() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.context = container.getContext();
        View penampung = inflater.inflate(R.layout.fragment_kanan, container, false);

        RecyclerView recyclerView = (RecyclerView) penampung.findViewById(R.id.listcaraMasak);
        CaraMasakAdapter caraMasakAdapter = new CaraMasakAdapter(context, ((PembukaResep)getActivity()).getResepModel()
                .getCaraMasakModels());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DekorAja(50, context));
        recyclerView.setAdapter(caraMasakAdapter);
        caraMasakAdapter.notifyDataSetChanged();

        return penampung;
    }
}

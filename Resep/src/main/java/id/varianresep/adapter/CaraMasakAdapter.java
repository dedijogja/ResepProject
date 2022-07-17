package id.varianresep.adapter;


import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.varianresep.R;
import id.varianresep.bantu.DekorAja;
import id.varianresep.iklan.ViewWrapper;
import id.varianresep.model.CaraMasakModel;

public class CaraMasakAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<CaraMasakModel> caraMasakModels = new ArrayList<>();

    public CaraMasakAdapter(Context context, List<CaraMasakModel> caraMasakModels) {
        this.context = context;
        this.caraMasakModels = caraMasakModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewWrapper<>(new RecyPerItem(context));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holders, int position) {
        final RecyPerItem holder = (RecyPerItem) holders.itemView;
        holder.judulBumbu.setText(caraMasakModels.get(position).getJudulCaraMasak());
        ListVertikalAdapter listVertikalAdapter = new ListVertikalAdapter(caraMasakModels.get(position).getDaftarCaramasak(), context);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        holder.recyclerView.setLayoutManager(layoutManager);
        holder.recyclerView.setItemAnimator(new DefaultItemAnimator());
        holder.recyclerView.addItemDecoration(new DekorAja(5, context));
        holder.recyclerView.setAdapter(listVertikalAdapter);
        listVertikalAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return caraMasakModels.size();
    }

    class RecyPerItem extends FrameLayout {
        private TextView judulBumbu;
        private RecyclerView recyclerView;
        public RecyPerItem(Context context) {
            super(context);
            inflate(context, R.layout.design_bumbu_bahan, this);
            judulBumbu = (TextView) findViewById(R.id.judulBumbu);
            recyclerView = (RecyclerView) findViewById(R.id.listBumbu);
        }
    }
}

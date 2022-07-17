package id.varianresep.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

import id.varianresep.R;
import id.varianresep.iklan.ViewWrapper;

public class ListVertikalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<String> daftarVertikal = new ArrayList<>();
    private Context context;

    public ListVertikalAdapter(ArrayList<String> daftarVertikal, Context context) {
        this.daftarVertikal = daftarVertikal;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewWrapper<>(new RecyPerItem(context));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holders, int position) {
        final RecyPerItem holder = (RecyPerItem) holders.itemView;
        holder.listVertikal.setText(daftarVertikal.get(position));
    }

    @Override
    public int getItemCount() {
        return daftarVertikal.size();
    }

    class RecyPerItem extends FrameLayout {
        private TextView listVertikal;
        public RecyPerItem(Context context) {
            super(context);
            inflate(context, R.layout.design_list_vertikal, this);
            listVertikal = (TextView) findViewById(R.id.listVertikal);
        }
    }
}

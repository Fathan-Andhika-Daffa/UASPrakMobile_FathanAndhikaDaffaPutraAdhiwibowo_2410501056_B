package com.example.uasprakmobile_fathanandhikadaffaputraadhiwibowo_2410501056_b;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class EndemikAdapter extends RecyclerView.Adapter<EndemikAdapter.ViewHolder> {
    private List<Endemik> list;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Endemik endemik);
    }

    public EndemikAdapter(List<Endemik> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_endemik, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Endemik item = list.get(position);
        holder.tvNama.setText(item.getNama());
        holder.tvNamaLatin.setText(item.getNamaLatin());
        holder.tvAsal.setText(item.getAsal());
        
        Glide.with(holder.itemView.getContext())
                .load(item.getFoto())
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(holder.imgEndemik);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item);
            }
        });
    }

    public void filterList(List<Endemik> filteredList) {
        this.list = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgEndemik;
        TextView tvNama, tvNamaLatin, tvAsal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgEndemik = itemView.findViewById(R.id.img_endemik);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvNamaLatin = itemView.findViewById(R.id.tv_nama_latin);
            tvAsal = itemView.findViewById(R.id.tv_asal);
        }
    }
}

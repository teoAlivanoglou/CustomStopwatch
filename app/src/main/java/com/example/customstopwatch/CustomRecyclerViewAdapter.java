package com.example.customstopwatch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customstopwatch.Stopwatch.Sector;

import java.util.ArrayList;
import java.util.List;

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.ViewHolder> {

    private List<Sector> dataObjects;
    private LayoutInflater inflater;
    private ItemClickListener clickListener;

    CustomRecyclerViewAdapter(Context context, List<Sector> dataObjects) {
        this.inflater = LayoutInflater.from(context);
        this.dataObjects = dataObjects;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_timer, parent,  false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String txt = dataObjects.get(position).toString();
        holder.txt.setText(txt);
    }

    public Sector getItem(int id) {
        return dataObjects.get(id);
    }

    @Override
    public int getItemCount() {
        return dataObjects.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txt;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt= itemView.findViewById(R.id.txt_timer);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(clickListener != null) clickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

package com.example.navigation;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    ArrayList<Image> arrayList;
    Context context;
    OnItemClickListener onItemClickListener;
    ImageView img;

    public ImageAdapter(Context context, ArrayList<Image> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(arrayList.get(position).getTitle());
        holder.date.setText(arrayList.get(position).getDate());
        img = holder.imageView;
        Glide.with(context).load(arrayList.get(position).getPath()).placeholder(R.drawable.ic_launcher_background).into(holder.imageView);
        holder.itemView.setOnClickListener(v -> onItemClickListener.onClick(v, arrayList.get(position).getPath()));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title, date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.list_item_image);
            title = itemView.findViewById(R.id.list_item_title);
            date = itemView.findViewById(R.id.list_item_date);
        }
    }

    public interface OnItemClickListener {
        void onClick(View view, String path);
    }
}
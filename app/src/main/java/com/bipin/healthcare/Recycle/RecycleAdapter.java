package com.bipin.healthcare.Recycle;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bipin.healthcare.Database.Model;
import com.bipin.healthcare.R;

import java.util.ArrayList;

public class RecycleAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private Context context;
    private ArrayList<Model> dataArrayList;


    public RecycleAdapter(Context context, ArrayList<Model> dataArrayList) {
        this.context = context;
        this.dataArrayList = dataArrayList;

//        for (int i = 0; i < this.dataArrayList.size(); i++) {
//
//        }
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_info, viewGroup, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Log.i("DataArray", this.dataArrayList.get(position).getName());
        holder.name.setText(dataArrayList.get(position).getName());
        holder.confirm.setText(dataArrayList.get(position).getConfirmed());
        holder.cChanged.setText(dataArrayList.get(position).getCtodayschange());
        holder.death.setText(dataArrayList.get(position).getDeath());
        holder.dChanged.setText(dataArrayList.get(position).getDtodayschange());
        holder.recovered.setText(dataArrayList.get(position).getRecovered());
        holder.serious.setText(dataArrayList.get(position).getSerious());
    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }
}

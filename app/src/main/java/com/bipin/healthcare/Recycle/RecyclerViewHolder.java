package com.bipin.healthcare.Recycle;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bipin.healthcare.R;


public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    TextView name,confirm,cChanged,death,dChanged,recovered,serious;
    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        name=itemView.findViewById(R.id.name);
        confirm=itemView.findViewById(R.id.confirm);
        cChanged=itemView.findViewById(R.id.cChanged);
        death=itemView.findViewById(R.id.death);
        dChanged=itemView.findViewById(R.id.dChanged);
        recovered=itemView.findViewById(R.id.recovered);
        serious=itemView.findViewById(R.id.serious);


    }
}

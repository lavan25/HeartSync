package com.example.heartsync;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView textView17;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        textView17 = itemView.findViewById(R.id.textView17);
    }
}

package com.example.todo;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    CheckBox checkbox;
    TextView textView;
    ImageButton imageButton;

    public MyViewHolder(View itemView) {
        super(itemView);
        checkbox = itemView.findViewById(R.id.checkboxTarefa);
        textView = itemView.findViewById(R.id.descTarefa);
        imageButton = itemView.findViewById(R.id.btnDeleteTarefa);
    }

}

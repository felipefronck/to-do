package com.example.todo;

import static java.nio.file.Files.size;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

    Context context;
    List<Tarefa> tarefas;

    public MyAdapter(Context context, List<Tarefa> tarefas) {
        this.context = context;
        this.tarefas = tarefas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.tarefa_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.checkbox.setChecked(tarefas.get(position).isConcluida());
        holder.textView.setText(tarefas.get(position).getDescricao());

        holder.imageButton.setOnClickListener(view -> {
            tarefas.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, tarefas.size());
        });
    }

    @Override
    public int getItemCount() {
        return tarefas.size();
    }
}

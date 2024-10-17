package com.example.todo;

import static java.nio.file.Files.size;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

    private final Context context;
    private List<Tarefa> tarefas;

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
        Tarefa tarefa = tarefas.get(position);

        holder.textView.setText(tarefas.get(position).getDescricao());
        holder.checkbox.setChecked(tarefas.get(position).isConcluida());

        verificaConclusao(tarefa, holder);

        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                alteraChecked(tarefa, checked, holder);
            }
        });

        holder.imageButton.setOnClickListener(view -> {
            tarefas.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, tarefas.size());
        });
    }

    public void atualizaLista(List<Tarefa> listaFiltrada){
        if(listaFiltrada != null){
            tarefas.clear();
            tarefas.addAll(listaFiltrada);
            notifyDataSetChanged();
        }
    }

    public void verificaConclusao(Tarefa tarefa, MyViewHolder holder){
        if (tarefa.isConcluida()) {
            holder.textView.setPaintFlags(holder.textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.textView.setPaintFlags(holder.textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }

    public void alteraChecked(Tarefa tarefa, Boolean checked, MyViewHolder holder){
        tarefa.setConcluida(checked);

        if(checked){
            holder.textView.setPaintFlags(holder.textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.textView.setPaintFlags(holder.textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }

    @Override
    public int getItemCount() {
        return tarefas.size();
    }
}

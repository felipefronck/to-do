package com.example.todo;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

    private final Context context;
    private List<Tarefa> tarefas;
    private TarefaRoomDatabase tarefaDb;

    public MyAdapter(Context context, List<Tarefa> tarefas, TarefaRoomDatabase tarefaDb) {
        this.context = context;
        this.tarefas = tarefas;
        this.tarefaDb = tarefaDb;
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

        holder.checkbox.setOnCheckedChangeListener(null);

        holder.checkbox.setChecked(tarefa.isConcluida());

        holder.checkbox.setOnCheckedChangeListener((compoundButton, checked) -> {
            alteraChecked(tarefa, checked, holder);
        });

        holder.imageButton.setOnClickListener(view -> {
            deletaItem(position);
        });
    }

    public void alteraChecked(Tarefa tarefa, Boolean checked, MyViewHolder holder){
        tarefa.setConcluida(checked);

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            tarefaDb.tarefaDao().updateTarefa(tarefa);
        });

        identaConclusao(tarefa, holder);
    }

    public void identaConclusao(Tarefa tarefa, MyViewHolder holder){
        if (tarefa.isConcluida()) {
            holder.textView.setPaintFlags(holder.textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.textView.setPaintFlags(holder.textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }

    public void deletaItem(int position){
        Tarefa tarefa = tarefas.get(position);

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            tarefaDb.tarefaDao().deletarTarefa(tarefa);
        });

        tarefas.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, tarefas.size());
    }

    public void filtraLista(List<Tarefa> listaFiltrada){
        if(listaFiltrada != null){
            tarefas.clear();
            tarefas.addAll(listaFiltrada);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return tarefas.size();
    }
}

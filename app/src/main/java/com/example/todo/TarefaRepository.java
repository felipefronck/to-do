package com.example.todo;

import android.app.Application;

import androidx.lifecycle.LiveData;
import java.util.List;

public class TarefaRepository {
    private TarefaDao tarefaDao;
    private LiveData<List<Tarefa>> buscarTodas;

    public TarefaRepository(Application aplication) {
        TarefaRoomDatabase tarefaRoomDatabase = TarefaRoomDatabase.getDatabase(aplication);
        this.tarefaDao = tarefaRoomDatabase.tarefaDao();
        this.buscarTodas = tarefaDao.buscarTodas();
    }

    LiveData<List<Tarefa>> getBuscarTodas(){
        return buscarTodas;
    }
}

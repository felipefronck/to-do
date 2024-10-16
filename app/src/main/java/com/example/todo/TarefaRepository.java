package com.example.todo;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;

public class TarefaRepository {
    private TarefaDao mTarefaDao;
    private LiveData<List<Tarefa>> mBuscarTodas;

    public TarefaRepository(Application aplication) {
        TarefaRoomDatabase db = TarefaRoomDatabase.getDatabase(aplication);
        this.mTarefaDao = db.tarefaDao();
        this.mBuscarTodas = mTarefaDao.buscarTodas();
    }

    public LiveData<List<Tarefa>> buscarTodas(){
        return mBuscarTodas;
    }

    void inserirTarefa(Tarefa tarefa){
        TarefaRoomDatabase.databaseWriteExecutor.execute(() -> {
            mTarefaDao.inserirTarefa(tarefa);
        });
    }
}

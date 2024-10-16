package com.example.todo;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TarefaViewModel extends AndroidViewModel {

    private TarefaRepository mRepository;
    private final LiveData<List<Tarefa>> mBuscarTodas;

    public TarefaViewModel(Application application) {
        super(application);
        mRepository = new TarefaRepository(application);
        mBuscarTodas = mRepository.buscarTodas();
    }

    public LiveData<List<Tarefa>> getmBuscarTodas() {
        return mBuscarTodas;
    }

    public void inserirTarefa(Tarefa tarefa){
        mRepository.inserirTarefa(tarefa);
    }
}

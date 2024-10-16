package com.example.todo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TarefaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void inserirTarefa(Tarefa tarefa);

    @Delete
    void deletarTarefa(Tarefa tarefa);

    @Update
    void updateTarefa(Tarefa tarefa);

    @Query("SELECT * FROM tarefa_table")
    LiveData<List<Tarefa>> buscarTodas();

    @Query("SELECT * FROM tarefa_table WHERE concluida like 1")
    LiveData<List<Tarefa>> buscarConcluidas();

    @Query("SELECT * FROM tarefa_table WHERE concluida like 0")
    LiveData<List<Tarefa>> buscarInconcluidas();
}

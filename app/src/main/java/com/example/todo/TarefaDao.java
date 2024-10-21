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
    List<Tarefa> buscarTodas();

    @Query("SELECT * FROM tarefa_table WHERE concluida = 1")
    List<Tarefa> buscarConcluidas();

    @Query("SELECT * FROM tarefa_table WHERE concluida = 0")
    List<Tarefa>buscarInconcluidas();

    @Query("SELECT * FROM tarefa_table WHERE `desc` LIKE '%' || :query || '%'")
    List<Tarefa> buscarFiltradas(String query);

    @Query("SELECT * FROM tarefa_table WHERE concluida = 1 AND `desc` LIKE '%' || :query || '%'")
    List<Tarefa> buscarConcluidasQuery(String query);

    @Query("SELECT * FROM tarefa_table WHERE concluida = 0 AND `desc` LIKE '%' || :query || '%'")
    List<Tarefa>buscarInconcluidasQuery(String query);
}

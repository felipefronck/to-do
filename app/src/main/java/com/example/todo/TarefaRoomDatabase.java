package com.example.todo;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Tarefa.class}, version = 1, exportSchema = false)
public abstract class TarefaRoomDatabase extends RoomDatabase {

    public abstract TarefaDao tarefaDao();

}

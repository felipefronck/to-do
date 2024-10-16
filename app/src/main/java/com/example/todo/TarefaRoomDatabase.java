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

    private static volatile TarefaRoomDatabase instance;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

    static TarefaRoomDatabase getDatabase(final Context context){
        if(instance == null){
            synchronized (TarefaRoomDatabase.class){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            TarefaRoomDatabase.class,
                            "tarefa_database")
                            .build();
                }
            }
        }
        return instance;
    }

    public static void destruirInstance() {
        instance = null;
    }
}

package com.example.todo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "tarefa_table")
public class Tarefa implements Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "desc")
    private String descricao;
    @ColumnInfo(name = "concluida")
    private boolean concluida;

    public Tarefa(@NonNull String descricao, boolean concluida) {
        this.descricao = descricao;
        this.concluida = concluida;
    }

    public void setDescricao(@NonNull String descricao) {
        this.descricao = descricao;
    }

    @NonNull
    public String getDescricao() {
        return descricao;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    public boolean isConcluida() {
        return concluida;
    }
}

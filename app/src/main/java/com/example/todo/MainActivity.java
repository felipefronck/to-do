package com.example.todo;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private List<Tarefa> tarefas = new ArrayList<>();
    private List<Tarefa> listaInconcluidas = new ArrayList<>();;
    private List<Tarefa> listaConcluidas = new ArrayList<>();;
    private MyAdapter adapter;
    private TarefaRoomDatabase tarefaDb;
    private TarefaDao tarefaDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        tarefaDb = Room.databaseBuilder(getApplicationContext(), TarefaRoomDatabase.class, "TarefaDb")
                .build();
        tarefaDao = tarefaDb.tarefaDao();

        TextInputEditText filtro = findViewById(R.id.inputFiltros);
        ChipGroup chipGroup = findViewById(R.id.chipGroup);
        TextInputEditText descTarefaInput = findViewById(R.id.inputField);
        FloatingActionButton btnAddTarefa = findViewById(R.id.btnAddTarefa);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MyAdapter(getApplicationContext(), tarefas, tarefaDb);
        recyclerView.setAdapter(adapter);

        Executors.newSingleThreadExecutor().execute(() -> {
            iniciaLista(tarefaDao);
        });

        chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            aplicarFiltros(filtro.getText().toString(), checkedId);
        });

                filtro.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        aplicarFiltros(charSequence.toString(), chipGroup.getCheckedChipId());
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

        btnAddTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTarefa(descTarefaInput, tarefaDao);
            }
        });
    }

    public void aplicarFiltros(String filtro, int checkedId){
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            List<Tarefa> tarefasFiltradas;

            if(checkedId == R.id.btnToDo){
                tarefasFiltradas = tarefaDb.tarefaDao().buscarInconcluidasQuery(filtro);
            } else if (checkedId == R.id.btnDone){
                tarefasFiltradas = tarefaDb.tarefaDao().buscarConcluidasQuery(filtro);
            } else {
                tarefasFiltradas = tarefaDb.tarefaDao().buscarFiltradas(filtro);
            }

            runOnUiThread(() -> {
                adapter.filtraLista(tarefasFiltradas);
            });
        });
    }

    public void iniciaLista(TarefaDao tarefaDao){

        new Thread(() -> {
            List<Tarefa> todasTarefas = tarefaDb.tarefaDao().buscarTodas();

            runOnUiThread(() -> {
                tarefas.clear();
                tarefas.addAll(todasTarefas);
                adapter.notifyDataSetChanged();
            });
        }).start();
    }


    public void addTarefa(TextInputEditText descTarefaInput, TarefaDao tarefaDao){
        String descricaoTarefa = descTarefaInput.getText().toString();
        if(!descricaoTarefa.isEmpty()) {

            Tarefa novaTarefa = new Tarefa(descricaoTarefa);
            descTarefaInput.setText("");

            new Thread(() -> {
                tarefaDao.inserirTarefa(novaTarefa);
                List<Tarefa> tarefasAtualizada = tarefaDao.buscarTodas();

                runOnUiThread(() -> {
                    tarefas.clear();
                    tarefas.addAll(tarefasAtualizada);
                    adapter.notifyDataSetChanged();
                });

            }).start();

        } else{
            Toast.makeText(MainActivity.this, "Insira uma descrição para a tarefa.", Toast.LENGTH_SHORT).show();
        }
    }
}
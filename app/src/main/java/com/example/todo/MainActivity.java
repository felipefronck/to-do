package com.example.todo;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Tarefa> tarefas;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        TarefaRoomDatabase db = TarefaRoomDatabase.getDatabase(getApplicationContext());
        TarefaDao tarefaDao = db.tarefaDao();

        TextInputEditText filtro = findViewById(R.id.inputFiltros);
        Button btnToDo = findViewById(R.id.btnToDo);
        Button btnDone = findViewById(R.id.btnDone);
        Button btnAll = findViewById(R.id.btnAll);

        TextInputEditText descTarefaInput = findViewById(R.id.inputField);
        FloatingActionButton btnAddTarefa = findViewById(R.id.btnAddTarefa);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tarefas = new ArrayList<>();
        adapter = new MyAdapter(getApplicationContext(), tarefas);
        recyclerView.setAdapter(adapter);
        

        filtro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String query = charSequence.toString();

                tarefaDao.buscarFiltradas(query).observe(MainActivity.this, listaFiltrada -> {
                    adapter.atualizaLista(listaFiltrada);
                });
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

//    public void iniciaLista(){
//        tarefaDao.buscarTodas().observe(this, tarefas1 -> {
//            adapter.atualizaLista(tarefas);
//        });
//    }

    public void addTarefa(TextInputEditText descTarefaInput, TarefaDao tarefaDao){
        String descricaoTarefa = descTarefaInput.getText().toString();
        if(!descricaoTarefa.isEmpty()) {
            Tarefa novaTarefa = new Tarefa(descricaoTarefa);
            descTarefaInput.setText("");
            new Thread(() -> {
                tarefaDao.inserirTarefa(novaTarefa);
            }).start();
        } else{
            Toast.makeText(MainActivity.this, "Insira uma descrição para a tarefa.", Toast.LENGTH_SHORT).show();
        }
    }
}
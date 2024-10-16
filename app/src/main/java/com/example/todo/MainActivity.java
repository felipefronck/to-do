package com.example.todo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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

        Button btnToDo = findViewById(R.id.btnToDo);
        Button btnDone = findViewById(R.id.btnDone);
        Button btnAll = findViewById(R.id.btnAll);
        TextInputLayout textInputLayout = findViewById(R.id.inputFieldLayout);
        TextInputEditText inputField = findViewById(R.id.inputField);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        FloatingActionButton btnAddTarefa = findViewById(R.id.btnAddTarefa);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tarefas = new ArrayList<>();
        adapter = new MyAdapter(getApplicationContext(), tarefas);
        recyclerView.setAdapter(adapter);

        btnAddTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTarefa(inputField);
            }
        });
    }

    void addTarefa(TextInputEditText inputField){
        String descricaoTarefa = inputField.getText().toString();
        if(!descricaoTarefa.isEmpty()) {
            Tarefa novaTarefa = new Tarefa(descricaoTarefa);
            tarefas.add(novaTarefa);
            adapter.notifyItemInserted(tarefas.size() - 1);
            inputField.setText("");
        } else{
            Toast.makeText(MainActivity.this, "Insira uma descrição para a tarefa.", Toast.LENGTH_SHORT).show();
        }
    }




}
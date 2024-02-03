package com.esteban.eventosapp.InicioApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.esteban.eventosapp.LoginAndRegister.InicioSesion;
import com.esteban.eventosapp.Perfil.Perfil;
import com.esteban.eventosapp.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class InicioApp extends AppCompatActivity {

    RecyclerView recyclerView;
    MainAdapter mainAdapter;

    LinearLayout proifle, salirappP;

    EditText buscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_app);
        proifle = findViewById(R.id.profileBtn);
        salirappP = findViewById(R.id.salirappP);
        buscar = findViewById(R.id.buscar);

        proifle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InicioApp.this, Perfil.class);
                startActivity(intent);
            }
        });

        salirappP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cerrarSesion();
            }
        });

        recyclerView = (RecyclerView)findViewById(R.id.rv);
        // Configura el LayoutManager para mostrar las tarjetas horizontalmente
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("eventos"), MainModel.class)
                        .build();
        mainAdapter = new MainAdapter(options);
        recyclerView.setAdapter(mainAdapter);

        setupTextWatcher();
    }

    private void cerrarSesion() {
        Intent intent = new Intent(this, InicioSesion.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }

    private void setupTextWatcher() {
        buscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                textSearch(editable.toString());
            }
        });
    }

    private void textSearch(String s) {
        FirebaseRecyclerOptions<MainModel> firebaseRecyclerOptions =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("eventos").orderByChild("partido").startAt(s).endAt(s + "~"), MainModel.class)
                        .build();
        mainAdapter = new MainAdapter(firebaseRecyclerOptions);
        mainAdapter.startListening();
        recyclerView.setAdapter(mainAdapter);
    }
}
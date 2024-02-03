package com.esteban.eventosapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.esteban.eventosapp.LoginAndRegister.InicioSesion;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Utilizar un Handler para retrasar la ejecución de ciertas acciones
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Crear un intent para iniciar la actividad principal (MainActivity)
                Intent intent = new Intent(MainActivity.this, InicioSesion.class);

                // Iniciar la actividad principal después de un retraso de 2000 milisegundos (2 segundos)
                startActivity(intent);
            }
        }, 2800); // Tiempo de retraso en milisegundos
    }
}
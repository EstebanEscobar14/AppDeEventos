package com.esteban.eventosapp.Perfil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.esteban.eventosapp.InicioApp.InicioApp;
import com.esteban.eventosapp.LoginAndRegister.InicioSesion;
import com.esteban.eventosapp.R;

public class Perfil extends AppCompatActivity {
    ConstraintLayout imageView, compartir, salirapp, web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        imageView = findViewById(R.id.volverAMenuPrincipal);
        compartir = findViewById(R.id.compartirapp);
        salirapp = findViewById(R.id.salirapp);
        web = findViewById(R.id.webir);

        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // URL de la página web a la que deseas redirigir
                String url = "https://multimediaproyecto.netlify.app/";

                // Crea una intención para abrir la URL en el navegador
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Perfil.this, InicioApp.class);
                startActivity(intent);
            }
        });
        
        compartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compartirApp();
            }
        });

        salirapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cerrarSesion();
            }
        });
    }

    private void compartirApp() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String mensaje = "¡Visita La Web de esta increible aplicacion! [https://multimediaproyecto.netlify.app/]";
        intent.putExtra(Intent.EXTRA_TEXT, mensaje);
        startActivity(Intent.createChooser(intent, "Compartir con"));
    }

    private void cerrarSesion() {
        Intent intent = new Intent(this, InicioSesion.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void toastIncorrecto(String mensaje) {
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_toast_error, (ViewGroup) findViewById(R.id.ll_custom_toast_error));
        TextView txtMensaje = view.findViewById(R.id.txtMensajeToast2);
        txtMensaje.setText(mensaje);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 200);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }
}
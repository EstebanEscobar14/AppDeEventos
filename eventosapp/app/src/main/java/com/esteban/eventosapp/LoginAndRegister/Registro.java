package com.esteban.eventosapp.LoginAndRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.esteban.eventosapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registro extends AppCompatActivity {

    TextView textoInicioSesion;
    EditText campoCorreo;
    EditText campoContrasena;
    EditText campoNombre;

    private String email;
    private String nombre;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        campoCorreo = findViewById(R.id.campoCorreo);
        campoContrasena = findViewById(R.id.campoContrasena);
        textoInicioSesion = findViewById(R.id.irAInicioSesion);
        campoNombre = findViewById(R.id.campoNombre);
        mAuth = FirebaseAuth.getInstance();

        textoInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Agrega el código para iniciar la actividad de registro aquí
                Intent intent = new Intent(Registro.this, InicioSesion.class);
                startActivity(intent);
            }
        });
    }

    public void registrar(View view) {
        nombre = campoNombre.getText().toString();
        email = campoCorreo.getText().toString();
        String password = campoContrasena.getText().toString();

        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            //Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            toastIncorrecto("Por favor, completa todos los campos");
        } else {
            createAccount(email, password);
        }
    }

    private void createAccount(String email, String password) {
        this.mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((Activity) this, new OnCompleteListener<AuthResult>() {
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    toastCorrecto("Registro exitoso");
                    FirebaseUser currentUser = Registro.this.mAuth.getCurrentUser();
                    iraIniciarSesion();
                } else {
                    //Toast.makeText(Registro.this, "Error en el registro: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    toastIncorrecto("Error en el registro");
                    Log.e("Registro", "Error en el registro", task.getException());
                }
            }
        });
    }

    public void iraIniciarSesion() {
        startActivity(new Intent(this, InicioSesion.class));
    }

    public void toastCorrecto(String mensaje){
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_toast_ok, (ViewGroup) findViewById(R.id.ll_custom_toast_ok));
        TextView txtMensaje = view.findViewById(R.id.txtMensajeToast1);
        txtMensaje.setText(mensaje);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 200);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }

    public void toastIncorrecto(String mensaje){
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

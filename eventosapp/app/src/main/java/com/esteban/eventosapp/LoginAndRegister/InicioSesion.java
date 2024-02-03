package com.esteban.eventosapp.LoginAndRegister;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.esteban.eventosapp.InicioApp.InicioApp;
import com.esteban.eventosapp.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

public class InicioSesion extends AppCompatActivity {
    private EditText campoCorreo;
    private EditText campoContrasena;

    private FirebaseAuth mAuth;
    private GoogleSignInClient googleSignInClient;

    private final ActivityResultLauncher<Intent> activityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            handleGoogleSignInResult(result);
                        }
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        campoCorreo = findViewById(R.id.correo);
        campoContrasena = findViewById(R.id.contrasena);

        mAuth = FirebaseAuth.getInstance();
        FirebaseApp.initializeApp(this);

        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(InicioSesion.this, options);

        // Agrega esta línea para cerrar la sesión actual de Google
        googleSignInClient.signOut();

        AppCompatButton signInButton = findViewById(R.id.iniciarConGoogle);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGoogleSignIn();
            }
        });

        findViewById(R.id.irARegistro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegistroActivity();
            }
        });
    }

    private void startGoogleSignIn() {
        Intent intent = googleSignInClient.getSignInIntent();
        activityResultLauncher.launch(intent);
    }

    private void startRegistroActivity() {
        Intent intent = new Intent(InicioSesion.this, Registro.class);
        startActivity(intent);
    }

    private void handleGoogleSignInResult(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
            try {
                GoogleSignInAccount signInAccount = accountTask.getResult(ApiException.class);
                AuthCredential authCredential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
                mAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        handleGoogleSignInComplete(task);
                    }
                });
            } catch (ApiException e) {
                e.printStackTrace();
                toastIncorrecto("Error al iniciar sesión con Google");
            }
        }
    }

    private void handleGoogleSignInComplete(Task<AuthResult> task) {
        if (task.isSuccessful()) {
            FirebaseUser user = mAuth.getCurrentUser();
            showToast("Inicio Exitoso");
            iraIn(user);
        } else {
            toastIncorrecto("Fallo la Autenticación");
        }
    }

    public void iniciarSesion(View view) {
        String email = campoCorreo.getText().toString();
        String password = campoContrasena.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            toastIncorrecto("Por favor, completa todos los campos");
        } else {
            signIn(email, password);
        }
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    showToast("Inicio Exitoso");
                    iraIn(mAuth.getCurrentUser());
                } else {
                    toastIncorrecto("Fallo la Autenticación");
                }
            }
        });
    }

    public void iraIn(FirebaseUser user) {
        startActivity(new Intent(this, InicioApp.class));
        finish(); // Cierra la actividad actual si no se necesita mantenerla en la pila
    }

    private void showToast(String message) {
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_toast_ok, (ViewGroup) findViewById(R.id.ll_custom_toast_ok));
        TextView txtMensaje = view.findViewById(R.id.txtMensajeToast1);
        txtMensaje.setText(message);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 200);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
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

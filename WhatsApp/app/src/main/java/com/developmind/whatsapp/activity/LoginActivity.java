package com.developmind.whatsapp.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.developmind.whatsapp.R;
import com.developmind.whatsapp.config.ConfiguracaoFirebase;
import com.developmind.whatsapp.entity.Usuario;
import com.developmind.whatsapp.util.Base64Custom;
import com.developmind.whatsapp.util.Permissao;
import com.developmind.whatsapp.util.Preferencias;
import com.developmind.whatsapp.util.Utils;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.editEmail)
    EditText editEmail;

    @BindView(R.id.editSenha)
    EditText editSenha;

    @BindView(R.id.btnLogar)
    Button btnLogar;

    private Usuario usuario;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        verificarUsuarioLogado();
    }

    public void logar(View v) {
        usuario = new Usuario();
        usuario.setEmail(editEmail.getText().toString());
        usuario.setSenha(editSenha.getText().toString());
        validarLogin();
    }

    private void verificarUsuarioLogado() {
        auth = ConfiguracaoFirebase.getFirebaseAuth();
        if (auth.getCurrentUser() != null) {
            abrirTelaPrincipal();
        }
    }

    private void validarLogin() {
        auth = ConfiguracaoFirebase.getFirebaseAuth();
        auth.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Preferencias preferencias = new Preferencias(LoginActivity.this);
                    preferencias.salvarDados(Base64Custom.encodeBase64(usuario.getEmail()));
                    abrirTelaPrincipal();
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Erro: Ao fazer login, tente novamente mais tarde!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void abrirTelaPrincipal() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void abrirCadastroUsuario(View v) {
        Intent intent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
        startActivity(intent);
    }
}

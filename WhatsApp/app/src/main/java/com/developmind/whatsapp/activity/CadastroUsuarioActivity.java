package com.developmind.whatsapp.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.developmind.whatsapp.R;
import com.developmind.whatsapp.config.ConfiguracaoFirebase;
import com.developmind.whatsapp.entity.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @BindView(R.id.editNomeCadastro)
    EditText editNome;

    @BindView(R.id.editEmailCadastro)
    EditText editEmail;

    @BindView(R.id.editSenhaCadastro)
    EditText editSenha;

    @BindView(R.id.btnCadastrar)
    Button btnCadastrar;

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);
        ButterKnife.bind(this);
    }

    public void cadastrar(View v) {
        usuario = new Usuario();
        usuario.setNome(editNome.getText().toString());
        usuario.setEmail(editEmail.getText().toString());
        usuario.setSenha(editSenha.getText().toString());
        cadastrarUsuario();
    }

    public void cadastrarUsuario() {
        auth = ConfiguracaoFirebase.getFirebaseAuth();
        auth.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CadastroUsuarioActivity.this, "Usuário cadastrado com Sucesso!", Toast.LENGTH_LONG).show();
                    usuario.setId(task.getResult().getUser().getUid());
                    usuario.salvar();
                    auth.signOut();
                    finish();
                } else {
                    String erro = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erro = "Digite uma senha mais forte, contendo mais caracteres e com letras e números!";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erro = "O e-mail é inválido, digite um novo e-mail!";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erro = "Esse e-mail já está em uso no app!";
                    } catch (Exception e) {
                        erro = "Ao efetuar o cadastro!";
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroUsuarioActivity.this, "Erro: " + erro, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

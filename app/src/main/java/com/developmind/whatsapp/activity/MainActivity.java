package com.developmind.whatsapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.developmind.whatsapp.R;
import com.developmind.whatsapp.adapter.TabAdapter;
import com.developmind.whatsapp.config.ConfiguracaoFirebase;
import com.developmind.whatsapp.entity.Contato;
import com.developmind.whatsapp.entity.Usuario;
import com.developmind.whatsapp.util.Base64Custom;
import com.developmind.whatsapp.util.Constantes;
import com.developmind.whatsapp.util.Preferencias;
import com.developmind.whatsapp.util.SlidingTabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference referenciaFirebase;

    private FirebaseAuth auth;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.stlLayout)
    SlidingTabLayout slidingTabLayout;

    @BindView(R.id.vpPagina)
    ViewPager viewPager;

    private String idContato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        auth = ConfiguracaoFirebase.getFirebaseAuth();
        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);
        carregarFragments();
    }

    public void carregarFragments() {
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.colorAccent));
        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);
        slidingTabLayout.setViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemAdicionar:
                abrirCadastroContato();
                return true;
            case R.id.itemConfiguracao:
                return true;
            case R.id.itemPesquisa:
                return true;
            case R.id.itemSair:
                deslogarUsuario();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void abrirCadastroContato() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.MyDialogTheme);
        builder.setTitle("Novo Contato");
        builder.setMessage("E-mail do usuário");
        builder.setCancelable(false);
        LinearLayout layout = new LinearLayout(MainActivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER_HORIZONTAL);
        final EditText input = new EditText(this);
        input.setSingleLine(true);
        layout.setPadding(50, 0, 50, 0);
        layout.addView(input);
        builder.setView(layout);
        builder.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email = input.getText().toString();
                if (email.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Preencha o e-mail", Toast.LENGTH_SHORT).show();
                } else {
                    idContato = Base64Custom.encodeBase64(email);
                    referenciaFirebase = ConfiguracaoFirebase.getFirebaseDatabase().child(Constantes.FIREBASE_USUARIO_CHILD).child(idContato);
                    referenciaFirebase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null) {
                                Usuario usuarioContato = dataSnapshot.getValue(Usuario.class);
                                Preferencias preferencias = new Preferencias(MainActivity.this);
                                String id = preferencias.getIdentificador();
                                referenciaFirebase = ConfiguracaoFirebase.getFirebaseDatabase();
                                referenciaFirebase = referenciaFirebase.child(Constantes.FIREBASE_CONTATO_CHILD).child(id).child(idContato);
                                Contato contato = new Contato(idContato, usuarioContato.getNome(), usuarioContato.getEmail());
                                referenciaFirebase.setValue(contato);
                            } else {
                                Toast.makeText(MainActivity.this, "Usuário não possui cadastro!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create();
        builder.show();
    }

    private void deslogarUsuario() {
        auth.signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}

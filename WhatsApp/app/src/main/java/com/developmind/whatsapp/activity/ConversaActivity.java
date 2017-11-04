package com.developmind.whatsapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.developmind.whatsapp.R;
import com.google.firebase.database.DatabaseReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConversaActivity extends AppCompatActivity {

    @BindView(R.id.toolbarConversa)
    Toolbar toolbar;

    private DatabaseReference referenciaFirebase;

    private String nomeContato;

    private String emailContato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversa);
        ButterKnife.bind(this);
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            nomeContato = extra.getString("nome");
            emailContato = extra.getString("email");
        }
        toolbar.setTitle(nomeContato);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
    }
}

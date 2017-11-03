package com.developmind.whatsapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.developmind.whatsapp.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class LoginActivity extends AppCompatActivity {

    private EditText telefone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        telefone = (EditText) findViewById(R.id.editTelefone);
        SimpleMaskFormatter smfTelefone = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
        MaskTextWatcher mtwTelefone = new MaskTextWatcher(telefone, smfTelefone);
        telefone.addTextChangedListener(mtwTelefone);
    }
}

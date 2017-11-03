package com.developmind.whatsapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.developmind.whatsapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference referenciaFirebase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        referenciaFirebase.child("pontos").setValue(100);
    }
}

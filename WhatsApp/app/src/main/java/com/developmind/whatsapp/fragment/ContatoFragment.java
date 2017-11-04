package com.developmind.whatsapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.developmind.whatsapp.R;
import com.developmind.whatsapp.activity.ConversaActivity;
import com.developmind.whatsapp.adapter.ContatoAdapter;
import com.developmind.whatsapp.config.ConfiguracaoFirebase;
import com.developmind.whatsapp.entity.Contato;
import com.developmind.whatsapp.util.Constantes;
import com.developmind.whatsapp.util.Preferencias;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContatoFragment extends Fragment {

    @BindView(R.id.lvContatos)
    ListView listView;

    private ArrayAdapter adapter;

    private List<Contato> contatos;

    private DatabaseReference referenciaFirebase;

    private ValueEventListener valueEventListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contato, container, false);
        ButterKnife.bind(this, view);
        carregarContatos();
        return view;
    }

    private void carregarContatos() {
        Preferencias preferencias = new Preferencias(getActivity());
        String id = preferencias.getIdentificador();
        contatos = new ArrayList<Contato>();
        adapter = new ContatoAdapter(getActivity(), contatos);
        listView.setAdapter(adapter);
        referenciaFirebase = ConfiguracaoFirebase.getFirebaseDatabase().child(Constantes.FIREBASE_CONTATO_CHILD).child(id);
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contatos.clear();
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Contato contato = dados.getValue(Contato.class);
                    contatos.add(contato);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ConversaActivity.class);
                Contato contato = contatos.get(position);
                intent.putExtra("nome", contato.getNome());
                intent.putExtra("email", contato.getEmail());
                startActivity(intent);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        referenciaFirebase.addValueEventListener(valueEventListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        referenciaFirebase.removeEventListener(valueEventListener);
    }
}

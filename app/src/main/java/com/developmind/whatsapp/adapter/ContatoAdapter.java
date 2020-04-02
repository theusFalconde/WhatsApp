package com.developmind.whatsapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.developmind.whatsapp.R;
import com.developmind.whatsapp.entity.Contato;

import java.util.List;

public class ContatoAdapter extends ArrayAdapter<Contato> {

    private List<Contato> contatos;

    private Context context;

    public ContatoAdapter(@NonNull Context context, @NonNull List<Contato> contatos) {
        super(context, 0, contatos);
        this.contatos = contatos;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;
        if (contatos != null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.lista_contato, parent, false);
            Contato contato = contatos.get(position);
            TextView nome = view.findViewById(R.id.tvNome);
            TextView email = view.findViewById(R.id.tvEmail);
            nome.setText(contato.getNome());
            email.setText(contato.getEmail());
        }
        return view;
    }
}

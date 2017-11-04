package com.developmind.whatsapp.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class Preferencias {

    private Context context;

    private SharedPreferences preferences;

    private static final String NOME_ARQUIVO = "whatsapp.preferencias";

    private SharedPreferences.Editor editor;

    public static final String CHAVE_NOME = "nome";

    public static final String CHAVE_TELEFONE = "telefone";

    public static final String CHAVE_TOKEN = "token";

    public Preferencias(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(NOME_ARQUIVO, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void salvarUsuario(String nome, String telefone, String token) {
        editor.putString(CHAVE_NOME, nome);
        editor.putString(CHAVE_TELEFONE, telefone);
        editor.putString(CHAVE_TOKEN, token);
        editor.commit();
    }

    public Map<String, String> getDadosUsuario() {
        Map<String, String> dadosUsuario = new HashMap<String, String>();
        dadosUsuario.put(CHAVE_NOME, preferences.getString(CHAVE_NOME, null));
        dadosUsuario.put(CHAVE_TELEFONE, preferences.getString(CHAVE_NOME, null));
        dadosUsuario.put(CHAVE_TOKEN, preferences.getString(CHAVE_NOME, null));
        return dadosUsuario;
    }
}

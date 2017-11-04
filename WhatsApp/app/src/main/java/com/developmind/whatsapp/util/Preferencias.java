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

    public static final String CHAVE_IDENTIFICADOR = "identificador";

    public Preferencias(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(NOME_ARQUIVO, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void salvarDados(String identificador) {
        editor.putString(CHAVE_IDENTIFICADOR, identificador);
        editor.commit();
    }

    public String getIdentificador() {
        return preferences.getString(CHAVE_IDENTIFICADOR, null);
    }

}

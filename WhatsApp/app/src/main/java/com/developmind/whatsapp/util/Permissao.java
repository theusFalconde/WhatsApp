package com.developmind.whatsapp.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissao {

    public static boolean validarPermissoes(int requestCode, Activity activity, String[] permissoes) {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> listaPermissao = new ArrayList<>();
            for (String permissao : permissoes) {
                if (ContextCompat.checkSelfPermission(activity, permissao) != PackageManager.PERMISSION_GRANTED) {
                    listaPermissao.add(permissao);
                }
            }
            if (!listaPermissao.isEmpty()) {
                String[] novasPermissoes = new String[listaPermissao.size()];
                listaPermissao.toArray(novasPermissoes);
                ActivityCompat.requestPermissions(activity, novasPermissoes, requestCode);
            }
        }
        return true;
    }
}

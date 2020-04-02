package com.developmind.whatsapp.util;

import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class Utils {
    public static String replaceCaracters(String text, String... caracteresReplace) {
        for (String c : caracteresReplace) {
            text = text.replace(c, "");
        }
        return text;
    }

    public static void setMascara(EditText edit, String pattern) {
        SimpleMaskFormatter simpleMaskFormatter = new SimpleMaskFormatter(pattern);
        MaskTextWatcher maskTextWatcher = new MaskTextWatcher(edit, simpleMaskFormatter);
        edit.addTextChangedListener(maskTextWatcher);
    }
}

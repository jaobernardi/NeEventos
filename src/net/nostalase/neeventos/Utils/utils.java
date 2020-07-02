package net.nostalase.neeventos.Utils;

import java.text.Normalizer;

public class utils {
    public static String removeSC(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }
}

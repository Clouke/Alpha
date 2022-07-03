package gg.clouke.alpha.util.chat;

/**
 * @author Clouke
 * @since 01.06.2022 18:13
 * © Alpha - All Rights Reserved
 */
public interface CC {

    String LINE = "&7&m---------------------------------";

    static String translate(String s) {
        return s.replaceAll("&", "§");
    }

}

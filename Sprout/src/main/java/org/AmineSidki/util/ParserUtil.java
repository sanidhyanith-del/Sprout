package org.AmineSidki.util;

public class ParserUtil {
    public static String getPackageName(String entityPackageName){
        if(entityPackageName.endsWith(".entity")) {
            return entityPackageName.substring(0, entityPackageName.lastIndexOf(".entity"));
        }
        return entityPackageName;
    }
}

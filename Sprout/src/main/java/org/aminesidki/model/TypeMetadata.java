package org.aminesidki.model;

public record TypeMetadata (String regularName,
                            String fullQualifiedName)
{
    public boolean isImportNeeded(){
        return (!fullQualifiedName().startsWith("java.lang."))
                || fullQualifiedName().substring(10).contains(".");
    }
}

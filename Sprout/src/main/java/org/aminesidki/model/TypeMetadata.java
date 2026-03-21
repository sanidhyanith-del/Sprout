package org.aminesidki.model;

/**
 * @param regularName contains class name of type
 * @param fullQualifiedName contains package + class name of the type
 */
public record TypeMetadata (String regularName,
                            String fullQualifiedName)
{
    //Checks if an import is needed by excluding java.lang.<class> packages, and allowing subclasses
    public boolean isImportNeeded(){
        try{
            return !fullQualifiedName.isEmpty() && !fullQualifiedName().startsWith("java.lang.")
                    || fullQualifiedName().substring(10).contains(".");
        } catch (Exception e) {
            return false;
        }
    }
}

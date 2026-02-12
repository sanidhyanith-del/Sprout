package org.AmineSidki.generator.ImportsGenerator;

import org.AmineSidki.generator.SproutImportsGenerator;
import org.AmineSidki.model.EntityMetadata;
import org.AmineSidki.model.HelperMetadata;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;

public class GenericImportsGenerator implements SproutImportsGenerator {

    @Override
    public HashSet<String> generate(EntityMetadata entityMetadata, Map<String, EntityMetadata> persistenceMap, Map<String, HelperMetadata> helperMap) {
        if((!entityMetadata.id().type().getFullQualifiedName().startsWith("java.lang.")
                || entityMetadata.id().type().getFullQualifiedName().substring(10).contains("."))) {
            return new HashSet<>(Collections.singleton(entityMetadata.id().type().getFullQualifiedName()));
        }
        return new HashSet<>();
    }
}

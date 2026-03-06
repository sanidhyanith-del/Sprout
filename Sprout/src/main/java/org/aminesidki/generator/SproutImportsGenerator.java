package org.aminesidki.generator;

import org.aminesidki.model.EntityMetadata;
import org.aminesidki.model.HelperMetadata;

import java.util.HashSet;
import java.util.Map;

public interface SproutImportsGenerator {
    HashSet<String> generate(EntityMetadata entityMetadata, Map<String, EntityMetadata> persistenceMap , Map<String, HelperMetadata> helperMap );
}

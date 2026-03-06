package org.aminesidki.model;

import java.util.List;

public record EntityMetadata(String packageName,
                             String className,
                             FieldMetadata id,
                             boolean hasLightDTO,
                             boolean isPaginated,
                             boolean isIgnored,
                             List<FieldMetadata> fields,
                             List<FieldMetadata> lightFields) {
}

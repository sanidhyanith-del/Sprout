package org.AmineSidki.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.LinkedHashMap;

@Getter
@AllArgsConstructor
public class EntityMetadata {
    final String packageName , className , idType;
    final LinkedHashMap<String , String> fields;
}

package org.aminesidki.model;

import org.aminesidki.enumeration.Association;

public record FieldMetadata(TypeMetadata type, String name, Association association) {
}

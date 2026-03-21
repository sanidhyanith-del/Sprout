package org.aminesidki.generator;

import org.aminesidki.exception.FileSystemException;
import org.aminesidki.model.EntityMetadata;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Generates java dependencies for given entity
 */
public interface SproutDependencyGenerator {
    HashSet<String> generate(EntityMetadata entityMetadata , Set<String> imports) throws IOException , FileSystemException;
}

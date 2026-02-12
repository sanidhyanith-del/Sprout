package org.AmineSidki.generator;

import org.AmineSidki.exception.FileSystemException;
import org.AmineSidki.model.EntityMetadata;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public interface SproutDependencyGenerator {
    HashSet<String> generate(EntityMetadata entityMetadata , Set<String> imports) throws IOException , FileSystemException;
}

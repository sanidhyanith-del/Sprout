package org.AmineSidki;

import com.github.javaparser.JavaParser;
import org.AmineSidki.model.EntityMetadata;
import org.AmineSidki.util.EntityParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.*;

public class EntityParserTest {
    private JavaParser parser;
    private File tempEntityFile;

    @Before
    public void setUp() {
        parser = new JavaParser();
        // Create a physical file for testing
        tempEntityFile = new File("TestUser.java");
    }

    @After
    public void tearDown() {
        if (tempEntityFile.exists()) {
            tempEntityFile.delete();
        }
    }

    @Test
    public void parse_ShouldExtractCorrectMetadata() throws IOException {
        // Arrange: Write a valid Java Entity to the temp file
        try (FileWriter writer = new FileWriter(tempEntityFile)) {
            writer.write("package com.amine.entity;\n" +
                    "import jakarta.persistence.Id;\n" +
                    "public class TestUser {\n" +
                    "    @Id private Long id;\n" +
                    "    private String email;\n" +
                    "}");
        }

        // Act
        EntityMetadata metadata = EntityParser.parse(parser, tempEntityFile);

        // Assert
        assertEquals("TestUser", metadata.getClassName());
        assertEquals("Long", metadata.getIdType());
        assertEquals("com.amine", metadata.getPackageName());
        assertTrue("Metadata should contain 'email' field", metadata.getFields().containsKey("email"));
        assertEquals("String", metadata.getFields().get("email"));
    }

    @Test(expected = RuntimeException.class)
    public void parse_ShouldThrowException_WhenIdAnnotationIsMissing() throws IOException {
        // Arrange: Entity without @Id
        try (FileWriter writer = new FileWriter(tempEntityFile)) {
            writer.write("package com.amine.entity; public class InvalidEntity { private String name; }");
        }

        // Act
        EntityParser.parse(parser, tempEntityFile);
    }
}
package org.aminesidki.parser;

import com.github.javaparser.ast.CompilationUnit;
import org.aminesidki.exception.ParsingException;

import java.io.FileNotFoundException;

public interface SproutParser<T> {
    T parse(CompilationUnit cu , String entity) throws FileNotFoundException , ParsingException;
}

package org.AmineSidki.parser;

import com.github.javaparser.ast.CompilationUnit;
import org.AmineSidki.exception.ParsingException;

import java.io.FileNotFoundException;

public interface SproutParser<T> {
    T parse(CompilationUnit cu , String entity) throws FileNotFoundException , ParsingException;
}

package org.AmineSidki.runnable;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import org.AmineSidki.generator.RepositoryGenerator;
import org.AmineSidki.model.EntityMetadata;
import org.AmineSidki.util.EntityParser;
import org.AmineSidki.util.ParserUtil;
import picocli.CommandLine;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

@CommandLine.Command(name="default" , version = "0.1" , description = "Default Sprout workflow")
public class DefaultRunnable implements Runnable{

    @CommandLine.Option(names = "-d , --dir")
    private String defaultDir = ".";

    @Override
    public void run() {

        File entityPackage = new File(defaultDir + "/entity");
        File[] files = entityPackage.listFiles();

        if(files == null){
            throw new RuntimeException("Couldn't resolve entity package directory !");
        }

        JavaParser parser = new JavaParser();
        MustacheFactory mf = new DefaultMustacheFactory();
        RepositoryGenerator repoGen = new RepositoryGenerator();

        //Compiling templates
        Mustache repoMustache = mf.compile("templates/RepositoryTemplate.mustache");

        for(File entity : files){
            if(!entity.isFile()){
                continue;
            }

            try {
                //Parsing the code
                EntityMetadata em = EntityParser.parse(parser , entity);
                repoGen.generate(em , repoMustache , defaultDir);

            } catch (FileNotFoundException e) {
                throw new RuntimeException("An error occurred whilst reading files.");
            } catch (IOException e) {
                throw new RuntimeException("An error occurred whilst generating files.");
            }
        }

    }
}

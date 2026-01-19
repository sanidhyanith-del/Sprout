package org.AmineSidki.runnable;

import com.github.javaparser.JavaParser;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import org.AmineSidki.generator.DtoGenerator;
import org.AmineSidki.generator.MapperGenerator;
import org.AmineSidki.generator.RepositoryGenerator;
import org.AmineSidki.generator.ServiceGenerator;
import org.AmineSidki.model.EntityMetadata;
import org.AmineSidki.util.BannerPrinter;
import org.AmineSidki.util.EntityParser;
import picocli.CommandLine;

import java.io.*;
import java.time.LocalDateTime;

@CommandLine.Command(name="default" , version = "0.1" , description = "Default Sprout workflow")
public class DefaultRunnable implements Runnable{

    @CommandLine.Option(names = "-d , --dir")
    private String defaultDir = ".";

    @Override
    public void run() {
        BannerPrinter.print();


        File entityPackage = new File(defaultDir + "/entity");
        File[] files = entityPackage.listFiles();

        if(files == null){
            throw new RuntimeException("Couldn't resolve entity package directory !");
        }

        JavaParser parser = new JavaParser();
        MustacheFactory mf = new DefaultMustacheFactory();

        //TODO: Change this into a generator list

        RepositoryGenerator repoGen = new RepositoryGenerator();
        ServiceGenerator serviceGen = new ServiceGenerator();
        DtoGenerator dtoGen = new DtoGenerator();
        MapperGenerator mapperGen = new MapperGenerator();

        //Compiling templates
        Mustache repoMustache = mf.compile("templates/RepositoryTemplate.mustache");
        Mustache serviceMustache = mf.compile("templates/ServiceTemplate.mustache");
        Mustache dtoMustache = mf.compile("templates/DtoTemplate.mustache");
        Mustache mapperMustache = mf.compile("templates/MapperTemplate.mustache");

        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold Generating.. |@ \n"));

        for(File entity : files){
            if(!entity.isFile()){
                continue;
            }

            try {
                //Parsing the code
                EntityMetadata em = EntityParser.parse(parser , entity);

                //Generating
                repoGen.generate(em , repoMustache , defaultDir);
                System.out.println(CommandLine.Help.Ansi.AUTO.string("@|faint " + LocalDateTime.now() + "|@ @|bold,blue  INFO|@ --- @|magenta [Sprout]|@ : Generating Repository for " + entity.getName()));
                dtoGen.generate(em , dtoMustache , defaultDir);
                System.out.println(CommandLine.Help.Ansi.AUTO.string("@|faint " + LocalDateTime.now() + "|@ @|bold,blue  INFO|@ --- @|magenta [Sprout]|@ : Generating DTOs for " + entity.getName()));
                mapperGen.generate(em , mapperMustache , defaultDir);
                System.out.println(CommandLine.Help.Ansi.AUTO.string("@|faint " + LocalDateTime.now() + "|@ @|bold,blue  INFO|@ --- @|magenta [Sprout]|@ : Generating Mapper for " + entity.getName()));
                serviceGen.generate(em , serviceMustache , defaultDir);
                System.out.println(CommandLine.Help.Ansi.AUTO.string("@|faint " + LocalDateTime.now() + "|@ @|bold,blue  INFO|@ --- @|magenta [Sprout]|@ : Generating Service for " + entity.getName()));

            } catch (FileNotFoundException e) {
                throw new RuntimeException("An error occurred whilst reading files.");
            } catch (IOException e) {
                throw new RuntimeException("An error occurred whilst generating files.");
            }
        }

        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold \nGeneration service ended successfully, shutting down ! |@ \n"));
    }
}

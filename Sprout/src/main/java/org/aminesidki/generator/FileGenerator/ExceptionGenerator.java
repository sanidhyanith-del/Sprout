package org.aminesidki.generator.FileGenerator;

import com.github.mustachejava.Mustache;
import lombok.RequiredArgsConstructor;
import org.aminesidki.exception.FileSystemException;
import org.aminesidki.generator.SproutFileGenerator;
import org.aminesidki.model.EntityMetadata;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

@RequiredArgsConstructor
public class ExceptionGenerator implements SproutFileGenerator {

    @Override
    public void generate(EntityMetadata entityMetadata, Mustache mustache, String defDir) throws IOException, FileSystemException {
        //Create the Repository package if it doesn't exist yet
        File controllerPackage = new File(defDir + File.separator + "exception");
        if(!controllerPackage.exists() && !controllerPackage.mkdir()){
            throw new FileSystemException("");
        }

        File controllerFile = new File(defDir + File.separator + "exception"+ File.separator + entityMetadata.className() + "NotFoundException.java");

        if(!controllerFile.exists() && !controllerFile.createNewFile()){
            throw new FileSystemException("");
        }

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(controllerFile))) {
            HashMap<String, Object> controllerContext = new HashMap<>();

            controllerContext.put("PackageName", entityMetadata.packageName());
            controllerContext.put("ClassName", entityMetadata.className());

            mustache.execute(writer, controllerContext);
        }
    }
}

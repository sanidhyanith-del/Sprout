package org.AmineSidki.generator.FileGenerator;

import com.github.mustachejava.Mustache;
import lombok.RequiredArgsConstructor;
import org.AmineSidki.exception.FileSystemException;
import org.AmineSidki.generator.ImportsGenerator.DtoImportsGenerator;
import org.AmineSidki.generator.SproutFileGenerator;
import org.AmineSidki.model.EntityMetadata;
import org.AmineSidki.model.FieldMetadata;
import org.AmineSidki.model.HelperMetadata;
import org.AmineSidki.util.ParserUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DtoGenerator implements SproutFileGenerator {
    private final DtoImportsGenerator dtoImportsGenerator;
    private final Map<String, EntityMetadata> persistenceMetadata;
    private final Map <String, HelperMetadata> helperMetadata;

    private record RecordFieldView(FieldMetadata field , boolean last){} ;

    public void generate(EntityMetadata entityMetadata, Mustache mustache, String defDir) throws IOException, FileSystemException {
        //Create dto package if it doesn't exist yet
        File dtoPackage = new File(defDir + File.separator +"dto");
        if (!dtoPackage.exists() && !dtoPackage.mkdir()) {
            throw new FileSystemException("");
        }

        File dtoFile = new File(defDir + File.separator +"dto"+ File.separator + entityMetadata.className() + "DTO.java");

        if ((!dtoFile.exists() && !dtoFile.createNewFile())) {
            throw new FileSystemException("");
        }

        List <FieldMetadata> fields = ParserUtil.mapToDtoField(entityMetadata , persistenceMetadata, helperMetadata);


        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dtoFile))) {
            HashMap <String,Object> dtoContext = new HashMap <>();

            HashSet<String> imports = dtoImportsGenerator.generate(entityMetadata, persistenceMetadata, helperMetadata);

            dtoContext.put("Imports" , imports);
            dtoContext.put("PackageName", entityMetadata.packageName());
            dtoContext.put("ClassName", entityMetadata.className());
            dtoContext.put("IdType", entityMetadata.id().type().getRegularName());
            dtoContext.put("Fields", fields.stream()
                    .map(f -> new RecordFieldView(f, f == fields.get(fields.size() - 1)))
                    .toList());

            mustache.execute(writer, dtoContext);
        }
    }
}
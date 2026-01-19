package org.AmineSidki.generator;

import com.github.mustachejava.Mustache;
import lombok.RequiredArgsConstructor;
import org.AmineSidki.enumeration.Association;
import org.AmineSidki.model.EntityMetadata;
import org.AmineSidki.model.FieldMetadata;
import org.AmineSidki.util.ParserUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DtoGenerator implements SproutGenerator {
    private final Map < String, EntityMetadata > persistenceMetadata;

    public void generate(EntityMetadata entityMetadata, Mustache mustache, String defDir) throws IOException {
        //Create dto package if it doesn't exist yet
        File dtoPackage = new File(defDir + "/dto");
        if (!dtoPackage.exists() && !dtoPackage.mkdir()) {
            throw new RuntimeException("An error occurred whilst generating files.");
        }

        File dtoFile = new File(defDir + "/dto/" + entityMetadata.getClassName() + "DTO.java");

        if ((!dtoFile.exists() && !dtoFile.createNewFile())) {
            throw new RuntimeException("An error occurred whilst generating files.");
        }

        List < FieldMetadata > fields = entityMetadata
                .getFields()
                .stream()
                .map(f -> f.getAssociation().equals(Association.ONE_TO_MANY) ||
                        f.getAssociation().equals(Association.MANY_TO_MANY) ?
                        new FieldMetadata("Set<" +
                                Optional.ofNullable(
                                                persistenceMetadata.get(ParserUtil.extractCollectionGenericType(f.getType())))
                                        .orElse(new EntityMetadata("", "", "Unknown", null))
                                        .getIdType() +
                                ">", f.getName(), f.getAssociation()) : ((f.getAssociation().equals(Association.MANY_TO_ONE) || f.getAssociation().equals(Association.ONE_TO_ONE)) ? new FieldMetadata(Optional.ofNullable(
                                persistenceMetadata.get(f.getType()))
                        .orElse(new EntityMetadata("", "", "Unknown", null))
                        .getIdType(), f.getName(), f.getAssociation()) : f))
                .collect(Collectors.toList());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dtoFile))) {
            HashMap < String, Object > dtoContext = new HashMap < > ();

            dtoContext.put("PackageName", entityMetadata.getPackageName());
            dtoContext.put("ClassName", entityMetadata.getClassName());
            dtoContext.put("IdType", entityMetadata.getIdType());
            dtoContext.put("Fields", fields);

            mustache.execute(writer, dtoContext);
        }
    }
}
package org.aminesidki.util;

import lombok.RequiredArgsConstructor;
import org.aminesidki.generator.DependencyGenerator.MapperDependencyGenerator;
import org.aminesidki.generator.FileGenerator.*;
import org.aminesidki.generator.ImportsGenerator.DtoImportsGenerator;
import org.aminesidki.generator.ImportsGenerator.GenericImportsGenerator;
import org.aminesidki.generator.ImportsGenerator.MapperImportsGenerator;
import org.aminesidki.handler.GenerationHandler;
import org.aminesidki.model.EntityMetadata;
import org.aminesidki.model.HelperMetadata;
import org.aminesidki.model.InitializationResources;
import org.aminesidki.model.PartialGenerationFlags;

import java.util.Map;

@RequiredArgsConstructor
public class GenerationHandlerInitializer {
    private final Map<String , EntityMetadata> emm;
    private final Map<String , HelperMetadata> hmm;
    private final GenerationHandler handler;
    private final PartialGenerationFlags pGroup;

    private void initializePartialGeneration(PartialGenerationFlags pGroup , InitializationResources resources){
        GenericImportsGenerator genericImportsGenerator = new GenericImportsGenerator();

        if(pGroup.rFlag){
            handler.add(new RepositoryGenerator(genericImportsGenerator, emm, hmm),
                    resources.repoMustache(),
                    Logger.getInstance().infoMessage("Generating repository for "));
        }
        if(pGroup.sFlag){
            handler.add(new ServiceGenerator(genericImportsGenerator, emm, hmm),
                    resources.serviceMustache(),
                    Logger.getInstance().infoMessage("Generating service for "));
        }
        if(pGroup.cFlag){
            handler.add(new ControllerGenerator(genericImportsGenerator, emm, hmm),
                    resources.controllerMustache(),
                    Logger.getInstance().infoMessage("Generating controller for "));
        }
        if(pGroup.mFlag){
            handler.add( new MapperGenerator(new MapperImportsGenerator() , new MapperDependencyGenerator(), emm, hmm),
                    resources.mapperMustache(),
                    Logger.getInstance().infoMessage("Generating mapper for "));
        }
        if(pGroup.dFlag){
            handler.add(new DtoGenerator(new DtoImportsGenerator(), emm, hmm),
                    resources.dtoMustache(),
                    Logger.getInstance().infoMessage("Generating dto for "));
        }
        if(pGroup.eFlag){
            handler.add(new ExceptionGenerator(),
                    resources.exceptionMustache(),
                    Logger.getInstance().infoMessage("Generating exception for "));
        }
    }

    public void initialize(){
        PartialGenerationFlags flags = pGroup == null ? PartialGenerationFlags.allEnabled() : pGroup;
        final InitializationResources resources = new InitializationResourcesMapper().map(flags);
        initializePartialGeneration(flags , resources);

    }
}

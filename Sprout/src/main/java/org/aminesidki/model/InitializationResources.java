package org.aminesidki.model;

import com.github.mustachejava.Mustache;

public record InitializationResources(Mustache repoMustache,
                                      Mustache dtoMustache,
                                      Mustache mapperMustache,
                                      Mustache serviceMustache,
                                      Mustache controllerMustache,
                                      Mustache exceptionMustache) {
}

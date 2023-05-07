package com.mjc.school.controller.utils;

import com.mjc.school.controller.implementation.NewsRestController;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//@Component
public class NewsModelAssembler extends RepresentationModelAssemblerSupport<NewsDtoRequest, NewsDtoResponse> {
    /**
     * Creates a new {@link RepresentationModelAssemblerSupport} using the given controller class and resource type.
     *
     * @param controllerClass must not be {@literal null}.
     * @param resourceType    must not be {@literal null}.
     */
    public NewsModelAssembler(Class<?> controllerClass, Class<NewsDtoResponse> resourceType) {
        super(NewsRestController.class, NewsDtoResponse.class);
    }

    /**
     * Converts the given entity into a {@code D}, which extends {@link RepresentationModel}.
     *
     * @param entity
     * @return
     */


    @Override
    public NewsDtoResponse toModel(NewsDtoRequest entity) {
        NewsDtoResponse newsDtoResponse = instantiateModel(entity);
        newsDtoResponse.add(linkTo(
                methodOn(NewsRestController.class)
                        .readById(entity.getId()))
                .withSelfRel());
        newsDtoResponse.setId(entity.getId());
        newsDtoResponse.setContent(entity.getContent());
        newsDtoResponse.setTitle(entity.getTitle());

        return newsDtoResponse;
    }
}

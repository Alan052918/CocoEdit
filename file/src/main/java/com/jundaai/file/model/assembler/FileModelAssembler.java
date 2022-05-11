package com.jundaai.file.model.assembler;

import com.jundaai.file.FileController;
import com.jundaai.file.model.File;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FileModelAssembler implements RepresentationModelAssembler<File, EntityModel<File>> {
    @Override
    public EntityModel<File> toModel(File entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(FileController.class).getFileById(entity.getId())).withSelfRel(),
                linkTo(methodOn(FileController.class).getAllFiles()).withRel("files"));
    }

    @Override
    public CollectionModel<EntityModel<File>> toCollectionModel(Iterable<? extends File> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}

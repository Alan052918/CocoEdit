package com.jundaai.file;

import com.jundaai.file.model.File;
import com.jundaai.file.model.assembler.FileModelAssembler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/files")
@Slf4j
public class FileController {

    private final FileService fileService;
    private final FileModelAssembler fileModelAssembler;

    @Autowired
    public FileController(FileService fileService, FileModelAssembler fileModelAssembler) {
        this.fileService = fileService;
        this.fileModelAssembler = fileModelAssembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<File>> getAllFiles() {
        log.info("Request to get all files.");
        List<File> files = fileService.getAllUsers();
        return fileModelAssembler.toCollectionModel(files);
    }

    @GetMapping(path = "{fileId}")
    public EntityModel<File> getFileById(@PathVariable(name = "fileId") Long fileId) {
        log.info("Request to get file by id: {}", fileId);
        File file = fileService.getFileById(fileId);
        return fileModelAssembler.toModel(file);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<File> createFile(@RequestBody FileCreationRequest fileCreationRequest) {
        log.info("Request to create new file: {}", fileCreationRequest);
        File file = fileService.createFile(fileCreationRequest);
        return fileModelAssembler.toModel(file);
    }

    @PostMapping(path = "{fileId}")
    public EntityModel<File> updateFileById(@PathVariable(name = "fileId") Long fileId,
                                            @RequestParam(name = "name", required = false) String newName,
                                            @RequestParam(name = "content", required = false) String newContent) {
        log.info("Request to update file by id: {}, new name: {}, new content: {}", fileId, newName, newContent);
        File file = fileService.updateFileById(fileId, newName, newContent);
        return fileModelAssembler.toModel(file);
    }

    @DeleteMapping(path = "{fileId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteFileById(@PathVariable(name = "fileId") Long fileId) {
        log.info("Request to delete file by id: {}", fileId);
        fileService.deleteFileById(fileId);
        return ResponseEntity.noContent().build();
    }

}

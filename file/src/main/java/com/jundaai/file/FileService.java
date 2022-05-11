package com.jundaai.file;

import com.jundaai.file.exception.CreatorNotFoundException;
import com.jundaai.file.exception.FileNotFoundException;
import com.jundaai.file.model.File;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FileService {

    private final FileRepository fileRepository;

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public List<File> getAllUsers() {
        log.info("Get all users.");
        return fileRepository.findAll();
    }

    public File getFileById(Long fileId) {
        log.info("Get file by id: {}", fileId);
        return fileRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException(fileId));
    }

    @Transactional
    public File createFile(FileCreationRequest fileCreationRequest) {
        log.info("Create new file: {}", fileCreationRequest);

        Long creatorId = fileCreationRequest.creatorId();
        boolean existsById = fileRepository.existsById(creatorId);
        if (!existsById) {
            throw new CreatorNotFoundException(creatorId);
        }

        ZonedDateTime requestDateTime = ZonedDateTime.now();
        File file = File.builder()
                .name(fileCreationRequest.name())
                .content("")
                .dateTimeCreated(requestDateTime)
                .dateTimeUpdated(requestDateTime)
                .participantIds(List.of(creatorId))
                .editActions(new ArrayList<>())
                .build();

        return fileRepository.save(file);
    }

    public File updateFileById(Long fileId, String newName, String newContent) {
        return null;
    }

    public void deleteFileById(Long fileId) {

    }
}

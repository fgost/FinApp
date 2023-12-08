package dsw.finApp.v1.infrastructure.service.storage;

import dsw.finApp.config.properties.ApplicationProperties;
import dsw.finApp.domain.Constants;
import dsw.finApp.exception.domain.ObjectNotFoundException;
import dsw.finApp.exception.domain.StorageException;
import dsw.finApp.exception.util.MessageResource;
import dsw.finApp.v1.storage.StorageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@AllArgsConstructor
public class LocalStorageService implements StorageService {
    private final ApplicationProperties properties;

    @Override
    public File save(File file) {
        var newFilePath = getRoot().resolve(file.getFileName());
        try {
            Files.write(newFilePath, file.getInputStream().readAllBytes());
            return getInfo(file.getFileName());
        } catch (Exception e) {
            var msg = MessageResource.getInstance().getMessage(Constants.STORAGE_NOT_PERSISTED);
            throw new StorageException(msg, e);
        }
    }

    @Override
    public File get(String fileName) {
        var fullPath = getRoot().resolve(fileName);
        if (Files.notExists(fullPath))
            throw new ObjectNotFoundException(Constants.STORAGE_OBJECT_NOT_FOUND);
        try {
            var file = StorageService.File.builder()
                    .fileName(fileName)
                    .size(Files.size(fullPath))
                    .url(fullPath.toString())
                    .inputStream(Files.newInputStream(fullPath))
                    .build();
            return file;
        } catch (Exception e) {
            var msg = MessageResource.getInstance().getMessage(Constants.STORAGE_READ_ERROR);
            throw new StorageException(msg, e);
        }
    }

    @Override
    public File getInfo(String fileName) {
        var fullPath = getRoot().resolve(fileName);
        if (Files.notExists(fullPath))
            throw new ObjectNotFoundException(Constants.STORAGE_OBJECT_NOT_FOUND);
        try {
            var file = StorageService.File.builder()
                    .fileName(fileName)
                    .size(Files.size(fullPath))
                    .url(fullPath.toString())
                    .build();
            return file;
        } catch (Exception e) {
            var msg = MessageResource.getInstance().getMessage(Constants.STORAGE_READ_ERROR);
            throw new StorageException(msg, e);
        }
    }

    @Override
    public void delete(String fileName) {
        var fullPath = getRoot().resolve(fileName);
        if (Files.notExists(fullPath))
            throw new ObjectNotFoundException(Constants.STORAGE_OBJECT_NOT_FOUND);
        try {
            Files.delete(fullPath);
        } catch (Exception e) {
            var msg = MessageResource.getInstance().getMessage(Constants.STORAGE_DELETE_ERROR);
            throw new StorageException(msg, e);
        }
    }

    @Override
    public File replace(String oldFile, File newFile) {
        return save(newFile);
    }

    private Path getRoot() {

        var rootPath = Paths.get(properties.getStorage().getLocal().getRoot());
        if (Files.notExists(rootPath)) {
            try {
                Files.createDirectories(rootPath);
            } catch (IOException e) {
                var msg = MessageResource.getInstance().getMessage(Constants.STORAGE_ROOT_ERROR);
                throw new StorageException(msg, e);
            }
        }
        return rootPath;
    }
}

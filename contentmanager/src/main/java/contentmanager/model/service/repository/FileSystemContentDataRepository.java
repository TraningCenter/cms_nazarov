package contentmanager.model.service.repository;

import contentmanager.model.entities.ContentData;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSystemContentDataRepository implements ContentDataRepository {

    private String storeDirectoryPath;

    public FileSystemContentDataRepository(String storeDirectoryPath) throws IOException {
        this.storeDirectoryPath = storeDirectoryPath;
        createStoreDirectoryIfNotExist(storeDirectoryPath);
    }

    public ContentData save(ContentData contentData) {
        try {
            Path pathToNewFile = makePathToFile(contentData.getName());
            Files.deleteIfExists(pathToNewFile);
            Files.createFile(pathToNewFile);
            Files.write(pathToNewFile, contentData.getBytes());

            return new ContentData(contentData, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ContentData(contentData, false);
    }

    @Override
    public ContentData read(String name) {
        try {
            Path pathToFile = makePathToFile(name);

            if (!Files.exists(pathToFile))
                return new ContentData(name, false);

            return new ContentData(Files.readAllBytes(pathToFile), name,true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ContentData(name, false);
    }

    @Override
    public ContentData remove(String name) {
        try {
            Path pathToNewFile = makePathToFile(name);
            Files.deleteIfExists(pathToNewFile);

            return new ContentData(name, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ContentData(name, false);
    }

    @Override
    public ContentData readAndRemove(String name) {
        try {
            Path pathToFile = makePathToFile(name);
            byte[] bytes = Files.readAllBytes(pathToFile);
            Files.deleteIfExists(pathToFile);

            return new ContentData(bytes, name,true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ContentData(name, false);
    }

    private void createStoreDirectoryIfNotExist(String storeDirectoryPath) throws IOException {
        Path storePath = Paths.get(storeDirectoryPath);
        if (!Files.isDirectory(storePath))
            Files.createDirectory(storePath);
    }

    private Path makePathToFile(String name) {
        return Paths.get(storeDirectoryPath, name);
    }
}

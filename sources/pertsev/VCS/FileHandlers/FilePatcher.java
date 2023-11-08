package pertsev.VCS.FileHandlers;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Stack;

public class FilePatcher {
    Path directory;

    public FilePatcher(Path directory) {
        this.directory = directory;
    }

    public void patch(Path filepath, String newValue) throws IOException {
        if (!filepath.startsWith(directory)) throw new IllegalArgumentException();
        if (!Files.exists(filepath)) throw new IllegalArgumentException();
        FileWriter fileWriter = new FileWriter(filepath.toFile(), false);

        fileWriter.write(newValue);
        fileWriter.close();
    }

    public void create(Path filepath, String value) throws IOException {
        if (!filepath.startsWith(directory)) throw new IllegalArgumentException();
        if (Files.exists(filepath)) throw new IllegalArgumentException();

        //Если между resources и нужным файлом есть несозданные директории, создадим их:
        Stack<Path> directoriesToCreate = new Stack<>();

        //Будем создавать список на создание необходимых директорий
        Path parent = filepath.getParent();
        while (!Files.exists(parent)) {
            directoriesToCreate.push(parent);
            parent = parent.getParent();
        }

        while (!directoriesToCreate.isEmpty()) {
            Files.createDirectory(directoriesToCreate.pop());
        }

        //Теперь можно создавать и сам файл
        Files.write(filepath, value.getBytes());
    }
}

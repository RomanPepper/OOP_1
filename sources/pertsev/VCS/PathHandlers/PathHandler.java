package pertsev.VCS.PathHandlers;

import java.nio.file.Path;

public class PathHandler {
    private final Path projectDir;

    public PathHandler(Path projectDir) {
        this.projectDir = projectDir;
    }

    public Path relativizeOfProject(Path filePath) {
        if (filePath.startsWith(projectDir)) {
            return projectDir.relativize(filePath);
        } else {
            return filePath;
        }
    }

    public Path unrelativizeOfProject(Path filepath) {
        return projectDir.resolve(filepath);
    }
}

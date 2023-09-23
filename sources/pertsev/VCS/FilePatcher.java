package pertsev.VCS;

import java.nio.file.Path;

public class FilePatcher {
    private Path trackedDirectory;
    public FilePatcher(Path trackedDirectory) {
        this.trackedDirectory = trackedDirectory;
    }

    public void patch(MyFile file) {

    }

    public void setTrackedDirectory(Path trackedDirectory) {
        this.trackedDirectory = trackedDirectory;
    }
}

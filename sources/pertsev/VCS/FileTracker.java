package pertsev.VCS;


import java.nio.file.Path;

public class FileTracker {
    private Path trackedDirectory;
    public FileTracker(Path trackedDirectory) {
        this.trackedDirectory = trackedDirectory;
    }

    private void check(MyFile file) {}
    public String status() {
        return trackedDirectory.toUri().toString();
    }

    public void setTrackedDirectory(Path trackedDirectory) {
        this.trackedDirectory = trackedDirectory;
    }
}

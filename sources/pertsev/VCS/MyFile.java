package pertsev.VCS;

import java.io.IOException;
import java.net.URI;
import java.nio.file.*;

public class MyFile implements Path {
    Path file;
    String version;

    public MyFile(Path file) {
        this.file = file;
        this.version = "0.1";
    }

    public MyFile(Path file, String version) {
        this.file = file;
        this.version = version;
    }

    @Override
    public FileSystem getFileSystem() {
        return file.getFileSystem();
    }

    @Override
    public boolean isAbsolute() {
        return file.isAbsolute();
    }

    @Override
    public MyFile getRoot() {
        return new MyFile(file.getRoot(), this.version);
    }

    @Override
    public MyFile getFileName() {
        return new MyFile(file.getFileName(), this.version);
    }

    @Override
    public MyFile getParent() {
        return new MyFile(file.getParent(), this.version);
    }

    @Override
    public int getNameCount() {
        return file.getNameCount();
    }

    @Override
    public MyFile getName(int index) {
        return new MyFile(file.getName(index), this.version);
    }

    @Override
    public MyFile subpath(int beginIndex, int endIndex) {
        return new MyFile(file.subpath(beginIndex, endIndex), this.version);
    }

    @Override
    public boolean startsWith(Path other) {
        return file.startsWith(other);
    }

    @Override
    public boolean endsWith(Path other) {
        return file.endsWith(other);
    }

    @Override
    public MyFile normalize() {
        return new MyFile(file.normalize(), this.version);
    }

    @Override
    public MyFile resolve(Path other) {
        return new MyFile(file.resolve(other), this.version);
    }

    @Override
    public MyFile relativize(Path other) {
        return new MyFile(file.relativize(other), this.version);
    }

    @Override
    public URI toUri() {
        return file.toUri();
    }

    @Override
    public MyFile toAbsolutePath() {
        return new MyFile(file.normalize(), this.version);
    }

    @Override
    public MyFile toRealPath(LinkOption... options) throws IOException {
        return new MyFile(file.toRealPath(options), this.version);
    }

    @Override
    public WatchKey register(WatchService watcher, WatchEvent.Kind<?>[] events, WatchEvent.Modifier... modifiers) throws IOException {
        return file.register(watcher, events, modifiers);
    }

    @Override
    public int compareTo(Path other) {
        return file.compareTo(other);
    }
}

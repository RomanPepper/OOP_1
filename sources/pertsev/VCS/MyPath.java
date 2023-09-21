package pertsev.VCS;

import java.io.IOException;
import java.net.URI;
import java.nio.file.*;

public class MyPath implements Path {
    Path file;
    String version;

    public MyPath(Path file) {
        this.file = file;
        this.version = "0.1";
    }

    public MyPath(Path file, String version) {
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
    public MyPath getRoot() {
        return new MyPath(file.getRoot(), this.version);
    }

    @Override
    public MyPath getFileName() {
        return new MyPath(file.getFileName(), this.version);
    }

    @Override
    public MyPath getParent() {
        return new MyPath(file.getParent(), this.version);
    }

    @Override
    public int getNameCount() {
        return file.getNameCount();
    }

    @Override
    public MyPath getName(int index) {
        return new MyPath(file.getName(index), this.version);
    }

    @Override
    public MyPath subpath(int beginIndex, int endIndex) {
        return new MyPath(file.subpath(beginIndex, endIndex), this.version);
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
    public MyPath normalize() {
        return new MyPath(file.normalize(), this.version);
    }

    @Override
    public MyPath resolve(Path other) {
        return new MyPath(file.resolve(other), this.version);
    }

    @Override
    public MyPath relativize(Path other) {
        return new MyPath(file.relativize(other), this.version);
    }

    @Override
    public URI toUri() {
        return file.toUri();
    }

    @Override
    public MyPath toAbsolutePath() {
        return new MyPath(file.normalize(), this.version);
    }

    @Override
    public MyPath toRealPath(LinkOption... options) throws IOException {
        return new MyPath(file.toRealPath(options), this.version);
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

package pertsev.VCS.Commit;

import pertsev.VCS.File.FileState;

public interface Change {
    public void apply(FileState file);

    public String toStringValue();
}


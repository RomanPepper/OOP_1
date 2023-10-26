package pertsev.VCS.Commit;

import java.nio.file.Path;

public interface Change {
    public FileValueWrapper apply(String[] linedText, Path path);

    public String toStringValue();
}


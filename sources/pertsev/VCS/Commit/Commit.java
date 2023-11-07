package pertsev.VCS.Commit;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @param name  ВОПРОC: (оставлять константами + нужно ли переходить на UPPER_CASE?) ИЛИ (final тут не нужен?)
 * @param files Список всех файлов и директорий в репозитории на момент утверждения коммита
 */
public record Commit(String name, Path[] files, Map<Path, List<Change>> fileChanges) {

    @Override
    public Map<Path, List<Change>> fileChanges() {
        return new HashMap<>(fileChanges);
    }
}

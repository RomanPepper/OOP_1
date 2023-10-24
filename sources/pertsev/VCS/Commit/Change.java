package pertsev.VCS.Commit;

public class Change {
    protected static class LineShiftPointer {
        private final int oldLineIndex;
        private final int newLineIndex;

        public LineShiftPointer(int oldI, int newI) {
            this.oldLineIndex = oldI;
            this.newLineIndex = newI;
        }

        public int getOldLineIndex() {
            return oldLineIndex;
        }

        public int getNewLineIndex() {
            return newLineIndex;
        }
    }

    private final LineShiftPointer lineShiftPointer;
    private final String replacementString;

    public Change(LineShiftPointer lineShiftPointer, String replacementString) {
        this.lineShiftPointer = lineShiftPointer;
        this.replacementString = replacementString;
    }

    public LineShiftPointer getLineShiftPointer() {
        return lineShiftPointer;
    }

    public String getReplacementString() {
        return replacementString;
    }
}


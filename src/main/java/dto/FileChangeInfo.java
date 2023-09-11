package dto;

public class FileChangeInfo {
    public String filePath;
    public int codeLine;

    public String getFilePath() {
        return this.filePath;
    }

    public int getCodeLine() {
        return this.codeLine;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setCodeLine(int codeLine) {
        this.codeLine = codeLine;
    }
}

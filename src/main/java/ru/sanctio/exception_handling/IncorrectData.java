package ru.sanctio.exception_handling;

public class IncorrectData {

    private String errorInfo;

    public IncorrectData(String info) {
        this.errorInfo = info;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }
}

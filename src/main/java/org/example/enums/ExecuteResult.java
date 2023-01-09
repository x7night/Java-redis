package org.example.enums;

public enum ExecuteResult {

    OK("+OK\r\n"),
    FAIL("-fail"),
    UNSUPPORTED("-unsupported operation\r\n");

    private String value;
    ExecuteResult(String res) {
        this.value = res;
    }

    public String getValue() {
        return value;
    }
}

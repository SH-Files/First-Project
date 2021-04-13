package com.example.first.project.enumeration;

public enum Action {
    CREATE("1"),
    READ("2"),
    UPDATE("3"),
    REMOVE("4"),
    QUIT("5"),
    UPDATESTUDENTFIRSTNAME("1"),
    UPDATESTUDENTLASTNAME("2"),
    UPDATESTUDENTBOOKS("3"),
    UPDATESTUDENTBOOKSBOOKTITLE("1"),
    UPDATESTUDENTBOOKSADDBOOK("2"),
    UPDATESTUDENTBOOKSREMOVEBOOK("3");

    private final String code;

    Action(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
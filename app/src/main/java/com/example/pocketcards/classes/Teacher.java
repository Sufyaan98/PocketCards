package com.example.pocketcards.classes;

public class Teacher extends User {
    public static String firstName;
    public static String secondName;
    public static String schoolCode;
    public static Teacher newTeacher = new Teacher(firstName, secondName, schoolCode);

    public Teacher(String fName, String sName, String code) {
        firstName = fName;
        secondName = sName;
        schoolCode = code;
    }

    public Teacher() {}

    public void clear() {
        firstName = null;
        secondName = null;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }
}

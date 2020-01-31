package com.laylasahara.main;

public abstract class Student extends Borrower {
    private int regNo;

    public Student(String name, String email, String libIdPrefix, int regNo) {
        super(name, email, libIdPrefix);
        this.regNo = regNo;
    }

    public int getRegNo() {
        return regNo;
    }
}

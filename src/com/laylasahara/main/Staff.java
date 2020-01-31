package com.laylasahara.main;

public class Staff extends Borrower {
    private int staffId;

    public Staff(String name,String email, int staffId) {
        super(name, email, "CA/");
        this.staffId = staffId;
    }

    public int getStaffId() {
        return staffId;
    }
}

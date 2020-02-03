package in.ed.poonacollege.androidassignments.model;

import androidx.annotation.NonNull;

public class Employee {

    private int no;
    private String name;
    private String address;
    private String phone;
    private float salary;
    private int dno;

    public Employee() {
    }

    public Employee(int no, String name, String address, String phone, float salary, int dno) {
        this.no = no;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.salary = salary;
        this.dno = dno;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public int getDno() {
        return dno;
    }

    public void setDno(int dno) {
        this.dno = dno;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}

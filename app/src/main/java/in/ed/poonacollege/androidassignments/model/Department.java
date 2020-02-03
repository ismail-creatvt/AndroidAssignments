package in.ed.poonacollege.androidassignments.model;

import androidx.annotation.NonNull;

public class Department {

    private int no;
    private String name;
    private String location;

    public Department() {
    }

    public Department(int no, String name, String location) {
        this.no = no;
        this.name = name;
        this.location = location;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}

package com.girevoy.university.model.entity;

public class Student extends Entity {
    private String name;
    private Group group;

    public Student() {
    }

    public Student(long id) {
        super(id);
    }

    public Student(long id, String name) {
        super(id);
        this.name = name;
    }

    public Student(long id, String name, Group group) {
        super(id);
        this.name = name;
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Student student = (Student) o;

        if (this.getID() != student.getID()) return false;

        return name.equals(student.name);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Student{" +
                "ID=" + super.getID() +
                ", name='" + name + '\'' +
                '}';
    }
}

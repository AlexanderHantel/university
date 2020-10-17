package com.girevoy.university.model.entity;

import java.util.List;

public class Group extends Entity {
    private String name;
    private List<Student> students;
    private Faculty faculty;

    public Group() {
    }

    public Group(long id) {
        super(id);
    }

    public Group(long id, String name) {
        super(id);
        this.name = name;
    }

    public Group(long id, String name, Faculty faculty) {
        super(id);
        this.name = name;
        this.faculty = faculty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Group group = (Group) o;

        if (this.getID() != group.getID()) return false;

        return name.equals(group.name);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}

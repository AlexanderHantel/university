package com.girevoy.university.model.entity;

import java.util.List;

public class Department extends Entity {
    private String name;
    private List<Teacher> teachers;
    private Faculty faculty;

    public Department() {
    }

    public Department(long id) {
        super(id);
    }

    public Department(long id, String name) {
        super(id);
        this.name = name;
    }

    public Department(long id, String name, Faculty faculty) {
        super(id);
        this.name = name;
        this.faculty = faculty;
    }

    public Department(long id, String name, List<Teacher> teachers, Faculty faculty) {
        super(id);
        this.name = name;
        this.teachers = teachers;
        this.faculty = faculty;
    }

    public Department(String name, List<Teacher> teachers, Faculty faculty) {
        this.name = name;
        this.teachers = teachers;
        this.faculty = faculty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
    }

    public void removeTeacher(Teacher teacher) {
        teachers.remove(teacher);
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Department that = (Department) o;

        if (this.getID() != that.getID()) return false;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}

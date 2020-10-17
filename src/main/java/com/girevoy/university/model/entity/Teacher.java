package com.girevoy.university.model.entity;

public class Teacher extends Entity {
    private String name;
    private Faculty faculty;
    private Department department;

    public Teacher() {
    }

    public Teacher(long id) {
        super(id);
    }

    public Teacher(long id, String name) {
        super(id);
        this.name = name;
    }

    public Teacher(long id, String name, Faculty faculty, Department department) {
        super(id);
        this.name = name;
        this.faculty = faculty;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Teacher teacher = (Teacher) o;
        if (this.getID() != teacher.getID()) return false;

        return name.equals(teacher.name);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}

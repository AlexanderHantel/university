package com.girevoy.university.model.entity;

import java.util.List;

public class Faculty extends Entity {
    private String name;
    private University university;
    private List<Group> groups;
    private List<Subject> subjects;
    private List<Department> departments;

    public Faculty() {
    }

    public Faculty(long id) {
        super(id);
    }

    public Faculty(long id, String name) {
        super(id);
        this.name = name;
    }

    public Faculty(String name) {
        this.name = name;
    }

    public Faculty(long id, String name, University university) {
        super(id);
        this.name = name;
        this.university = university;
    }

    public Faculty(String name, University university) {
        this.name = name;
        this.university = university;
    }

    public Faculty(String name, List<Group> groups, List<Subject> subjects, List<Department> departments) {
        this.name = name;
        this.groups = groups;
        this.subjects = subjects;
        this.departments = departments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public void addGroup(Group group) {
        groups.add(group);
    }

    public void removeGroup(Group group) {
        groups.remove(group);
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    public void removeSubject(Subject subject) {
        subjects.remove(subject);
    }

    public void addDepartment(Department department) {
        departments.add(department);
    }

    public void removeDepartment(Department department) {
        departments.remove(department);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Faculty faculty = (Faculty) o;

        if (this.getID() != faculty.getID()) return false;

        return name.equals(faculty.name);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}

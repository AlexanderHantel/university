package com.girevoy.university.model.entity;

import java.util.List;

public class Subject extends Entity {
    private String name;
    private int hours;
    private List<Theme> themes;
    private Faculty faculty;

    public Subject() {
    }

    public Subject(long id) {
        super(id);
    }

    public Subject(long id, String name, int hours) {
        super(id);
        this.name = name;
        this.hours = hours;
    }

    public Subject(long id, String name, int hours, Faculty faculty) {
        super(id);
        this.name = name;
        this.hours = hours;
        this.faculty = faculty;
    }

    public Subject(long id, String name, int hours, List<Theme> themes, Faculty faculty) {
        super(id);
        this.name = name;
        this.hours = hours;
        this.themes = themes;
        this.faculty = faculty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public List<Theme> getThemes() {
        return themes;
    }

    public void setThemes(List<Theme> themes) {
        this.themes = themes;
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

        Subject subject = (Subject) o;

        if (this.getID() != subject.getID()) return false;
        if (hours != subject.hours) return false;

        return name.equals(subject.name);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + hours;
        return result;
    }
}

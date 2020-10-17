package com.girevoy.university.model.entity;

public class Theme extends Entity {
    private String description;
    private Subject subject;

    public Theme() {
    }

    public Theme(long id) {
        super(id);
    }

    public Theme(long id, String description) {
        super(id);
        this.description = description;
    }

    public Theme(long id, String description, Subject subject) {
        super(id);
        this.description = description;
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Theme theme = (Theme) o;
        if (this.getID() != theme.getID()) return false;

        return description.equals(theme.description);
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }
}

package com.girevoy.university.model.entity;

public class Room extends Entity {
    private String number;
    private University university;

    public Room() {
    }

    public Room(long id) {
        super(id);
    }

    public Room(String number) {
        this.number = number;
    }

    public Room(long id, String number) {
        super(id);
        this.number = number;
    }

    public Room(String number, University university) {
        this.number = number;
        this.university = university;
    }

    public Room(long id, String number, University university) {
        super(id);
        this.number = number;
        this.university = university;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Room room = (Room) o;

        if (this.getID() != room.getID()) return false;

        return getNumber().equals(room.getNumber());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getNumber().hashCode();
        return result;
    }
}

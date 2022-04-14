package ru.muctr.Students;

/**
 * @author Evgenia Skichko
 */
public class Student{
    String surname;
    int reiting;
    int groupNumber;

    public String getSurname() {
        return surname;
    }

    public int getReiting() {
        return reiting;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public Student(String surname, int reiting, int gn) {
        this.surname = surname;
        this.reiting = reiting;
        this.groupNumber = gn;
    }

    @Override
    public String toString() {
        return this.getSurname();
    }
}

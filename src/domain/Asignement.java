package domain;

public class Asignement {
    private String id;
    private String description;
    private int startWeek;
    private int deadlineWeek;

    public Asignement(String id, String description, int deadlineWeek) {
        this.id = id;
        this.description = description;
        this.deadlineWeek = deadlineWeek;
    }
}

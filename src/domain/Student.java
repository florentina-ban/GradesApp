package domain;

public class Student <Integer> extends Entity<Integer>{
    private String sirName;
    private String name;
    private int group;
    private String email;
    private String laboratoryGuide;

    public Student(String sirName, String name, int group) {
        this.sirName = sirName;
        this.name = name;
        this.group = group;
    }

    public Student(String sirName, String name, int group, String email, String laboratoryGuide) {
        this.sirName = sirName;
        this.name = name;
        this.group = group;
        this.email = email;
        this.laboratoryGuide = laboratoryGuide;
    }

    public Integer getId() {
        return super.getId();
    }

    public String getSirName() {
        return sirName;
    }

    public String getName() {
        return name;
    }

    public int getGroup() {
        return group;
    }

    public String getEmail() {
        return email;
    }

    public String getLaboratoryGuide() {
        return laboratoryGuide;
    }

    public void setSirName(String sirName) {
        this.sirName = sirName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLaboratoryGuide(String laboratoryGuide) {
        this.laboratoryGuide = laboratoryGuide;
    }
}

package domain;

public class Student extends Entity<Integer>{
    private String sirName;
    private String name;
    private int group;
    private String email;
    private String laboratoryGuide;

    /**
     * constructor
     * @param id - int - id of the student
     * @param sirName - string
     * @param name  - string
     * @param group - int
     * @param email - string
     * @param laboratoryGuide - string
     */
    public Student(String sirName, String name, int group, String email, String laboratoryGuide) {
        this.sirName = sirName;
        this.name = name;
        this.group = group;
        this.email = email;
        this.laboratoryGuide = laboratoryGuide;
    }

    /**
     * @return - int - student's id
     */
    public Integer getId() {
        return super.getId();
    }

    /**
     * @return - string - sirname of the sutdent
     */
    public String getSirName() {
        return sirName;
    }

    /**
     * @return - string - name of the student
     */
    public String getName() {
        return name;
    }

    /**
     * @return - int - student's group number
     */
    public Integer getGroup() {
        return group;
    }

    /**
     * @return - string - student's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return - string -name of the laboratory teacher
     */
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

    @Override
    public String toString() {
        return "Student: " +
                "id=" + super.getId()+
                ", sirName='" + sirName + '\'' +
                ", name='" + name + '\'' +
                ", group=" + group +
                ", email='" + email + '\'' +
                ", laboratoryGuide='" + laboratoryGuide + '\'';
    }
}

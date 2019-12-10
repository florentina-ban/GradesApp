package domain;

public class MeanDto {
    private Student student;
    private String fullName;
    private int group;
    private float mean;

    public MeanDto(Student student, float mean) {
        this.student = student;
        this.mean = mean;
        this.fullName=student.getHoleName();
        this.group=student.getGroup();
    }

    public int getGroup() {
        return group;
    }

    public String getFullName() {
        return fullName;
    }

    public Student getStudent() {
        return student;
    }

    public float getMean() {
        return mean;
    }
}

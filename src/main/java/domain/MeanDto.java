package domain;

public class MeanDto {
    private Student student;
    private float mean;

    public MeanDto(Student student, float mean) {
        this.student = student;
        this.mean = mean;
    }

    public Student getStudent() {
        return student;
    }

    public float getMean() {
        return mean;
    }
}

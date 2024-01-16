package camp.model;


import java.util.List;

public class Student {
    private String studentId;
    private String studentName;
    private final List<Subject> selectSubjects;

    public Student(String studentId, String studentName, List<Subject> selectSubjects) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.selectSubjects = selectSubjects;
    }

    // Getter
    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public List<Subject> getSelectSubjects() {
        return selectSubjects;
    }

    // Setter
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

}

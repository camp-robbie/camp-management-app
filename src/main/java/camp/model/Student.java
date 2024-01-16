package camp.model;


import camp.enums.StudentStatus;
import camp.enums.SubjectType;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String studentId;
    private String studentName;
    private StudentStatus status;
    private final List<Subject> selectSubjects;

    public Student(String studentName, StudentStatus status, List<Subject> selectSubjects) {
        this.studentName = studentName;
        this.selectSubjects = selectSubjects;
        this.status = status;
    }

    public void updateStatus(StudentStatus status) {
        this.status = status;
    }

    public List<Subject> getMandatorySubjects() {
        List<Subject> mandatorySubjects = new ArrayList<>();
        for (Subject selectSubject : selectSubjects) {
            if (SubjectType.MANDATORY == selectSubject.getSubjectType()) {
                mandatorySubjects.add(selectSubject);
            }
        }
        return mandatorySubjects;
    }

    // Getter
    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public StudentStatus getStatus() {
        return status;
    }

    public List<Subject> getSelectSubjects() {
        return selectSubjects;
    }

    // Setter
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

}

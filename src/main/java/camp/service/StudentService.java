package camp.service;


import camp.enums.StudentStatus;
import camp.model.Student;
import camp.model.Subject;
import camp.repository.StudentStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StudentService {
    private final StudentStore studentStore;

    public StudentService(StudentStore studentStore) {
        this.studentStore = studentStore;
    }

    public Student createStudent(String studentName, List<Subject> selectSubjects) {
        Student student = new Student(studentName, StudentStatus.GREEN, selectSubjects);
        return studentStore.save(student);
    }

    public List<Student> inquireStudents() {
        return studentStore.getStore();
    }

    public Student findStudentById(String studentId) {
        for (Student student : studentStore.getStore()) {
            if(Objects.equals(student.getStudentId(), studentId)) {
                return student;
            }
        }
        throw new RuntimeException("Not Found Student");
    }

    public List<Student> inquireStudentByStatus(StudentStatus studentStatus) {
        List<Student> students = new ArrayList<>();
        for (Student student : studentStore.getStore()) {
            if (student.getStatus() == studentStatus) {
                students.add(student);
            }
        }
        return students;
    }

    public boolean deleteStudent(Student student) {
        return studentStore.delete(student);
    }
}

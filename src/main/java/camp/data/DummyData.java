package camp.data;


import camp.enums.StudentStatus;
import camp.model.Student;
import camp.model.Subject;
import camp.repository.StudentStore;
import camp.repository.SubjectStore;
import camp.service.ScoreService;

import java.util.List;

public class DummyData {
    private final SubjectStore subjectStore;
    private final StudentStore studentStore;
    private final ScoreService scoreService;

    public DummyData(StudentStore studentStore, SubjectStore subjectStore, ScoreService scoreService) {
        this.studentStore = studentStore;
        this.subjectStore = subjectStore;
        this.scoreService = scoreService;
    }

    public void initDummyData() {
        for (Student student : getStudents(List.of(
                this.subjectStore.getStore().get(1),
                this.subjectStore.getStore().get(2),
                this.subjectStore.getStore().get(3),
                this.subjectStore.getStore().get(7),
                this.subjectStore.getStore().get(8)
        ))) {
            studentStore.save(student);
        }
        getScores(this.studentStore.getStore());
    }

    public List<Student> getStudents(List<Subject> subjects) {
        return List.of(
                new Student("Robbie", StudentStatus.GREEN, subjects),
                new Student("Robbert", StudentStatus.RED, subjects),
                new Student("Bob", StudentStatus.YELLOW, subjects),
                new Student("Leo", StudentStatus.YELLOW, subjects),
                new Student("Lio", StudentStatus.GREEN, subjects),
                new Student("Rob", StudentStatus.RED, subjects),
                new Student("Robbin", StudentStatus.GREEN, subjects),
                new Student("Ryan", StudentStatus.GREEN, subjects),
                new Student("Bobby", StudentStatus.GREEN, subjects),
                new Student("Toby", StudentStatus.RED, subjects)
        );
    }

    public void getScores(List<Student> students) {
        for (Student student : students) {
            for (Subject selectSubject : student.getSelectSubjects()) {
                for (int i = 1; i < 11; i++) {
                    scoreService.createScore(student, selectSubject, i, (int)(Math.random() * 40) + 60);
                }
            }
        }
    }
}

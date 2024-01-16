package camp.context;


import camp.data.DummyData;
import camp.grade.ChoicePolicy;
import camp.grade.MandatoryPolicy;
import camp.repository.ScoreStore;
import camp.repository.StudentStore;
import camp.repository.SubjectStore;
import camp.service.ScoreService;
import camp.service.StudentService;
import camp.service.SubjectService;

public class CampContext {
    private final StudentService studentService;
    private final SubjectService subjectService;
    private final ScoreService scoreService;

    public CampContext() {
        StudentStore studentStore = new StudentStore();
        SubjectStore subjectStore = new SubjectStore();
        ScoreStore scoreStore = new ScoreStore();
        studentService = new StudentService(studentStore);
        subjectService = new SubjectService(subjectStore);
        scoreService = new ScoreService(scoreStore, new MandatoryPolicy(), new ChoicePolicy());
        new DummyData(studentStore, subjectStore, scoreService).initDummyData();
    }

    // Getter
    public StudentService getStudentService() {
        return studentService;
    }

    public SubjectService getSubjectService() {
        return subjectService;
    }

    public ScoreService getScoreService() {
        return scoreService;
    }

}

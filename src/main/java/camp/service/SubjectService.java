package camp.service;


import camp.enums.SubjectType;
import camp.model.Subject;
import camp.repository.SubjectStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SubjectService {
    private final SubjectStore subjectStore;

    public SubjectService(SubjectStore subjectStore) {
        this.subjectStore = subjectStore;
    }

    public List<Subject> inquireSubjects() {
        return subjectStore.getStore();
    }

    public List<Subject> selectSubjects(String inputSubjects) {
        List<Subject> selectSubjects = new ArrayList<>();
        List<String> subjectIds = List.of(
                inputSubjects.trim().replace(" ", "").split(",")
        );

        for (Subject subject : subjectStore.getStore()) {
            if (subjectIds.contains(subject.getSubjectId())) {
                selectSubjects.add(subject);
            }
        }
        return selectSubjects;
    }

    public boolean validateSelectSubjects(List<Subject> selectSubjects) {
        int mandatoryCnt = 0;
        int choiceCnt = 0;
        for (Subject selectSubject : selectSubjects) {
            if (SubjectType.MANDATORY == selectSubject.getSubjectType()) {
                mandatoryCnt++;
            } else {
                choiceCnt++;
            }
        }
        return mandatoryCnt >= 3 && choiceCnt >= 2;
    }

    public Subject findSubjectById(String subjectId) {
        for (Subject subject : subjectStore.getStore()) {
            if (Objects.equals(subject.getSubjectId(), subjectId)) {
                return subject;
            }
        }
        throw new RuntimeException("Not Found Subject");
    }
}

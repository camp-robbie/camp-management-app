package camp.model;


import camp.enums.SubjectType;

import java.util.Objects;

public class Subject {
    private String subjectId;
    private String subjectName;
    private final SubjectType subjectType;

    public Subject(String subjectId, String subjectName, SubjectType subjectType) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.subjectType = subjectType;
    }

    public boolean isMandatory() {
        return Objects.equals(SubjectType.MANDATORY, this.subjectType);
    }

    // Getter
    public String getSubjectId() {
        return subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public SubjectType getSubjectType() {
        return subjectType;
    }

}

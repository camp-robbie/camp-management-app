package camp.repository;


import camp.context.Constants;
import camp.enums.SubjectType;
import camp.model.Subject;

import java.util.List;

public class SubjectStore {
    private final List<Subject> store;
    private int storeIndex;

    public SubjectStore() {
        store = List.of(
                new Subject(
                        sequence(),
                        "Java",
                        SubjectType.MANDATORY
                ),
                new Subject(
                        sequence(),
                        "객체지향",
                        SubjectType.MANDATORY
                ),
                new Subject(
                        sequence(),
                        "Spring",
                        SubjectType.MANDATORY
                ),
                new Subject(
                        sequence(),
                        "JPA",
                        SubjectType.MANDATORY
                ),
                new Subject(
                        sequence(),
                        "MySQL",
                        SubjectType.MANDATORY
                ),
                new Subject(
                        sequence(),
                        "디자인 패턴",
                        SubjectType.CHOICE
                ),
                new Subject(
                        sequence(),
                        "Spring Security",
                        SubjectType.CHOICE
                ),
                new Subject(
                        sequence(),
                        "Redis",
                        SubjectType.CHOICE
                ),
                new Subject(
                        sequence(),
                        "MongoDB",
                        SubjectType.CHOICE
                )
        );
    }

    public List<Subject> getStore() {
        return store;
    }

    private String sequence() {
        storeIndex++;
        return Constants.IndexType.SUBJECT + storeIndex;
    }

}

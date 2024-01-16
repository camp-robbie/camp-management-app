package camp.repository;


import camp.context.Constants;
import camp.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentStore {
    private final List<Student> store;
    private int storeIndex;

    public StudentStore() {
        store = new ArrayList<>();
    }

    private String sequence() {
        storeIndex++;
        return Constants.IndexType.STUDENT + storeIndex;
    }

    public Student save(Student student) {
        student.setStudentId(sequence());
        this.store.add(student);
        return student;
    }

    // Getter
    public List<Student> getStore() {
        return store;
    }

    public boolean delete(Student student) {
        return this.store.remove(student);
    }
}

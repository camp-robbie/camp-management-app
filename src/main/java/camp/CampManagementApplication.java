package camp;


import camp.enums.SubjectType;
import camp.grade.ChoicePolicy;
import camp.grade.GradePolicy;
import camp.grade.MandatoryPolicy;
import camp.model.Score;
import camp.model.Student;
import camp.model.Subject;
import camp.view.Display;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * Notification
 * Java, 객체지향이 아직 익숙하지 않은 분들은 위한 소스코드 틀입니다.
 * main 메서드를 실행하면 프로그램이 실행됩니다.
 * model 의 클래스들과 아래 (// 기능 구현...) 주석 부분을 완성해주세요!
 * 프로젝트 구조를 변경하거나 기능을 추가해도 괜찮습니다!
 * 구현에 도움을 주기위한 Base 프로젝트입니다. 자유롭게 이용해주세요!
 */
public class CampManagementApplication {
    // 데이터 저장소
    private static List<Student> studentStore;
    private static List<Subject> subjectStore;
    private static List<Score> scoreStore;

    // index 관리 필드
    private static int studentIndex;
    private static final String INDEX_TYPE_STUDENT = "ST";
    private static int subjectIndex;
    private static final String INDEX_TYPE_SUBJECT = "SU";
    private static int scoreIndex;
    private static final String INDEX_TYPE_SCORE = "SC";

    // Grade
    private static final GradePolicy mandatoryPolicy = new MandatoryPolicy();
    private static final GradePolicy choicePolicy = new ChoicePolicy();

    // 스캐너
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        setInitData();
        try {
            displayMainView();
        } catch (Exception e) {
            System.out.println("\nError Log: " + e.getClass());
            System.out.println("Error Message: " + e.getMessage());
            System.out.println("오류 발생!\n프로그램을 종료합니다.");
        }
    }

    // 초기 데이터 생성
    private static void setInitData() {
        studentStore = new ArrayList<>();
        subjectStore = List.of(
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "Java",
                        SubjectType.MANDATORY
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "객체지향",
                        SubjectType.MANDATORY
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "Spring",
                        SubjectType.MANDATORY
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "JPA",
                        SubjectType.MANDATORY
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "MySQL",
                        SubjectType.MANDATORY
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "디자인 패턴",
                        SubjectType.CHOICE
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "Spring Security",
                        SubjectType.CHOICE
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "Redis",
                        SubjectType.CHOICE
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "MongoDB",
                        SubjectType.CHOICE
                )
        );
        scoreStore = new ArrayList<>();
    }

    // index 자동 증가
    private static String sequence(String type) {
        switch (type) {
            case INDEX_TYPE_STUDENT -> {
                studentIndex++;
                return INDEX_TYPE_STUDENT + studentIndex;
            }
            case INDEX_TYPE_SUBJECT -> {
                subjectIndex++;
                return INDEX_TYPE_SUBJECT + subjectIndex;
            }
            default -> {
                scoreIndex++;
                return INDEX_TYPE_SCORE + scoreIndex;
            }
        }
    }

    private static void displayMainView() throws InterruptedException {
        boolean flag = true;
        while (flag) {
            Display.mainView();
            int input = sc.nextInt();

            switch (input) {
                case 1 -> displayStudentView(); // 수강생 관리
                case 2 -> displayScoreView(); // 점수 관리
                case 3 -> flag = false; // 프로그램 종료
                default -> {
                    System.out.println("잘못된 입력입니다.\n되돌아갑니다!");
                    Thread.sleep(2000);
                }
            }
        }
        System.out.println("프로그램을 종료합니다.");
    }

    private static void displayStudentView() {
        boolean flag = true;
        while (flag) {
            Display.studentView();
            int input = sc.nextInt();

            switch (input) {
                case 1 -> createStudent(); // 수강생 등록
                case 2 -> inquireStudent(); // 수강생 목록 조회
                case 3 -> flag = false; // 메인 화면 이동
                default -> {
                    System.out.println("잘못된 입력입니다.\n메인 화면 이동...");
                    flag = false;
                }
            }
        }
    }

    // 수강생 등록
    private static void createStudent() {
        System.out.println("\n수강생을 등록합니다...");
        System.out.print("수강생 이름 입력: ");
        String studentName = sc.next();

        // 등록할 과목 목록 조회
        Display.subjectList(subjectStore);

        List<Subject> selectSubjects = new ArrayList<>();
        boolean flag = true;
        while (flag) {
            System.out.println("\n필수 과목 3개, 선택 과목 2개의 과목 ID를 ,로 구분지어 입력해주세요!");
            String inputSubjects = sc.next();
            List<String> subjectIds = List.of(
                    inputSubjects.trim().replace(" ", "").split(",")
            );

            for (Subject subject : subjectStore) {
                if (subjectIds.contains(subject.getSubjectId())) {
                    selectSubjects.add(subject);
                }
            }

            if (validateSelectSubjects(selectSubjects)) {
                flag = false;
            } else {
                System.out.println("\n필수 과목 3개 이상, 선택 과목 2개 이상은 필수입니다.");
            }
        }

        System.out.println("\n수강생 등록 중....");
        Student student = new Student(sequence(INDEX_TYPE_STUDENT), studentName, selectSubjects);
        studentStore.add(student);
        // 기능 구현
        System.out.println(student.getStudentName() + " 수강생 등록 성공!\n");
    }

    private static boolean validateSelectSubjects(List<Subject> selectSubjects) {
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

    // 수강생 목록 조회
    private static void inquireStudent() {
        System.out.println("\n수강생 목록을 조회합니다...");
        Display.studentList(studentStore);
    }

    private static void displayScoreView() {
        boolean flag = true;
        while (flag) {
            Display.scoreView();
            int input = sc.nextInt();

            switch (input) {
                case 1 -> createScore(); // 수강생의 과목별 시험 회차 및 점수 등록
                case 2 -> updateRoundScoreBySubject(); // 수강생의 과목별 회차 점수 수정
                case 3 -> inquireRoundGradeBySubject(); // 수강생의 특정 과목 회차별 등급 조회
                case 4 -> flag = false; // 메인 화면 이동
                default -> {
                    System.out.println("잘못된 입력입니다.\n메인 화면 이동...");
                    flag = false;
                }
            }
        }
    }

    // 수강생의 과목별 시험 회차 및 점수 등록
    private static void createScore() {
        String studentId = getStudentId(); // 관리할 수강생 고유 번호
        Student student = findStudentById(studentId);

        Display.subjectList(student.getSelectSubjects());
        String subjectId = getSubjectId(); // 관리할 과목 고유 번호
        Subject subject = findSubjectById(subjectId);

        int round = 1;
        boolean flag = true;
        while (flag) {
            System.out.println("\n등록할 회차를 입력하시오...");
            round = sc.nextInt();
            if (round <= 10 && round >= 1) {
                boolean isRegistered = false;
                for (Score sc : scoreStore) {
                    if (Objects.equals(sc.getStudentId(), student.getStudentId())
                            && Objects.equals(sc.getSubjectId(), subject.getSubjectId())
                            && sc.getRound() == round) {
                        isRegistered = true;
                        break;
                    }
                }
                if(!isRegistered) {
                    flag = false;
                } else {
                    System.out.println("이미 등록된 회차입니다.");
                }
            } else {
                System.out.println("회차는 1 ~ 10");
            }
        }

        int score = 0;
        flag = true;
        while (flag) {
            System.out.println("\n등록할 점수를 입력하시오...");
            score = sc.nextInt();
            if (score <= 100 && score >= 0) {
                flag = false;
            } else {
                System.out.println("점수는 0 ~ 100");
            }
        }

        System.out.println("시험 점수를 등록합니다...");
        String grade = getGrade(subject, score);
        Score saveScore = new Score(student.getStudentId(), subject.getSubjectId(), round, score, grade);
        scoreStore.add(saveScore);

        System.out.println("\n" +
                student.getStudentName() + " 수강생 " +
                subject.getSubjectName() + " 과목 " +
                saveScore.getRound() + "회차 " +
                saveScore.getScore() + "점수 등록 성공!"
        );
    }

    // 수강생의 과목별 회차 점수 수정
    private static void updateRoundScoreBySubject() {
        String studentId = getStudentId(); // 관리할 수강생 고유 번호
        Student student = findStudentById(studentId);

        Display.subjectList(student.getSelectSubjects());
        String subjectId = getSubjectId(); // 관리할 과목 고유 번호
        Subject subject = findSubjectById(subjectId);

        int round = 1;
        boolean flag = true;
        while (flag) {
            System.out.println("\n수정할 회차를 입력하시오...");
            round = sc.nextInt();
            if (round <= 10 && round >= 1) {
                boolean isRegistered = false;
                for (Score sc : scoreStore) {
                    if (Objects.equals(sc.getStudentId(), student.getStudentId())
                            && Objects.equals(sc.getSubjectId(), subject.getSubjectId())
                            && sc.getRound() == round) {
                        isRegistered = true;
                        break;
                    }
                }
                if (isRegistered) {
                    flag = false;
                } else {
                    System.out.println("등록 되어있지 않은 회차입니다.");
                }
            } else {
                System.out.println("회차는 1 ~ 10");
            }
        }

        int updateScore = 0;
        flag = true;
        while (flag) {
            System.out.println("\n등록할 점수를 입력하시오...");
            updateScore = sc.nextInt();
            if (updateScore <= 100 && updateScore >= 0) {
                flag = false;
            } else {
                System.out.println("점수는 0 ~ 100");
            }
        }

        System.out.println("시험 점수를 수정합니다...");
        Score findScore = null;
        for (Score score : scoreStore) {
            if (Objects.equals(score.getStudentId(), student.getStudentId())
                    && Objects.equals(score.getSubjectId(), subject.getSubjectId())
                    && score.getRound() == round) {
                findScore = score;
            }
        }

        if (findScore != null) {
            findScore.updateScore(updateScore);
            findScore.updateGrade(getGrade(subject, updateScore));
        } else {
            throw new RuntimeException("Not Found Score");
        }
        System.out.println("\n" +
                student.getStudentName() + " 수강생 " +
                subject.getSubjectName() + " 과목 " +
                findScore.getRound() + "회차 " +
                findScore.getScore() + "점수 수정 성공!");
    }

    // 수강생의 특정 과목 회차별 등급 조회
    private static void inquireRoundGradeBySubject() {
        String studentId = getStudentId(); // 관리할 수강생 고유 번호
        Student student = findStudentById(studentId);

        Display.subjectList(student.getSelectSubjects());
        String subjectId = getSubjectId(); // 관리할 과목 고유 번호
        Subject subject = findSubjectById(subjectId);

        // 기능 구현 (조회할 특정 과목)
        System.out.println(subject.getSubjectName() + " 과목 회차별 등급을 조회합니다...");
        List<Score> scores = new ArrayList<>();
        for (Score score : scoreStore) {
            if (Objects.equals(score.getStudentId(), student.getStudentId())
                    && Objects.equals(score.getSubjectId(), subject.getSubjectId())) {
                scores.add(score);
            }
        }
        Display.scoreListByRound(scores);
        System.out.println("\n등급 조회 성공!");
    }

    private static String getStudentId() {
        System.out.print("\n관리할 수강생의 번호를 입력하시오...");
        return sc.next();
    }

    private static String getSubjectId() {
        System.out.print("\n관리할 과목의 번호를 입력하시오...");
        return sc.next();
    }

    // Store 에서 해당 studentId 의 Student 찾기
    private static Student findStudentById(String studentId) {
        for (Student student : studentStore) {
            if (Objects.equals(student.getStudentId(), studentId)) {
                return student;
            }
        }
        throw new RuntimeException("Not Found Student");
    }

    // Store 에서 해당 subjectId 의 Subject 찾기
    private static Subject findSubjectById(String subjectId) {
        for (Subject subject : subjectStore) {
            if (Objects.equals(subject.getSubjectId(), subjectId)) {
                return subject;
            }
        }
        throw new RuntimeException("Not Found Subject");
    }

    // 점수 TO Grade
    private static String getGrade(Subject subject, int score) {
        return SubjectType.MANDATORY == subject.getSubjectType() ?
                mandatoryPolicy.convertToGrade(score) : choicePolicy.convertToGrade(score);
    }

}

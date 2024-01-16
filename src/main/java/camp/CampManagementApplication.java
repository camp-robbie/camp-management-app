package camp;


import camp.context.CampContext;
import camp.enums.StudentStatus;
import camp.model.Score;
import camp.model.Student;
import camp.model.Subject;
import camp.service.ScoreService;
import camp.service.StudentService;
import camp.service.SubjectService;
import camp.view.Display;

import java.util.*;

/**
 * Notification
 * Java, 객체지향이 아직 익숙하지 않은 분들은 위한 소스코드 틀입니다.
 * main 메서드를 실행하면 프로그램이 실행됩니다.
 * model 의 클래스들과 아래 (// 기능 구현...) 주석 부분을 완성해주세요!
 * 프로젝트 구조를 변경하거나 기능을 추가해도 괜찮습니다!
 * 구현에 도움을 주기위한 Base 프로젝트입니다. 자유롭게 이용해주세요!
 */
public class CampManagementApplication {
    private static StudentService studentService;
    private static SubjectService subjectService;
    private static ScoreService scoreService;

    // 스캐너
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        CampContext campContext = new CampContext();
        studentService = campContext.getStudentService();
        subjectService = campContext.getSubjectService();
        scoreService = campContext.getScoreService();

        try {
            displayMainView();
        } catch (Exception e) {
            System.out.println("\nError Log: " + e.getClass());
            System.out.println("Error Message: " + e.getMessage());
            System.out.println("오류 발생!\n프로그램을 종료합니다.");
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
                case 3 -> inquireStudentById(); // 수강생 조회
                case 4 -> updateStudentStatus(); // 수강생 정보 수정 (상태)
                case 5 -> inquireStudentByStatus(); // 상태별 수강생 목록 조회
                case 6 -> deleteStudent(); // 수강생 삭제
                case 7 -> flag = false; // 메인 화면 이동
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

        Display.subjectList(subjectService.inquireSubjects());

        List<Subject> selectSubjects = null;
        boolean flag = true;
        while (flag) {
            System.out.println("\n필수 과목 3개, 선택 과목 2개의 과목 ID를 ,로 구분지어 입력해주세요!");
            String inputSubjects = sc.next();
            selectSubjects = subjectService.selectSubjects(inputSubjects);

            if (subjectService.validateSelectSubjects(selectSubjects)) {
                flag = false;
            } else {
                System.out.println("\n필수 과목 3개 이상, 선택 과목 2개 이상은 필수입니다.");
            }
        }

        System.out.println("\n수강생 등록 중....");
        Student student = studentService.createStudent(studentName, Optional.of(selectSubjects).get());
        System.out.println(student.getStudentName() + " 수강생 등록 성공!\n");
    }

    // 수강생 목록 조회
    private static void inquireStudent() {
        System.out.println("\n수강생 목록을 조회합니다...");
        Display.studentList(studentService.inquireStudents());
    }

    // 수강생 조회
    private static void inquireStudentById() {
        String studentId = getStudentId(); // 관리할 수강생 고유 번호
        Student student = studentService.findStudentById(studentId);

        System.out.println("\n" + student.getStudentName() + " 수강생 정보를 조회합니다...");
        Display.studentInfo(student);
    }

    // 수강생 정보 수정 (상태)
    private static void updateStudentStatus() {
        String studentId = getStudentId(); // 관리할 수강생 고유 번호
        Student student = studentService.findStudentById(studentId);

        System.out.println("\n" + student.getStudentName() + " 수강생의 현재 상태는 :" + student.getStatus());
        System.out.print("변경할 상태(GREEN, RED, YELLOW)를 입력하시오...");
        String status = sc.next();
        StudentStatus studentStatus = StudentStatus.of(status);

        System.out.println("\n" + student.getStudentName() + " 수강생 상태를 수정합니다...");
        student.updateStatus(studentStatus);
        System.out.println("\n" + student.getStudentName() + " 수강생 상태 " + student.getStatus() + " 수정 성공!");
    }

    // 상태별 수강생 목록 조회
    private static void inquireStudentByStatus() {
        System.out.print("조회할 수강생 상태(GREEN, RED, YELLOW)를 입력하시오...");
        String status = sc.next();
        StudentStatus studentStatus = StudentStatus.of(status);

        System.out.println("\n" + studentStatus + " 상태 수강 목록 조회 중...");
        Display.studentList(studentService.inquireStudentByStatus(studentStatus));
    }

    // 수강생 삭제
    private static void deleteStudent() {
        String studentId = getStudentId(); // 관리할 수강생 고유 번호
        Student student = studentService.findStudentById(studentId);

        System.out.print("\n" + student.getStudentName() + " 수강생을 정말 삭제하시겠습니까? (Y/N)");
        String input = sc.next();
        if (Objects.equals("Y", input)) {
            if (studentService.deleteStudent(student)) {
                System.out.println("\n" + student.getStudentName() + " 수강생 삭제 성공!");
                scoreService.deleteScore(student);
            } else {
                throw new RuntimeException(student.getStudentName() +" 수강생 삭제 실패!");
            }
        } else {
            System.out.println(student.getStudentName() + " 수강생 삭제를 취소합니다.");
        }
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
                case 4 -> inquireGradeBySubject(); // 특정 수강생 과목별 등급 조회
                case 5 -> inquireAvgGradeByStatusStudent(); // 특정 상태 수강생들의 필수 과목 평균 등급 조회
                case 6 -> flag = false; // 메인 화면 이동
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
        Student student = studentService.findStudentById(studentId);

        Display.subjectList(student.getSelectSubjects());
        String subjectId = getSubjectId(); // 관리할 과목 고유 번호
        Subject subject = subjectService.findSubjectById(subjectId);

        int round = 1;
        boolean flag = true;
        while (flag) {
            System.out.println("\n등록할 회차를 입력하시오...");
            round = sc.nextInt();
            if (round <= 10 && round >= 1) {
                if (!scoreService.isRegistered(student, subject, round)) {
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
        Score saveScore = scoreService.createScore(student, subject, round, score);
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
        Student student = studentService.findStudentById(studentId);

        Display.subjectList(student.getSelectSubjects());
        String subjectId = getSubjectId(); // 관리할 과목 고유 번호
        Subject subject = subjectService.findSubjectById(subjectId);

        int round = 1;
        boolean flag = true;
        while (flag) {
            System.out.println("\n수정할 회차를 입력하시오...");
            round = sc.nextInt();
            if (round <= 10 && round >= 1) {
                if (scoreService.isRegistered(student, subject, round)) {
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
            System.out.println("\n수정할 점수를 입력하시오...");
            updateScore = sc.nextInt();
            if (updateScore <= 100 && updateScore >= 0) {
                flag = false;
            } else {
                System.out.println("점수는 0 ~ 100");
            }
        }

        System.out.println("시험 점수를 수정합니다...");
        scoreService.updateScore(student, subject, round, updateScore);
    }

    // 수강생의 특정 과목 회차별 등급 조회
    private static void inquireRoundGradeBySubject() {
        String studentId = getStudentId(); // 관리할 수강생 고유 번호
        Student student = studentService.findStudentById(studentId);

        Display.subjectList(student.getSelectSubjects());
        String subjectId = getSubjectId();
        Subject subject = subjectService.findSubjectById(subjectId);

        System.out.println(subject.getSubjectName() + " 과목 회차별 등급을 조회합니다...");
        List<Score> scoreList = scoreService.inquireGradeByRound(student, subject);
        Display.scoreListByRound(scoreList);
    }

    // 특정 수강생 과목별 평균 등급 조회
    private static void inquireGradeBySubject() {
        String studentId = getStudentId(); // 관리할 수강생 고유 번호
        Student student = studentService.findStudentById(studentId);

        System.out.println("\n" + student.getStudentName() + " 수강생의 과목별 평균 등급 조회 중...");
        Map<Subject, String> avgGrades = scoreService.inquireAvgGradeBySubject(student);
        Display.studentAgvGradeBySubject(avgGrades);
    }

    // 특정 상태 수강생들의 필수 과목 평균 등급 조회
    private static void inquireAvgGradeByStatusStudent() {
        System.out.print("조회할 수강생 상태(GREEN, RED, YELLOW)를 입력하시오...");
        String status = sc.next();
        StudentStatus studentStatus = StudentStatus.of(status);

        System.out.println("\n" + studentStatus + " 상태 수강생들의 필수 과목 평균 등급 조회 중...");
        List<Student> students = studentService.inquireStudentByStatus(studentStatus);
        Map<Student, String> avgGrades = scoreService.inquireAvgGradeByMandatorySubject(students);
        Display.studentAvgGradeByMandatorySubject(avgGrades);
    }

    private static String getStudentId() {
        System.out.print("\n관리할 수강생의 번호를 입력하시오...");
        return sc.next();
    }

    private static String getSubjectId() {
        System.out.print("\n관리할 과목의 번호를 입력하시오...");
        return sc.next();
    }

}

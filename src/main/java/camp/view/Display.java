package camp.view;


import camp.model.Score;
import camp.model.Student;
import camp.model.Subject;

import java.util.List;

public class Display {
    public static void mainView() {
        System.out.println("\n==================================");
        System.out.println("내일배움캠프 수강생 관리 프로그램 실행 중...");
        System.out.println("1. 수강생 관리");
        System.out.println("2. 점수 관리");
        System.out.println("3. 프로그램 종료");
        System.out.print("관리 항목을 선택하세요...");
    }

    public static void studentView() {
        System.out.println("==================================");
        System.out.println("수강생 관리 실행 중...");
        System.out.println("1. 수강생 등록");
        System.out.println("2. 수강생 목록 조회");
        System.out.println("3. 메인 화면 이동");
        System.out.print("관리 항목을 선택하세요...");
    }

    public static void scoreView() {
        System.out.println("==================================");
        System.out.println("점수 관리 실행 중...");
        System.out.println("1. 수강생의 과목별 시험 회차 및 점수 등록");
        System.out.println("2. 수강생의 과목별 회차 점수 수정");
        System.out.println("3. 수강생의 특정 과목 회차별 등급 조회");
        System.out.println("4. 메인 화면 이동");
        System.out.print("관리 항목을 선택하세요...");
    }


    // 과목 목록 출력
    public static void subjectList(List<Subject> subjects) {
        System.out.print("과목 목록 조회 중...\n");
        for (Subject subject : subjects) {
            System.out.println("==================================");
            System.out.println("과목 ID = " + subject.getSubjectId());
            System.out.println("과목 명 = " + subject.getSubjectName());
            System.out.println("과목 타입 = " + subject.getSubjectType());
        }
    }

    // 수강생 목록 출력
    public static void studentList(List<Student> students) {
        for (Student student : students) {
            System.out.println("==================================");
            System.out.println("수강생 ID = " + student.getStudentId());
            System.out.println("수강생 이름 = " + student.getStudentName());
        }
    }

    // 회차별 등급 조회
    public static void scoreListByRound(List<Score> scores) {
        for (Score score : scores) {
            System.out.println("==================================");
            System.out.println("회차 = " + score.getRound());
            System.out.println("등급 = " + score.getGrade());
        }
    }

}

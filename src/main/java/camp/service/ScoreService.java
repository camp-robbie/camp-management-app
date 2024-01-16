package camp.service;


import camp.enums.SubjectType;
import camp.grade.GradePolicy;
import camp.model.Score;
import camp.model.Student;
import camp.model.Subject;
import camp.repository.ScoreStore;

import java.util.*;

public class ScoreService {
    private final ScoreStore scoreStore;
    private final GradePolicy mandatoryPolicy;
    private final GradePolicy choicePolicy;

    public ScoreService(ScoreStore scoreStore, GradePolicy mandatoryPolicy, GradePolicy choicePolicy) {
        this.scoreStore = scoreStore;
        this.mandatoryPolicy = mandatoryPolicy;
        this.choicePolicy = choicePolicy;
    }

    public Score createScore(Student student, Subject subject, int round, int score) {
        String grade = getGrade(subject, score);
        return scoreStore.save(new Score(student.getStudentId(), subject.getSubjectId(), round, score, grade));
    }

    public boolean isRegistered(Student student, Subject subject, int round) {
        for (Score score : scoreStore.getStore()) {
            if (Objects.equals(score.getStudentId(), student.getStudentId())
                    && Objects.equals(score.getSubjectId(), subject.getSubjectId())
                    && score.getRound() == round) {
                return true;
            }
        }
        return false;
    }

    public void updateScore(Student student, Subject subject, int round, int updateScore) {
        Score findScore = null;
        for (Score score : scoreStore.getStore()) {
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
    }

    public List<Score> inquireGradeByRound(Student student, Subject subject) {
        List<Score> scores = new ArrayList<>();
        for (Score score : scoreStore.getStore()) {
            if (Objects.equals(score.getStudentId(), student.getStudentId())
                    && Objects.equals(score.getSubjectId(), subject.getSubjectId())) {
                scores.add(score);
            }
        }
        return scores;
    }

    public void deleteScore(Student student) {
        List<Score> deleteScoreList = new ArrayList<>();
        for (Score score : scoreStore.getStore()) {
            if (Objects.equals(score.getStudentId(), student.getStudentId())) {
                deleteScoreList.add(score);
            }
        }
        scoreStore.deleteAll(deleteScoreList);
    }

    public Map<Subject, String> inquireAvgGradeBySubject(Student student) {
        Map<Subject, String> avgGrades = new HashMap<>();
        for (Subject selectSubject : student.getSelectSubjects()) {
            List<Score> scores = inquireGradeByRound(student, selectSubject);
            if (scores.size() > 0) {
                int avgScore = Math.round((float) getTotalScore(scores) / scores.size());
                String avgGrade = getGrade(selectSubject, avgScore);
                avgGrades.put(selectSubject, avgGrade);
            } else {
                avgGrades.put(selectSubject, "등록된 점수가 없습니다.");
            }
        }
        return avgGrades;
    }

    public Map<Student, String> inquireAvgGradeByMandatorySubject(List<Student> students) {
        Map<Student, String> avgGrades = new HashMap<>();
        for (Student student : students) {
            int size = student.getMandatorySubjects().size();
            int totalScore = 0;
            for (Subject mandatorySubject : student.getMandatorySubjects()) {
                List<Score> scores = inquireGradeByRound(student, mandatorySubject);
                if (scores.size() > 0) {
                    totalScore += Math.round((float) getTotalScore(scores) / scores.size());
                }
            }
            String avgGrade = mandatoryPolicy.convertToGrade(Math.round((float) totalScore / size));
            if (totalScore != 0) {
                avgGrades.put(student, avgGrade);
            } else {
                avgGrades.put(student, "필수 과목에 등록된 점수가 없습니다.");
            }
        }
        return avgGrades;
    }

    private String getGrade(Subject subject, int score) {
        return SubjectType.MANDATORY == subject.getSubjectType() ?
                mandatoryPolicy.convertToGrade(score) : choicePolicy.convertToGrade(score);
    }

    private int getTotalScore(List<Score> scores) {
        int totalScore = 0;
        for (Score score : scores) {
            totalScore += score.getScore();
        }
        return totalScore;
    }

}

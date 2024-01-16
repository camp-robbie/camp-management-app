package camp.model;

public class Score {
    private String scoreId;
    private String studentId;
    private String subjectId;
    private int round;
    private int score;
    private String grade;

    public Score(String studentId, String subjectId, int round, int score, String grade) {
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.round = round;
        this.score = score;
        this.grade = grade;
    }

    public void updateScore(int score) {
        this.score = score;
    }

    public void updateGrade(String grade) {
        this.grade = grade;
    }

    // Getter
    public String getStudentId() {
        return studentId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public int getRound() {
        return round;
    }

    public int getScore() {
        return score;
    }

    public String getGrade() {
        return grade;
    }

    //Setter
    public void setScoreId(String scoreId) {
        this.scoreId = scoreId;
    }

}

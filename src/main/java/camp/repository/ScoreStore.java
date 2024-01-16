package camp.repository;


import camp.context.Constants;
import camp.model.Score;

import java.util.ArrayList;
import java.util.List;

public class ScoreStore {
    private final List<Score> store;
    private int storeIndex;

    public ScoreStore() {
        store = new ArrayList<>();
    }

    private String sequence() {
        storeIndex++;
        return Constants.IndexType.SCORE + storeIndex;
    }

    public Score save(Score score) {
        score.setScoreId(sequence());
        this.store.add(score);
        return score;
    }

    // Getter
    public List<Score> getStore() {
        return store;
    }

    public void deleteAll(List<Score> deleteScoreList) {
        this.store.removeAll(deleteScoreList);
    }

}

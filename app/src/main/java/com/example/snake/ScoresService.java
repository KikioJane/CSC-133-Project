package com.example.snake;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoresService {
    private static final Map<Difficulty, List<Score>> scores = new HashMap<>();
    static {
        scores.put(Difficulty.Easy, new ArrayList<>());
        scores.put(Difficulty.Medium, new ArrayList<>());
        scores.put(Difficulty.Hard, new ArrayList<>());
    }

    private static final DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
    private static HighScoresAdapter highScoresAdapter;

    // must call this once when first setting up the adapter from main activity
    public static void setHighScoresAdapter(HighScoresAdapter highScoresAdapter) {
        ScoresService.highScoresAdapter = highScoresAdapter;
    }

    // add a new score to the list, sort the list, and notify the adapter of changes
    public static void addScore(int points, Difficulty difficulty) {
        String dateString = dateFormat.format(new Date());
        scores.get(difficulty).add(new Score(points, dateString));

        for(List<Score> currentList : scores.values()) {
            Collections.sort(currentList);
        }

        highScoresAdapter.notifyDataSetChanged();
    }

    public static List<Score> getScores(Difficulty difficulty) {
        return scores.get(difficulty);
    }
}

// Score data class
class Score implements Comparable<Score> {
    private final int points;
    private final String date;

    public Score (int points, String date) {
        this.points = points;
        this.date = date;
    }

    public int getPoints() {
        return points;
    }

    public String getDate() {
        return date;
    }

    @Override
    public int compareTo(Score o) {
        return o.getPoints() - points;
    }
}

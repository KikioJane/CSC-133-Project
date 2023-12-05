package com.example.snake;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

// TODO: split scores by difficulty

public class ScoresService {
    private static final ArrayList<Score> scores = new ArrayList<>();
    private static final DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
    private static HighScoresAdapter highScoresAdapter;

    // must call this once when first setting up the adapter from main activity
    public static void setHighScoresAdapter(HighScoresAdapter highScoresAdapter) {
        ScoresService.highScoresAdapter = highScoresAdapter;
    }

    // add a new score to the list, sort the list, and notify the adapter of changes
    public static void addScore(int points) {
        String dateString = dateFormat.format(new Date());
        scores.add(new Score(points, dateString));

        Collections.sort(scores);

        highScoresAdapter.notifyDataSetChanged();
    }

    public static List<Score> getScores() {
        return scores;
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

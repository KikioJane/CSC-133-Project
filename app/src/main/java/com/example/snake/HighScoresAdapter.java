package com.example.snake;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HighScoresAdapter extends RecyclerView.Adapter<HighScoresAdapter.ViewHolder> {
    // the list of scores to use for displaying data
    private List<Score> dataSet;
    private Difficulty currentDifficulty = Difficulty.Medium;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = (TextView) view.findViewById(R.id.textView);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    public HighScoresAdapter() {
        dataSet = ScoresService.getScores(currentDifficulty);
    }

    public void setDifficulty(Difficulty newDifficulty) {
        this.currentDifficulty = newDifficulty;
        dataSet = ScoresService.getScores(currentDifficulty);
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.high_score_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        String text;
        if(dataSet.size() == 0) {
            // if no scores display special text
            text = "No scores";
            viewHolder.getTextView().setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        } else {
            // Each score needs to be called on twice, once for the date and once for points. Divide the
            // given position by two in order to get the actual index of the list we need
            int newPosition = position / 2;
            Score curScore = dataSet.get(newPosition);

            if (position % 2 == 0) {
                // even positions go in the first column and are dates
                text = String.format("%d.  %s", newPosition + 1, curScore.getDate());
                viewHolder.getTextView().setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
            } else {
                // odd positions go in the second column and are point values
                text = curScore.getPoints() + " points";
                viewHolder.getTextView().setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }
        }
        viewHolder.getTextView().setText(text);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        // Multiply by two for two-column data.
        // Minimum one for displaying special text with no scores.
        return Math.max(dataSet.size() * 2, 1);
    }
}


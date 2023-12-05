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
    private final List<Score> dataSet;

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
        dataSet = ScoresService.getScores();
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
        // Each score needs to be called on twice, once for the date and once for points. Divide the
        // given position by two in order to get the actual index of the list we need
        int newPosition = position / 2;
        Score curScore = dataSet.get(newPosition);

        String text;
        if(position % 2 == 0) {
            // even positions go in the first column and are dates
            text = String.format("%d.  %s", newPosition + 1, curScore.getDate());
            viewHolder.getTextView().setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        } else {
            // odd positions go in the second column and are point values
            text = curScore.getPoints() + " points";
            viewHolder.getTextView().setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
        viewHolder.getTextView().setText(text);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        // Multiply by two for two-column data.
        return dataSet.size() * 2;
        // Use this line instead if we want to limit it to top 10 scores (or some other amount)
        // return Math.min(10, dataSet.size()) * 2;
    }
}


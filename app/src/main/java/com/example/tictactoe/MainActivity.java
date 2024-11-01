package com.example.tictactoe;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private String currentPlayer = "X";
    private String[] board = new String[9];
    private boolean gameActive = true;
    private TextView statusTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusTextView = findViewById(R.id.statusTextView);
        statusTextView.setText("Player X's Turn"); // Initial player turn display

        GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            final int index = i;
            Button button = (Button) gridLayout.getChildAt(i);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (gameActive && board[index] == null) {
                        board[index] = currentPlayer;
                        button.setText(currentPlayer);
                        checkWinner();
                        if (gameActive) switchPlayer();
                    }
                }
            });
        }

        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    private void switchPlayer() {
        if (currentPlayer.equals("X")) {
            currentPlayer = "O";
            statusTextView.setText("Player O's Turn"); // Update to Player O's turn
        } else {
            currentPlayer = "X";
            statusTextView.setText("Player X's Turn"); // Update to Player X's turn
        }
    }

    private void checkWinner() {
        int[][] winCombinations = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
                {0, 4, 8}, {2, 4, 6}             // Diagonals
        };

        for (int[] combination : winCombinations) {
            String a = board[combination[0]];
            String b = board[combination[1]];
            String c = board[combination[2]];

            if (a != null && a.equals(b) && b.equals(c)) {
                gameActive = false;
                statusTextView.setText("Player " + a + " Wins!");
                statusTextView.setTextColor(Color.GREEN);

                highlightWinningCombination(combination);
                return;
            }
        }

        // Check for draw
        boolean draw = true;
        for (String s : board) {
            if (s == null) {
                draw = false;
                break;
            }
        }

        if (draw) {
            gameActive = false;
            statusTextView.setText("It's a Draw!");
            statusTextView.setTextColor(Color.BLUE);
        }
    }

    private void highlightWinningCombination(int[] combination) {
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int index : combination) {
            Button button = (Button) gridLayout.getChildAt(index);
            button.setTextColor(Color.BLACK); // Highlight winning buttons
        }
    }

    private void resetGame() {
        currentPlayer = "X";
        gameActive = true;
        statusTextView.setText("Player X's Turn"); // Reset turn display
        statusTextView.setTextColor(Color.WHITE); // Reset color

        GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            Button button = (Button) gridLayout.getChildAt(i);
            button.setText("");
            button.setTextColor(Color.BLACK); // Reset button text color
        }

        for (int i = 0; i < board.length; i++) {
            board[i] = null;
        }
    }
}

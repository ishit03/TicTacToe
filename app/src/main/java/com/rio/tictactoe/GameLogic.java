package com.rio.tictactoe;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameLogic {
    char[] board;
    ImageView[] imageboard;
    ArrayList<Integer> emptyCells;
    boolean[] moveChooser;

    public GameLogic(int mode) {
        board = new char[9];
        imageboard = new ImageView[9];
        Arrays.fill(board, ' ');
        emptyCells = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));
        if (mode == 1)
            moveChooser = new boolean[]{true, true, true, true, true, true, true, false, false, false};
        else if (mode == 0)
            moveChooser = new boolean[]{true, true, true, false, false, false, false, false, false, false};
    }

    public int gameState() {
        if (board[0] == board[1] && board[1] == board[2] && board[0] != ' ')
            return (board[0] == 'O') ? 10 : -10;
        else if (board[3] == board[4] && board[4] == board[5] && board[3] != ' ')
            return (board[3] == 'O') ? 10 : -10;
        else if (board[6] == board[7] && board[7] == board[8] && board[6] != ' ')
            return (board[6] == 'O') ? 10 : -10;
        else if (board[0] == board[3] && board[3] == board[6] && board[0] != ' ')
            return (board[0] == 'O') ? 10 : -10;
        else if (board[1] == board[4] && board[4] == board[7] && board[1] != ' ')
            return (board[1] == 'O') ? 10 : -10;
        else if (board[2] == board[5] && board[5] == board[8] && board[2] != ' ')
            return (board[2] == 'O') ? 10 : -10;
        else if (board[0] == board[4] && board[4] == board[8] && board[0] != ' ')
            return (board[0] == 'O') ? 10 : -10;
        else if (board[2] == board[4] && board[4] == board[6] && board[2] != ' ')
            return (board[2] == 'O') ? 10 : -10;
        else
            return 0;
    }

    public void fillBoard(ImageView[] imageboard) {
        int index = 0;
        for (ImageView i : imageboard) {
            if (i.getTag().equals("x")) {
                board[index] = 'X';
                emptyCells.remove(Integer.valueOf(index));
            } else if (i.getTag().equals("o")) {
                board[index] = 'O';
                emptyCells.remove(Integer.valueOf(index));
            } else
                board[index] = ' ';
            ++index;
        }
    }

    public boolean isMoveAvailable() {
        for (int i = 0; i < 9; ++i) {
            if (board[i] == ' ')
                return true;
        }
        return false;
    }

    public int getOptimalMove(int diff) {
        int score = -10, moveScore;
        int bestMove = -1;
        for (int i = 0; i < 9; ++i) {
            if (board[i] == ' ') {
                if (bestMove == -1)
                    bestMove = i;
                board[i] = 'O';
                moveScore = minimax(false);
                if (moveScore > score) {
                    score = moveScore;
                    bestMove = i;
                }
                board[i] = ' ';
            }
        }
        if (diff == 1 || diff == 0)
            if (!moveChooser[new Random().nextInt(9)])
                return emptyCells.get(new Random().nextInt(emptyCells.size()));
        return bestMove;
    }

    private int minimax(boolean turn) {
        int score = gameState();
        int value;
        if (score != 0 || !isMoveAvailable())
            return score;
        if (!turn) {
            value = 10;
            for (int i = 0; i < 9; ++i) {
                if (board[i] == ' ') {
                    board[i] = 'X';
                    value = Math.min(value, minimax(true));
                    board[i] = ' ';
                }
            }
        } else {
            value = -10;
            for (int i = 0; i < 9; ++i) {
                if (board[i] == ' ') {
                    board[i] = 'O';
                    value = Math.max(value, minimax(false));
                    board[i] = ' ';
                }
            }
        }
        return value;
    }
}

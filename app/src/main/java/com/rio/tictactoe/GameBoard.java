package com.rio.tictactoe;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class GameBoard extends Fragment implements View.OnClickListener {

    ImageView[] board;
    boolean isPvp, turn;
    GameLogic gl;
    int gameState, diff;
    ImageView resultimg, title;
    AlertDialog.Builder builder;
    AlertDialog dialog;

    public GameBoard() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_board, container, false);
        board = new ImageView[9];
        board[0] = view.findViewById(R.id.b1);
        board[1] = view.findViewById(R.id.b2);
        board[2] = view.findViewById(R.id.b3);
        board[3] = view.findViewById(R.id.b4);
        board[4] = view.findViewById(R.id.b5);
        board[5] = view.findViewById(R.id.b6);
        board[6] = view.findViewById(R.id.b7);
        board[7] = view.findViewById(R.id.b8);
        board[8] = view.findViewById(R.id.b9);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isPvp = false;
        diff = -1;
        if (getArguments() != null) {
            isPvp = getArguments().getBoolean("isPvp");
            if (!isPvp)
                diff = getArguments().getInt("difficulty");
        }
        turn = true;
        gl = new GameLogic(diff);
        for (ImageView i : board)
            i.setOnClickListener(this);
        builder = new AlertDialog.Builder(getActivity());
        View v = getLayoutInflater().inflate(R.layout.alertlayout, null);
        resultimg = v.findViewById(R.id.resultimg);
        title = v.findViewById(R.id.title);
        builder.setView(v);
    }

    @Override
    public void onClick(View view) {
        String tag = (String) view.getTag();
        ImageView imageView = (ImageView) view;
        if (isPvp) {
            if (!tag.equals("0"))
                Toast.makeText(getActivity(), "Invalid Move!!", Toast.LENGTH_SHORT).show();
            else {
                if (turn) {
                    imageView.setImageResource(R.drawable.xsym);
                    imageView.setTag("x");
                    turn = !turn;
                } else {
                    imageView.setImageResource(R.drawable.osym);
                    imageView.setTag("o");
                    turn = !turn;
                }
            }
        } else {
            if (!tag.equals("0"))
                Toast.makeText(getActivity(), "Invalid Move!!", Toast.LENGTH_SHORT).show();
            else {
                imageView.setImageResource(R.drawable.xsym);
                imageView.setTag("x");
                gl.fillBoard(board);
                if (gl.isMoveAvailable() && gl.gameState() == 0) {
                    int optimalMove = gl.getOptimalMove(diff);
                    board[optimalMove].setImageResource(R.drawable.osym);
                    board[optimalMove].setTag("o");
                }
            }
        }
        gl.fillBoard(board);
        gameState = gl.gameState();
        if (gameState != 0) {
            if (gameState == 10)
                resultimg.setImageResource(R.drawable.omega);
            else if (gameState == -10)
                resultimg.setImageResource(R.drawable.xmen);
            title.setImageResource(R.drawable.winner);
            dialog = builder.create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

            Navigation.findNavController(view).popBackStack();
        }
        if (!gl.isMoveAvailable() && gameState == 0) {
            resultimg.setImageResource(R.drawable.drawdesc);
            title.setImageResource(R.drawable.draw);
            dialog = builder.create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
            Navigation.findNavController(view).popBackStack();
        }
    }
}
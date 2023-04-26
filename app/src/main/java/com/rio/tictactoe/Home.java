package com.rio.tictactoe;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class Home extends Fragment implements View.OnClickListener {

    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ImageButton pvpbtn = view.findViewById(R.id.vsP);
        ImageButton pvaibtn = view.findViewById(R.id.vsAi);
        ImageButton exit = view.findViewById(R.id.exit);
        Bundle bundle = new Bundle();
        bundle.putBoolean("isPvp", true);
        pvpbtn.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_home_to_gameBoard, bundle));
        pvaibtn.setOnClickListener(view1 -> {
            View v = (View) view1.getParent();
            v.findViewById(R.id.vsP).setVisibility(View.GONE);
            v.findViewById(R.id.difficulty).setVisibility(View.VISIBLE);
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) v.findViewById(R.id.exit).getLayoutParams();
            params.topToBottom = R.id.difficulty;
        });
        view.setOnClickListener(v -> {
            v.findViewById(R.id.difficulty).setVisibility(View.GONE);
            v.findViewById(R.id.vsP).setVisibility(View.VISIBLE);
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) v.findViewById(R.id.exit).getLayoutParams();
            params.topToBottom = R.id.vsP;
        });
        exit.setOnClickListener(v -> getActivity().finish());
        view.findViewById(R.id.diffNorm).setOnClickListener(this);
        view.findViewById(R.id.diffMod).setOnClickListener(this);
        view.findViewById(R.id.diffExt).setOnClickListener(this);
        applyTransition(view);
        return view;
    }

    private void applyTransition(View v) {
        LayoutTransition layoutTransition = new LayoutTransition();
        layoutTransition.setDuration(500);
        layoutTransition.enableTransitionType(LayoutTransition.CHANGING);
        ConstraintLayout l = v.findViewById(R.id.main);
        l.setLayoutTransition(layoutTransition);
    }

    @Override
    public void onClick(View view) {
        int diff = 0;
        int id = view.getId();
        if (id == R.id.diffMod)
            diff = 1;
        else if (id == R.id.diffExt)
            diff = 2;
        Bundle bundle = new Bundle();
        bundle.putBoolean("isPvp", false);
        bundle.putInt("difficulty", diff);
        Navigation.findNavController(this.getView()).navigate(R.id.action_home_to_gameBoard, bundle);
    }
}
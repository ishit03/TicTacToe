package com.rio.tictactoe;

import android.os.Bundle;
import android.view.WindowInsets;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getWindow().getInsetsController().hide(WindowInsets.Type.statusBars());
    }
}
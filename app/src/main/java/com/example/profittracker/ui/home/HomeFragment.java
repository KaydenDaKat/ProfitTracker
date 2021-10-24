package com.example.profittracker.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.profittracker.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    TextView textViewProfitRatio;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        textViewProfitRatio = root.findViewById(R.id.textView_profitRatio_homeFragment);
        ProgressBar progressBarGoalProgress = root.findViewById(R.id.progressBar_goalProgress_homeFragment);

        textViewProfitRatio.setText("1000/6000");
        progressBarGoalProgress.setProgress(60);
        root.getRootView().setBackgroundColor(Color.parseColor("#00BCD4")); //custom blue

        return root;
    }

}
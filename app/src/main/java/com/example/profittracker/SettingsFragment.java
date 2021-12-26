package com.example.profittracker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {

    Button resetAllButton, resetJobButton, resetStockButton, resetCryptoButton, resetOthersButton, resetTaxButton;
    TextView wholeScreenTextView;
    Boolean isSnackBarPresent = false;

    public SettingsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        resetAllButton = root.findViewById(R.id.button_allLists_settings);
        resetJobButton = root.findViewById(R.id.button_jobList_settings);
        resetStockButton = root.findViewById(R.id.button_stockList_settings);
        resetCryptoButton = root.findViewById(R.id.button_cryptoList_settings);
        resetOthersButton = root.findViewById(R.id.button_othersList_settings);
        resetTaxButton = root.findViewById(R.id.button_taxList_settings);

        wholeScreenTextView = root.findViewById(R.id.textView_wholeScreen_settings);

        resetAllButton.setOnClickListener(this);
        resetJobButton.setOnClickListener(this);
        resetStockButton.setOnClickListener(this);
        resetCryptoButton.setOnClickListener(this);
        resetOthersButton.setOnClickListener(this);
        resetTaxButton.setOnClickListener(this);
        wholeScreenTextView.setOnClickListener(this);

        wholeScreenTextView.setClickable(false);

        return root;
    }

    @Override
    public void onClick(View view)
    {
        if (!isSnackBarPresent) {
            switch (view.getId()) {
                case R.id.button_allLists_settings:
                    showSnackBar("All", resetAllButton);
                    break;
                case R.id.button_jobList_settings:
                    showSnackBar("Job", resetJobButton);
                    break;
                case R.id.button_stockList_settings:
                    showSnackBar("Stock", resetStockButton);
                    break;
                case R.id.button_cryptoList_settings:
                    showSnackBar("Crypto", resetCryptoButton);
                    break;
                case R.id.button_othersList_settings:
                    showSnackBar("Others", resetOthersButton);
                    break;
                case R.id.button_taxList_settings:
                    showSnackBar("Tax", resetTaxButton);
            }
            setButtonAccessibility(false);
        }
        else {
            switch (view.getId()) {
                case R.id.textView_wholeScreen_settings:
                Snackbar snackBar = Snackbar.make(getActivity().findViewById(android.R.id.content), "Selection Reset"
                        , Snackbar.LENGTH_LONG);
                snackBar.show();
                setButtonAccessibility(true);
                isSnackBarPresent = false;
                case R.id.button_allLists_settings:

                case R.id.button_jobList_settings:

                case R.id.button_stockList_settings:

                case R.id.button_cryptoList_settings:

                case R.id.button_othersList_settings:

                case R.id.button_taxList_settings:

            }
        }
    }

    public void showSnackBar(String category, Button button)
    {
        isSnackBarPresent = true;
        Snackbar snackBar = Snackbar.make(getActivity().findViewById(android.R.id.content),"Are You Sure You Want to Reset " + category + " Lists?"
                ,Snackbar.LENGTH_INDEFINITE)
                .setAction("YES", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        button.setEnabled(true);
                        button.setClickable(true);
                        Snackbar snackBar2 = Snackbar.make(getActivity().findViewById(android.R.id.content),"Press Reset " + category + " Lists Again"
                                ,Snackbar.LENGTH_INDEFINITE);
                        snackBar2.show();
                    }
                });
        snackBar.show();

    }

    public void setButtonAccessibility(boolean state)
    {
        wholeScreenTextView.setClickable(!state);

        resetAllButton.setEnabled(state);
        resetJobButton.setEnabled(state);
        resetStockButton.setEnabled(state);
        resetCryptoButton.setEnabled(state);
        resetOthersButton.setEnabled(state);
        resetTaxButton.setEnabled(state);

        resetAllButton.setClickable(state);
        resetJobButton.setClickable(state);
        resetStockButton.setClickable(state);
        resetCryptoButton.setClickable(state);
        resetOthersButton.setClickable(state);
        resetTaxButton.setClickable(state);
    }
}
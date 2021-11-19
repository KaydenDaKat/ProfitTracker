package com.example.profittracker.ui.itemsetups;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.profittracker.R;
import com.example.profittracker.ui.home.HomeViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddItemFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private EditText insertNameEditText, insertProfitEditText;
    private Button createItemButton;

    private String itemName, itemProfitString;
    private double itemProfit;

    private int id;

    public AddItemFragment() {
        // Required empty public constructor
    }
    public static AddItemFragment newInstance(String param1, String param2) {
        AddItemFragment fragment = new AddItemFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add_item, container, false);
        insertNameEditText = root.findViewById(R.id.editText_createName_createItem);
        insertProfitEditText = root.findViewById(R.id.editText_createProfit_createItem);
        createItemButton = root.findViewById(R.id.button_createItem_createItem);

        onCreateSetUp();
        setListeners();
        return root;
    }

    public void onCreateSetUp()
    {
       id = getArguments().getInt("ID");

       if(id==0) {
           insertNameEditText.setHint("Insert Job Name");
           createItemButton.setText("CREATE JOB");
       } else if (id==1) {
           insertNameEditText.setHint("Insert Stock Name");
           createItemButton.setText("CREATE STOCK");
       }
       else if (id==2){
           insertNameEditText.setHint("Insert Crypto Name");
           createItemButton.setText("CREATE CRYPTO");
       }
    }

    public void setListeners()
    {
        insertProfitEditText.addTextChangedListener(new TextWatcher() { // this all keeps displayed profit from displaying unnecessary numbers
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                itemProfitString = insertProfitEditText.getText().toString();

                if(!itemProfitString.contains("$"))
                {
                    itemProfitString = "$"+itemProfitString;
                    insertProfitEditText.setText(itemProfitString);
                    insertProfitEditText.setSelection(itemProfitString.length());
                }

                while(itemProfitString.indexOf('0')==1 && !(itemProfitString.indexOf('.')==2))
                {
                    itemProfitString = "";
                    insertProfitEditText.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (itemProfitString.contains(".")) {
                    int decimalPosition = itemProfitString.indexOf('.');
                    if (decimalPosition == 1)
                    {
                        itemProfitString = "$0.";
                        insertProfitEditText.setText("$0.");
                        insertProfitEditText.setSelection(itemProfitString.length());
                    }

                    int itemProfitStringLength = itemProfitString.length();
                    if (itemProfitStringLength > decimalPosition + 3) {

                        itemProfitString = itemProfitString.substring(0, decimalPosition + 3);
                        insertProfitEditText.setText(itemProfitString);
                        insertProfitEditText.setSelection(itemProfitStringLength - 1);
                    }
                }
            }
        });

        createItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (id)
                {
                    case 0:


                    case 1:


                    case 2:

                }
            }
        });
    }
}
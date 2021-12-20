package com.example.profittracker.ui.itemsetups;

import android.content.Context;
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
import android.widget.Toast;

import com.example.profittracker.MainCellItemClass;
import com.example.profittracker.R;
import com.example.profittracker.ui.home.HomeViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private String pastInputString;
    private boolean mightBeDoubleZero;

    private int id;
    private List<MainCellItemClass> itemCellList;

    private OnDataPass dataPasser;

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
       String json = getArguments().getString("jsonStringForAddition");
       itemCellList = reverseJsonFile(json);

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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                pastInputString = s.toString();
                Log.e(pastInputString,pastInputString);
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                itemProfitString = s.toString();
                Log.e(itemProfitString,itemProfitString);

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
                if (itemProfitString.indexOf("$") != 0)
                {
                    itemProfitString = itemProfitString.substring(1);
                    insertProfitEditText.setText(itemProfitString);
                    insertProfitEditText.setSelection(itemProfitString.length());
                }
                else {
                    if (itemProfitString.contains(".")) {
                        int decimalPosition = itemProfitString.indexOf('.');
                        if (decimalPosition == 1) {
                            itemProfitString = "$0.";
                            insertProfitEditText.setText("$0.");
                            insertProfitEditText.setSelection(3);
                            decimalPosition = itemProfitString.indexOf('.');
                        }

                        if (itemProfitString.length() > decimalPosition + 3) {

                            itemProfitString = itemProfitString.substring(0, decimalPosition + 3);
                            insertProfitEditText.setText(itemProfitString);
                            insertProfitEditText.setSelection(itemProfitString.length());
                        }
                    }
                }
            }
        });

        insertProfitEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER))
                {
                    if(itemProfitString.indexOf(".") == itemProfitString.length()-1)
                    {
                        itemProfitString = itemProfitString + "00";
                        insertProfitEditText.setText(itemProfitString);
                        insertProfitEditText.setSelection(itemProfitString.length());
                    }
                    else if (itemProfitString.indexOf(".") == itemProfitString.length()-2)
                    {
                        itemProfitString = itemProfitString + "0";
                        insertProfitEditText.setText(itemProfitString);
                        insertProfitEditText.setSelection(itemProfitString.length());
                    }
                    return true;
                }
                return false;
            }
        });

        createItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!(insertNameEditText.getText().toString().trim().matches("") ||
                        insertProfitEditText.getText().toString().trim().equals("$"))) {

                    processNewList();
                    switch (id) {
                        case 0:
                    passData(itemCellList);

                        case 1:


                        case 2:

                    }
                }
                else
                {
                    Toast.makeText(getActivity(),"PLEASE FILL IN ALL OF THE FIELDS", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public interface OnDataPass {                                // Handles the list being sent back to main activity
        public void onDataPass(List<MainCellItemClass> data);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPass) context;
    }
    public void passData(List<MainCellItemClass> data) {
        dataPasser.onDataPass(data);
    }

    public void processNewList()
    {
        MainCellItemClass item = new MainCellItemClass();
        item.setName(insertNameEditText.getText().toString());
        item.setMoney(Double.parseDouble(insertProfitEditText.getText().toString().substring(1)));
        itemCellList.add(item);
    }

    public List<MainCellItemClass> reverseJsonFile(String jsonFile)
    {
        TypeToken<Map<Integer,List<MainCellItemClass>>> token = new TypeToken<Map<Integer,List<MainCellItemClass>>>(){};

        Gson gson = new Gson();
        Map<Integer,List<MainCellItemClass>> map = gson.fromJson(jsonFile,token.getType());
        List<MainCellItemClass> returnedList = map.get(1);
        return returnedList;
    }
}
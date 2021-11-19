package com.example.profittracker.ui.jobs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.profittracker.ListViewAdapter;
import com.example.profittracker.MainCellItemClass;
import com.example.profittracker.R;

import java.util.ArrayList;
import java.util.List;

public class JobsFragment extends Fragment {

    private JobsViewModel jobsViewModel;
    ListView listViewItemDisplays;
    List<MainCellItemClass> testList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        jobsViewModel =
                new ViewModelProvider(this).get(JobsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_jobs, container, false);
        listViewItemDisplays = root.findViewById(R.id.listView_itemDisplays_displayFragment);

       cellListTester();

        ListViewAdapter mainListViewAdapter = new ListViewAdapter(getContext(),R.layout.item_cell, testList);
        listViewItemDisplays.setAdapter(mainListViewAdapter);

        Log.e("potato",testList.get(0).getMoney()+"");

        return root;
    }

    public void cellListTester()
    {

        testList = new ArrayList<>();
        MainCellItemClass first = new MainCellItemClass();
        first.setMoney(1405);
        first.setName("Tay Ho");
        MainCellItemClass second = new MainCellItemClass();
        second.setMoney(234);
        second.setName("Chase Bank");
        testList.add(first);
        testList.add(second);
    }
}
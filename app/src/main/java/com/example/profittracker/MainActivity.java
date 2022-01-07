package com.example.profittracker;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.Toast;

import com.example.profittracker.ui.itemsetups.AddItemFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AddItemFragment.OnDataPassFromAddItemFragment, SettingsFragment.OnDataPassFromSettingsFragment {

    private AppBarConfiguration mAppBarConfiguration;
    private boolean fragmentID; // tells which fragments are active
    private List<MainCellItemClass> jobCellItemsList, stockCellItemsList, cryptoCellItemsList, othersCellItemsList, taxcellItemsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Bundle bundle = new Bundle();

                switch(item.getItemId())
                {
                    case R.id.nav_home:
                        Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment).navigate(R.id.nav_home);
                        Toast.makeText(MainActivity.this,"potato", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.nav_gallery:
                        bundle.putString("jobCellItemsListJson", createJsonFile(jobCellItemsList));
                        Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment).navigate(R.id.nav_gallery, bundle);
                        Toast.makeText(MainActivity.this,"job", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.nav_slideshow:
                        Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment).navigate(R.id.nav_slideshow);
                        Toast.makeText(MainActivity.this,"slide", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        });

        initialSetup();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Bundle bundle = new Bundle();


        switch(item.getItemId())
        {
            case R.id.action_settings:
                bundle.putString("jsonJobList", createJsonFile(jobCellItemsList));
                Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.settingsFragment, bundle);
                return true;
                
            case R.id.action_addJob:
                bundle.putInt("ID",0);
                bundle.putString("jsonStringForAddition", createJsonFile(jobCellItemsList));
                Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment).navigate(R.id.addItemFragment, bundle);
                fragmentID = false;
                return true;

            case R.id.action_addStock:
                bundle.putInt("ID",1);
                Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment).navigate(R.id.addItemFragment, bundle);
                fragmentID = false;
                return true;

            case R.id.action_addCrypto:
                bundle.putInt("ID",2);
                Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment).navigate(R.id.addItemFragment, bundle);
                fragmentID = false;
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        if(fragmentID == true)
        {
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();}
        Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment).navigate(R.id.nav_home);
        fragmentID = true;
        return true;
    }


    public void initialSetup()
    {
        fragmentID = true;
        try {
            FileInputStream fileInputStream = openFileInput("JobListFile");
        } catch (FileNotFoundException e) {
            writeInitialSaveFiles();
        }
        readSaveFiles();
        Log.e("HELLO", "INITIALSETUP");
        Log.e("STRING", jobCellItemsList.get(1).getName());
        Log.e("STRING", reverseJsonFile(createJsonFile(jobCellItemsList)).get(1).getName());

    }

    public void writeInitialSaveFiles()
    {
        List<MainCellItemClass> setupList = new ArrayList<>();
        MainCellItemClass initialItem = new MainCellItemClass();
        initialItem.setMoney(0.00);
        initialItem.setName("Total");
        setupList.add(initialItem);

        String initialCellItemsList = createJsonFile(setupList);
        try {
            FileOutputStream jobFileOutputStream = openFileOutput("JobListFile", MODE_PRIVATE);
            jobFileOutputStream.write(initialCellItemsList.getBytes());
            FileOutputStream stockFileOutputStream = openFileOutput("StockListFile", MODE_PRIVATE);
            stockFileOutputStream.write(initialCellItemsList.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editSaveFiles()
    {
        String jobCellItemsListJson = createJsonFile(jobCellItemsList);
        String stockCellItemsListJson = createJsonFile(stockCellItemsList);
        try {
            FileOutputStream jobFileOutputStream = openFileOutput("JobListFile", MODE_PRIVATE);
            jobFileOutputStream.write(jobCellItemsListJson.getBytes());
            FileOutputStream stockFileOutputStream = openFileOutput("StockListFile", MODE_PRIVATE);
            stockFileOutputStream.write(stockCellItemsListJson.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        readSaveFiles();
    }

    public void readSaveFiles()
    {

        try {
            FileInputStream jobFileInputStream = openFileInput("JobListFile");
            FileInputStream stockFileInputStream = openFileInput("StockListFile");

            InputStreamReader jobFileInputReader = new InputStreamReader(jobFileInputStream);
            InputStreamReader stockInputStreamReader = new InputStreamReader(stockFileInputStream);

            BufferedReader jobBufferedReader = new BufferedReader(jobFileInputReader);
            BufferedReader stockBufferedReader = new BufferedReader(stockInputStreamReader);

            String jobInputString = jobBufferedReader.readLine();
            String stockInputString = stockBufferedReader.readLine();

            jobCellItemsList = new ArrayList<>();
            stockCellItemsList = new ArrayList<>();

            jobCellItemsList = reverseJsonFile(jobInputString);
            stockCellItemsList = reverseJsonFile(stockInputString);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDataPassFromAddItemFragment(List<MainCellItemClass> data) {                        // Receives data from AddItemFragments and rewrites files
        jobCellItemsList = data;
        editSaveFiles();
    }

    @Override
    public void onDataPassFromSettingsFragment(List<MainCellItemClass> data) {                        // Receives data from AddItemFragments and rewrites files
        if(data.get(0).getName().equals("ItWouldTruelyBeAShameIfSomeoneTriedToNameACompanyThisString")) {

        }
        else {jobCellItemsList = data;}

        editSaveFiles();
    }

    public String createJsonFile(List<MainCellItemClass> inputList)         // Turns list into JSON
    {
        Map<Integer,List<MainCellItemClass>> map = new HashMap<>();
        map.put(1,inputList);
        Gson gson = new Gson();
        String json = gson.toJson(map);
        return json;
    }

    public List<MainCellItemClass> reverseJsonFile(String jsonFile)         // Turns Json String into List
    {
        TypeToken<Map<Integer,List<MainCellItemClass>>> token = new TypeToken<Map<Integer,List<MainCellItemClass>>>(){};

        Gson gson = new Gson();
        Map<Integer,List<MainCellItemClass>> map = gson.fromJson(jsonFile,token.getType());
        List<MainCellItemClass> returnedList = map.get(1);
        return returnedList;
    }
}
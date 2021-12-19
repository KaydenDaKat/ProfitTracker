package com.example.profittracker;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.example.profittracker.ui.home.HomeFragment;
import com.example.profittracker.ui.itemsetups.AddItemFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AddItemFragment.OnDataPass {

    private AppBarConfiguration mAppBarConfiguration;
    private boolean fragmentID; // tells which fragments are active
    private List<MainCellItemClass> jobCellItemsList;


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
        MainCellItemClass first = new MainCellItemClass();
        first.setMoney(0.00);
        first.setName("Total");
        setupList.add(first);

        String jobCellItemsListJson = createJsonFile(setupList);
        try {
            FileOutputStream fileOutputStream = openFileOutput("JobListFile", MODE_PRIVATE);
            fileOutputStream.write(jobCellItemsListJson.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editSaveFiles()
    {
        String jobCellItemsListJson = createJsonFile(jobCellItemsList);
        try {
            FileOutputStream fileOutputStream = openFileOutput("JobListFile", MODE_PRIVATE);
            fileOutputStream.write(jobCellItemsListJson.getBytes());
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
            FileInputStream fileOutputStream = openFileInput("JobListFile");
            InputStreamReader inputStreamReader = new InputStreamReader(fileOutputStream);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String inputString = bufferedReader.readLine();

            jobCellItemsList = new ArrayList<>();
            jobCellItemsList = reverseJsonFile(inputString);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDataPass(List<MainCellItemClass> data) {                        // Receives data from fragment and rewrites files
        Log.e("LOG DATA PASS","hello " + data.get(1).getName());
        jobCellItemsList = data;
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
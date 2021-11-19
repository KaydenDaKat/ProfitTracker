package com.example.profittracker;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.example.profittracker.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private boolean fragmentID; // tells which fragments are active

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
                switch(item.getItemId())
                {
                    case R.id.nav_home:
                        Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment).navigate(R.id.nav_home);
                        Toast.makeText(MainActivity.this,"potato", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.nav_gallery:
                        Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment).navigate(R.id.nav_gallery);
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
    }
}
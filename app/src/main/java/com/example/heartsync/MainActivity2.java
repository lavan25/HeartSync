package com.example.heartsync;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity2 extends AppCompatActivity{

    private ImageView imageView12;
    private ImageView imageView13;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ListView navList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setTheme(R.style.Base_Theme_HeartSync);

        String username = getIntent().getStringExtra("key_username");
        Log.d("UsernameCheck", "Username received: " + username);

        drawerLayout = findViewById(R.id.drawerLayout);
        navList = findViewById(R.id.navList);

        // Set up the custom Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.drawer_open,
                R.string.drawer_close
        );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set up the navigation drawer menu
        navList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                new String[]{"Logout"}));

        navList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    logoutUser();
                }
                drawerLayout.closeDrawers();
            }
        });

        // Load HomeFragment by default
        if (savedInstanceState == null) {
            openHomeFragment();
        }

        imageView12 = findViewById(R.id.imageView12);
        imageView12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomeFragment();
                imageView12.setSelected(true);
                imageView13.setSelected(false);
            }
        });
        imageView13=findViewById(R.id.imageView13);
        imageView13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfileFragment(username);
                imageView12.setSelected(false);
                imageView13.setSelected(true);
            }
        });

        imageView12.setSelected(true);
        imageView13.setSelected(false);

    }

    private void openHomeFragment() {
        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container2, homeFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void openProfileFragment(String username) {
        ProfileFragment profileFragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString("username", username);
        profileFragment.setArguments(args);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container2, profileFragment)
                .addToBackStack(null)
                .commit();
    }
    private void logoutUser() {
        finishAffinity();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

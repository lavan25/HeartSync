package com.example.heartsync;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView2 = findViewById(R.id.textView2);
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide existing views
                hideViews();

                // Replace fragment
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new Buffer());
                transaction.addToBackStack(null); // Optional, to enable back navigation
                transaction.commit();
            }
        });
    }

    private void hideViews() {
        // Hide the views you want to hide
        textView2.setVisibility(View.GONE);
        // ... hide other views as needed
    }

    // Override onBackPressed to handle visibility restoration
    @Override
    public void onBackPressed() {
        // Show the views again when navigating back from the fragment
        textView2.setVisibility(View.VISIBLE);
        // ... show other views as needed

        super.onBackPressed();
    }
}

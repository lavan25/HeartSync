package com.example.heartsync;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.Toast;

public class MainActivity7 extends AppCompatActivity {

    RatingBar ratingBar;
    Button button6;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        addListenerOnButtonClick();
    }

    public void addListenerOnButtonClick(){
        ratingBar = findViewById(R.id.ratingBar);
        button6 = findViewById(R.id.button6);
        webView = findViewById(R.id.webView);

        button6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                String rating = String.valueOf(ratingBar.getRating());
                Toast.makeText(getApplicationContext(), "You have rated " + rating, Toast.LENGTH_LONG).show();
                showPopupMenu(arg0); // Force onCreateOptionsMenu to be called
            }
        });

        String url = "https://www.keralamatrimony.com/";
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.action_share) {
            showPopupMenu(findViewById(R.id.action_share)); // Pass the view associated with the options menu item
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.menu_share); // Create a popup_menu.xml file with your desired items
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId == R.id.action_twitter) {
                    // Handle Twitter share
                    return true;
                } else if (itemId == R.id.action_instagram) {
                    // Handle Instagram share
                    return true;
                } else if (itemId == R.id.action_facebook) {
                    // Handle Facebook share
                    return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }
}

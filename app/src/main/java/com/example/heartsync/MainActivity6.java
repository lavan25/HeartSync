package com.example.heartsync;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.Toolbar;
import android.widget.SeekBar;
import android.widget.ToggleButton;
import android.widget.VideoView;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity6 extends AppCompatActivity {

    private VideoView videoView;
    private SeekBar seekBar;
    private ToggleButton toggleButton;
    private Handler updateHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        videoView = findViewById(R.id.videoView);
        seekBar = findViewById(R.id.seekBar);
        toggleButton = findViewById(R.id.toggleButton);

        // Set the video source
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video));

        // Start video playback when the activity is created


        // Set the seek bar max duration to the video duration
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoView.start();
                seekBar.setMax(mp.getDuration());
                // Start updating the seek bar
                updateSeekBar();
            }
        });

        // Set seek bar listener to seek video when the user changes the progress manually
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    videoView.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Set listener for the toggle button to show the Snackbar when clicked
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggleButton.isChecked()) {
                    // User is interested
                    showSnackbar("You're interested");
                } else {
                    // User is not interested (Undo the toggle button action)
                    showSnackbar("Not interested");
                    toggleButton.setChecked(true);
                }
            }
        });
    }

    // Method to update the seek bar
    private void updateSeekBar() {
        seekBar.setProgress(videoView.getCurrentPosition());
        if (videoView.isPlaying()) {
            // Delay the next update by 1 second
            updateHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    updateSeekBar();
                }
            }, 1000);
        }
    }

    // Method to show the Snackbar
    private void showSnackbar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.constraintLayout), message, Snackbar.LENGTH_LONG)
                .setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Undo the toggle button action
                        toggleButton.setChecked(!toggleButton.isChecked());
                    }
                });
        snackbar.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release the VideoView when the activity is destroyed
        videoView.stopPlayback();
    }
}

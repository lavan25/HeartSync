package com.example.heartsync;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Buffer extends Fragment {

    private int[] imageResources = {R.drawable.icon1, R.drawable.icon2, R.drawable.icon3};
    private int currentIndex = 0;
    private ImageSwitcher imageSwitcher;
    private Handler handler = new Handler();
    private Runnable imageSwitcherRunnable = this::switchImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buffer, container, false);

        imageSwitcher = view.findViewById(R.id.imageSwitcher);
        imageSwitcher.setFactory(createImageViewFactory());

        startImageSwitching();

        return view;
    }

    private ViewSwitcher.ViewFactory createImageViewFactory() {
        return new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(requireContext());
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                return imageView;
            }
        };
    }

    private void switchImage() {
        imageSwitcher.setImageResource(imageResources[currentIndex]);
        currentIndex = (currentIndex + 1) % imageResources.length;

        if (currentIndex == 0) {
            // All images have been shown, navigate to MainActivity3
            handler.removeCallbacksAndMessages(null); // Stop the image switching
            navigateToMainActivity3();
        } else {
            handler.postDelayed(imageSwitcherRunnable, 800); // Switch every 2 seconds
        }
    }

    private void startImageSwitching() {
        handler.post(imageSwitcherRunnable);
    }

    private void navigateToMainActivity3() {
        // Use an Intent to navigate to MainActivity3
        Intent intent = new Intent(requireContext(), MainActivity3.class);
        startActivity(intent);
        requireActivity().finish(); // Close the current activity
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacksAndMessages(null); // Remove callbacks when fragment is destroyed
    }
}

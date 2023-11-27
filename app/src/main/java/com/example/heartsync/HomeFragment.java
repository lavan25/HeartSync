package com.example.heartsync;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.ShareCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class HomeFragment extends Fragment {



    private ImageView imageView1, imageView2, imageView3;
    private ImageView clickedImageView; // To keep track of the clicked image view


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container2, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container2, false);

        imageView1 = rootView.findViewById(R.id.imageView7);
        imageView2 = rootView.findViewById(R.id.imageView8);
        imageView3 = rootView.findViewById(R.id.imageView9);

        // Register image views for context menu
        registerForContextMenu(imageView1);
        registerForContextMenu(imageView2);
        registerForContextMenu(imageView3);

        // Set click listeners for all image views
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedImageView = imageView1;
                getActivity().openContextMenu(v); // Open the context menu for the clicked image view
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedImageView = imageView2;
                getActivity().openContextMenu(v); // Open the context menu for the clicked image view
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedImageView = imageView3;
                getActivity().openContextMenu(v); // Open the context menu for the clicked image view
            }
        });

        Button button4 = rootView.findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the MainActivity6 activity
                startActivity(new Intent(getActivity(), MainActivity6.class));

                // Check if notification permission is granted
                if (hasNotificationPermission()) {
                    // If permission is granted, show the notification
                    showNotification("You've viewed profile 1");
                } else {
                    // If permission is not granted, you can handle it here
                    // For example, show a message to the user or navigate to notification settings
                    Toast.makeText(requireContext(), "Notification permission not granted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button button2 = rootView.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity6.class));

                // Check if notification permission is granted
                if (hasNotificationPermission()) {
                    // If permission is granted, show the notification
                    showNotification("You've viewed profile 2");
                } else {
                    // If permission is not granted, you can handle it here
                    // For example, show a message to the user or navigate to notification settings
                    Toast.makeText(requireContext(), "Notification permission not granted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button button3 = rootView.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity6.class));

                // Check if notification permission is granted
                if (hasNotificationPermission()) {
                    // If permission is granted, show the notification
                    showNotification("You've viewed profile 3");
                } else {
                    // If permission is not granted, you can handle it here
                    // For example, show a message to the user or navigate to notification settings
                    Toast.makeText(requireContext(), "Notification permission not granted", Toast.LENGTH_SHORT).show();
                }
            }
        });


        FloatingActionButton floatingActionButton  = rootView.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity7.class));
            }
        });

        return rootView;
    }
    private void showNotification(String message) {
        if (hasNotificationPermission()) {
            Context context = requireContext();
            createNotificationChannel(context);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "profile_view_channel")
                    .setSmallIcon(R.drawable.icon)
                    .setContentTitle("Profile Viewed")
                    .setContentText(message)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

            try {
                notificationManager.notify(1, builder.build());
            } catch (SecurityException e) {
                // Handle the SecurityException here
                Toast.makeText(requireContext(), "Failed to show notification: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            // Permission not granted, handle this situation
            // You can show a message to the user or navigate to the app's notification settings
            Toast.makeText(requireContext(), "Notification permission not granted", Toast.LENGTH_SHORT).show();
        }
    }



    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Profile View Channel";
            String description = "Channel for profile view notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("profile_view_channel", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private boolean hasNotificationPermission() {
        return NotificationManagerCompat.from(requireContext()).areNotificationsEnabled();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.image_context_menu, menu);
        clickedImageView = (ImageView) v; // Set the clickedImageView to the clicked image view
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_copy) {
            copyImageToClipboard(clickedImageView);
            return true;
        } else if (itemId == R.id.action_download) {
            downloadImage(clickedImageView);
            return true;
        } else if (itemId == R.id.action_share) {
            shareImage();
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    private void copyImageToClipboard(ImageView imageView) {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        ClipboardManager clipboard = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);

        // Create an Intent to share the image
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        Uri imageUri = bitmapToUri(requireContext(), bitmap);
        intent.putExtra(Intent.EXTRA_STREAM, imageUri);

        // Create ClipData with the image URI
        ClipData clip = ClipData.newUri(requireContext().getContentResolver(), "Image", imageUri);
        clip.addItem(new ClipData.Item(intent));
        clipboard.setPrimaryClip(clip);

        Toast.makeText(requireContext(), "Image copied to clipboard", Toast.LENGTH_SHORT).show();
    }

    private void downloadImage(ImageView imageView) {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        String fileName = "image.jpg";
        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(directory, fileName);

        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            Toast.makeText(requireContext(), "Image downloaded", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Download failed", Toast.LENGTH_SHORT).show();
        }
    }

    public void shareImage() {
        String mimeType = "image/jpeg";

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType(mimeType);

        startActivity(Intent.createChooser(shareIntent, "Share this image with"));
    }

    private Uri bitmapToUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Image", null);
        return Uri.parse(path);
    }
}

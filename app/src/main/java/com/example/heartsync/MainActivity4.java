package com.example.heartsync;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity4 extends AppCompatActivity {

    private EditText editTextDate;
    private EditText editTextTime;
    private EditText editTextUsername;

    private EditText editTextEmailAddress;
    private EditText editTextPhoneNumber;

    private EditText editTextPostalAddress;
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextPassword;
    private RadioGroup radioGroup;
    private String[] spinnerItems = {"Christian", "Hindu", "Muslim", "Sikh", "Buddhist"};
    private Button buttonRegister;
    private CheckBox checkBox,checkBox2,checkBox3,checkBox4;
    private Spinner spinner;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        editTextDate = findViewById(R.id.editTextDate);
        editTextTime = findViewById(R.id.editTextTime);
        buttonRegister = findViewById(R.id.button4);
        editTextUsername = findViewById(R.id.editTextText3);
        editTextPassword = findViewById(R.id.editTextTextPassword);
        editTextFirstName = findViewById(R.id.editTextText);
        editTextLastName = findViewById(R.id.editTextText2);
        editTextPhoneNumber = findViewById(R.id.editTextNumber);
        editTextPostalAddress = findViewById(R.id.editTextTextPostalAddress);
        editTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        radioGroup = findViewById(R.id.radioGroup);
        checkBox = findViewById(R.id.checkBox);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        checkBox4 = findViewById(R.id.checkBox4);
        spinner = findViewById(R.id.spinner);
        db = new DBHelper(this);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerItems);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        buttonRegister.setOnClickListener(this::showBottomSheet);


        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(v);
            }
        });

        editTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(v);
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = editTextUsername.getText().toString();

                if (validateFields()) {
                    showBottomSheet(v);
                }
                if (hasNotificationPermission()) {
                    // If permission is granted, show the notification
                    showNotification("You've registered yourself");
                } else {
                    // If permission is not granted, you can handle it here
                    // For example, show a message to the user or navigate to notification settings
                    Toast.makeText(MainActivity4.this, "Notification permission not granted", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void showNotification(String message) {
        if (hasNotificationPermission()) {
            Context context = MainActivity4.this;
            createNotificationChannel(context);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "profile_view_channel")
                    .setSmallIcon(R.drawable.icon)
                    .setContentTitle("Registration")
                    .setContentText(message)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

            try {
                notificationManager.notify(1, builder.build());
            } catch (SecurityException e) {
                // Handle the SecurityException here
                Toast.makeText(MainActivity4.this, "Failed to show notification: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            // Permission not granted, handle this situation
            // You can show a message to the user or navigate to the app's notification settings
            Toast.makeText(MainActivity4.this, "Notification permission not granted", Toast.LENGTH_SHORT).show();
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
        return NotificationManagerCompat.from(MainActivity4.this).areNotificationsEnabled();
    }

    public void showDatePicker(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR) - 18; // Adjusted to allow users 18 years and older
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Validate the selected date
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, monthOfYear, dayOfMonth);

                        Calendar minAge = Calendar.getInstance();
                        minAge.add(Calendar.YEAR, -18); // 18 years ago

                        if (selectedDate.before(minAge)) {
                            String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                            editTextDate.setText(date);
                        } else {
                            Toast.makeText(MainActivity4.this, "Must be 18 or older to register", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                year, month, day);

        datePickerDialog.show();
    }


    public void showTimePicker(View view) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String time = hourOfDay + ":" + minute;
                        editTextTime.setText(time);
                    }
                },
                hour, minute, false);

        timePickerDialog.show();
    }

    public void showBottomSheet(View view) {

        // Inflate the bottom sheet layout
        View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_terms, null);

        // Create the bottom sheet dialog
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(bottomSheetView);

        // Find the "I Agree" button in the bottom sheet
        Button buttonAgree = bottomSheetView.findViewById(R.id.buttonAgree);

        // Set a click listener for the "I Agree" button
        buttonAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show an alert dialog to confirm the details
                new AlertDialog.Builder(MainActivity4.this)
                        .setTitle("Confirmation")
                        .setMessage("Confirm all the details?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String user = editTextUsername.getText().toString();
                                String pass = editTextPassword.getText().toString();
                                String firstname = editTextFirstName.getText().toString();
                                String lastname = editTextLastName.getText().toString();
                                String date = editTextDate.getText().toString();
                                String phone = editTextPhoneNumber.getText().toString();
                                String email = editTextEmailAddress.getText().toString();
                                String address = editTextPostalAddress.getText().toString();
                                String time = editTextTime.getText().toString();
                                Boolean checkuser = db.checkusername(user);
                                if(checkuser==false){
                                    Boolean insert = db.insertdata(user, pass, firstname, lastname, date, phone, email, address, time);
                                    if(insert == true){
                                        Toast.makeText(MainActivity4.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MainActivity4.this, MainActivity5.class);
                                        startActivity(intent);
                                    }else {
                                        Toast.makeText(MainActivity4.this,"Registration failed",Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(MainActivity4.this, "User already exits please login", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                // Dismiss the bottom sheet dialog
                bottomSheetDialog.dismiss();
            }
        });

        // Show the bottom sheet dialog
        bottomSheetDialog.show();
    }

    private boolean validateFields() {
        String firstName = editTextFirstName.getText().toString();
        String lastName = editTextLastName.getText().toString();
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        String email = editTextEmailAddress.getText().toString();
        String phoneNumber = editTextPhoneNumber.getText().toString();
        String dateOfBirth = editTextDate.getText().toString();
        String accountCreationTime = editTextTime.getText().toString();
        String address = editTextPostalAddress.getText().toString();

        if (firstName.isEmpty() || firstName.matches(".*\\d.*")) {
            editTextFirstName.setError("Please enter a valid first name");
            return false;
        }

        if (lastName.isEmpty() || lastName.matches(".*\\d.*")) {
            editTextLastName.setError("Please enter a valid last name");
            return false;
        }

        if (username.isEmpty()){
            editTextUsername.setError("Enter a username");
            return false;
        }

        if (password.isEmpty()){
            editTextPassword.setError("Please enter a password");
            return false;
        }

        if (email.isEmpty() || !isValidEmail(email)) {
            editTextEmailAddress.setError("Please enter valid email address");
            return false;
        }

        if (phoneNumber.isEmpty() || phoneNumber.length() != 10) {
            editTextPhoneNumber.setError("Please enter a valid phone number(10 digits)");
            return false;
        }

        if (address.isEmpty()){
            editTextPostalAddress.setError("Please enter your address");
            return false;
        }

        if (dateOfBirth.isEmpty()) {
            editTextDate.setError("Please enter your date of birth");
            return false;
        }

        if (accountCreationTime.isEmpty()){
            editTextTime.setError("Please select the time");
            return false;
        }

        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select a gender", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!checkBox.isChecked() && !checkBox2.isChecked() && !checkBox3.isChecked() && !checkBox4.isChecked()) {
            Toast.makeText(this, "Please select at least one hobby", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (spinner.getSelectedItemPosition() == -1) {
            Toast.makeText(this, "Please select a religion", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() ||
                password.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() ||
                dateOfBirth.isEmpty() || accountCreationTime.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    private boolean isValidEmail(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}



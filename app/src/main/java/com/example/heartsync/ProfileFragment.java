package com.example.heartsync;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    TextView firstname, lastname, username, email, phone, dob, address,time;
    String username1;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container2, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container2, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerview);

        List<item> items = new ArrayList<item>();
        items.add(new item("Your Profile"));

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MyAdaptar(getContext(), items));


        firstname = rootView.findViewById(R.id.firstname);
        lastname = rootView.findViewById(R.id.lastname);
        username = rootView.findViewById(R.id.username);
        email = rootView.findViewById(R.id.email);
        phone = rootView.findViewById(R.id.phone);
        dob = rootView.findViewById(R.id.dob);
        address = rootView.findViewById(R.id.address);
        time = rootView.findViewById(R.id.time);

        username1 = getArguments().getString("username");
        getUserDetails();

        return rootView;
    }
    public void getUserDetails() {
        DBHelper db = new DBHelper(getActivity());
        ArrayList<UserModal> al = db.getUser(username1);
        Log.d("ProfileFragment", "getUserDetails: username1 = " + username1);

        if (!al.isEmpty()) {
            UserModal userModal = al.get(0);

            firstname.setText(userModal.getFirstname());
            lastname.setText(userModal.getLastname());
            username.setText(userModal.getUsername());
            email.setText(userModal.getEmail());
            phone.setText(userModal.getPhonenumber());
            address.setText(userModal.getAddress());
            dob.setText(userModal.getDate());
            time.setText(userModal.getTime());
        } else {
            Toast.makeText(getActivity(), "No user details found", Toast.LENGTH_SHORT).show();
        }
    }

}


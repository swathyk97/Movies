package com.example.mylogin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;


public class RegisterFragment extends Fragment {
    Button buttonRegister;
    EditText etName, etEmail, etPassword;
    String userName, name, pass;
    SharedPreferences.Editor editor;
    FragmentTransaction fragmentTransaction;


    @Override
    public void onAttach(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userfile", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_register, container, false);
        etName = view.findViewById(R.id.et_name);
        etEmail = view.findViewById(R.id.et_email);
        etPassword = view.findViewById(R.id.et_password);


        buttonRegister = view.findViewById(R.id.btn_register);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = etName.getText().toString();
                userName = etEmail.getText().toString();
                pass = etPassword.getText().toString();
                if (validateEmailAndPassword()) {
                    editor.putString("name", name);
                    editor.putString("userName", userName);
                    editor.putString("pass", pass);
                    editor.apply();


                }
            }

        });
        return view;
    }

    private boolean validateEmailAndPassword() {
        boolean status = true;
        int MINIMUM_LENGTH_EMAIL = 4;
        int MINIMUM_LENGTH_PASSWORD = 8;


        if (etEmail.getText().toString().trim().length() < MINIMUM_LENGTH_EMAIL) {
            Toast.makeText(getContext(), "require minimum email length 4", Toast.LENGTH_SHORT).show();

        } else if (etPassword.getText().toString().trim().length() < MINIMUM_LENGTH_PASSWORD) {
            Toast.makeText(getContext(), "require minimum password length 8", Toast.LENGTH_SHORT).show();
        } else {
            if (isValidEmail(etEmail.getText().toString().trim())) {

                Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
                Fragment fragment = new LoginFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();

            } else {
                Toast.makeText(getContext(), "Invalid email address", Toast.LENGTH_SHORT).show();

            }
        }
        return status;
    }

    public static boolean isValidEmail(String id) {
        Pattern emailPattern = Pattern.compile("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$");
        return emailPattern.matcher(id).matches();
    }
}

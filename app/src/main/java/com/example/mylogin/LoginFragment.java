package com.example.mylogin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginFragment extends Fragment {
    Button buttonLogin, buttonRegister;
    EditText email, password;
    String userName, pass;
    SharedPreferences sharedPreferences, loginPreferences;
    SharedPreferences.Editor editor, editor1;
    Fragment fragment;
    private static FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    public void onAttach(Context context) {
        sharedPreferences = context.getSharedPreferences("userfile", Context.MODE_PRIVATE);
        loginPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor1 = loginPreferences.edit();

        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_fragment_login, container, false);
        email = view.findViewById(R.id.et_email);
        password = view.findViewById(R.id.et_password);

        buttonLogin = view.findViewById(R.id.btn_login);
        buttonRegister = view.findViewById(R.id.btn_register);

        final String uName, uPass, login;
        uName = sharedPreferences.getString("userName", null);
        uPass = sharedPreferences.getString("pass", null);

        Log.i("Log", "uName:" + uName);
        Log.i("Log", "uPass:" + uPass);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = email.getText().toString();
                pass = password.getText().toString();

                if (userName.equals(uName) && pass.equals(uPass)) {
                    intent();
                    editor1.putString("email", userName);
                    editor1.putString("password", pass);
                    editor1.putString("name", sharedPreferences.getString("name", null));
                    editor1.apply();
                } else {
                    Toast.makeText(getContext(), "incorrect email or password", Toast.LENGTH_SHORT).show();
                }

            }
        });
        login = loginPreferences.getString("email", null);
        if (login != null) {
            Intent homeIntent = new Intent(getActivity(), UserActivity.class);
            startActivity(homeIntent);
            getActivity().finish();
        }


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new RegisterFragment();
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
            }
        });
        return view;


    }

    private void intent() {
        Intent homeIntent = new Intent(getActivity(), UserActivity.class);
        startActivity(homeIntent);
        getActivity().finish();
        Toast.makeText(getContext(), "Login", Toast.LENGTH_SHORT).show();
    }


}

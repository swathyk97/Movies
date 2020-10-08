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

import com.example.mylogin.Presenter.LoginInterface;
import com.example.mylogin.Presenter.LoginPresenter;


public class LoginFragment extends Fragment implements LoginInterface.View {
    Button buttonLogin, buttonRegister;
    EditText email, password;
    String userName, pass;
    SharedPreferences sharedPreferences, loginPreferences;
    SharedPreferences.Editor editor, editor1;

    private static FragmentManager fragmentManager;

    LoginInterface.Presenter presenter;

    @Override
    public void onAttach(Context context) {
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
        presenter = new LoginPresenter(getContext(),this);
        fragmentManager=getFragmentManager();
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
                presenter.LoginClicked(userName,pass,fragmentManager);


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
               presenter.RegisterClicked(fragmentManager);

            }
        });
        return view;


    }
    @Override
    public void intent() {
        Intent homeIntent = new Intent(getActivity(), UserActivity.class);
        startActivity(homeIntent);
        getActivity().finish();
        Toast.makeText(getContext(), "Login", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void registerFailV(String message) {
        Toast.makeText(getContext(),message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void registerSuccessV(String message) {
        Toast.makeText(getContext(),message, Toast.LENGTH_SHORT).show();
    }
}

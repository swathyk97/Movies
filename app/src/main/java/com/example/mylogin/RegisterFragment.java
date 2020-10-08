package com.example.mylogin;

import android.content.Context;
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

import com.example.mylogin.Presenter.RegisterInterface;
import com.example.mylogin.Presenter.RegisterPresenter;


public class RegisterFragment extends Fragment implements RegisterInterface.View{
    Button buttonRegister;
    EditText etName, etEmail, etPassword;
    String userName, name, pass;
    SharedPreferences.Editor editor;
    FragmentTransaction fragmentTransaction;
    RegisterInterface.Presenter mPresenter;
    FragmentManager fragmentManager;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment_register, container, false);
        etName = view.findViewById(R.id.et_name);
        etEmail = view.findViewById(R.id.et_email);
        etPassword = view.findViewById(R.id.et_password);
        name = etName.getText().toString();
        userName = etEmail.getText().toString();
        pass = etPassword.getText().toString();
        mPresenter = new RegisterPresenter(getContext(), this);
        mPresenter.initP();
        fragmentManager=getFragmentManager();

        buttonRegister = view.findViewById(R.id.btn_register);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.RegisterClickedP(etEmail.getText().toString(),etPassword.getText().toString(),fragmentManager);
                AppPrefs.init(getContext());
                AppPrefs.setName(etName.getText().toString());
                AppPrefs.setUserName(etEmail.getText().toString());
                AppPrefs.setPassword(etPassword.getText().toString());
            }

        });

        return view;
    }



    @Override
    public void initV() {


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

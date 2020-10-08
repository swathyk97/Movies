package com.example.mylogin.Presenter;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mylogin.AppPrefs;
import com.example.mylogin.R;
import com.example.mylogin.RegisterFragment;
import com.example.mylogin.UserActivity;

public class LoginPresenter implements LoginInterface.Presenter {
    FragmentTransaction fragmentTransaction;
    private final Context mContext;
    LoginInterface.View mView;

    public LoginPresenter(Context mContext, LoginInterface.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }
    @Override
    public void LoginClicked(String userName, String password, FragmentManager fragmentManager) {
        if (userName.equals(AppPrefs.getUserName()) && password.equals(AppPrefs.getPassword())) {
            AppPrefs.setEmail(userName);
            AppPrefs.setPASS(password);
            mView.intent();
        } else {
           mView.registerFailV("incorrect email or password");
        }

    }

    @Override
    public void RegisterClicked(FragmentManager fragmentManager) {
        Fragment fragment = new RegisterFragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

}

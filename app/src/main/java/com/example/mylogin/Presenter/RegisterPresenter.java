package com.example.mylogin.Presenter;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mylogin.LoginFragment;
import com.example.mylogin.R;

import java.util.regex.Pattern;

public class RegisterPresenter implements RegisterInterface.Presenter {
    private final Context mContext;
    RegisterInterface.View mView;
    int MINIMUM_LENGTH_EMAIL = 4;
    int MINIMUM_LENGTH_PASSWORD = 8;
    FragmentTransaction fragmentTransaction;

    public RegisterPresenter(Context mContext, RegisterInterface.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }


    @Override
    public void initP() {
mView.initV();
    }

    @Override
    public void RegisterClickedP(String userName, String password,FragmentManager fragmentManager) {
        Log.d("data","userName:"+userName+" "+"password"+password);
            if (userName.length() < MINIMUM_LENGTH_EMAIL) {
                mView.registerFailV("require minimum email length 4");
            } else if (password.length() < MINIMUM_LENGTH_PASSWORD) {
                mView.registerFailV("require minimum password length 8");
            } else {
                if (isValidEmail(userName)) {
                    mView.registerSuccessV("success");
                    Fragment fragment = new LoginFragment();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.commit();

                } else {
                    mView.registerFailV("Invalid email address");

                }
            }


        }


    public static boolean isValidEmail(String id) {
        Pattern emailPattern = Pattern.compile("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$");
        return emailPattern.matcher(id).matches();
    }




}

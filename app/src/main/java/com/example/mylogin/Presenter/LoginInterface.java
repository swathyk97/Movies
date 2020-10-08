package com.example.mylogin.Presenter;

import androidx.fragment.app.FragmentManager;

public interface LoginInterface {
    interface View {
        void registerFailV(String message);
        void intent();
        void registerSuccessV(String message);
    }
    interface Presenter {
        void LoginClicked(String userName, String password, FragmentManager fragmentManager);

        void RegisterClicked(FragmentManager fragmentManager);

    }
}

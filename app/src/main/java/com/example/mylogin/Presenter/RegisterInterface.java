package com.example.mylogin.Presenter;

import androidx.fragment.app.FragmentManager;

public interface RegisterInterface {
    interface View {
        void initV();

        void registerFailV(String message);

        void registerSuccessV(String message);
    }
    interface Presenter {
        void initP();

        void RegisterClickedP(String userName, String password, FragmentManager fragmentManager);
    }
}

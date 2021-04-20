package com.ucas.chat.base;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;


public class BaseFragment extends Fragment implements View.OnClickListener{

    protected static String TAG;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}

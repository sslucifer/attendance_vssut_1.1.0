package com.example.austine.login;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class dia extends DialogFragment {
    public dia() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("DialogFragment Demo");
        View view = inflater.inflate(R.layout.dialog_box, container);

        return view;
    }
}

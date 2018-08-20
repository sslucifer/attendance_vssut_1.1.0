package com.example.austine.login;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class auth_dia extends DialogFragment {
    private TextView auth_fail_button;
    public auth_dia() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("DialogFragment Demo");
        View view = inflater.inflate(R.layout.auth_dia, container);


        return view;
    }
}

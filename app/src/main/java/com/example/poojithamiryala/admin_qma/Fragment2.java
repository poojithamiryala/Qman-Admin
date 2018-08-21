package com.example.poojithamiryala.admin_qma;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by poojitha miryala on 18-03-2018.
 */

public class Fragment2  extends android.support.v4.app.Fragment
{
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.timesettings, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        // getActivity().setTitle("home");
    }

    public static android.support.v4.app.Fragment newInstance() {
        android.support.v4.app.Fragment fragment = new Fragment2();
        return fragment;
    }

}

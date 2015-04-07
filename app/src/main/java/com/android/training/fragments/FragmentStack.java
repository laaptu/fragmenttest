package com.android.training.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.training.Extras;
import com.android.training.R;

/**
 * Created by laaptu on 4/7/15.
 */
public class FragmentStack extends Fragment {

    private TextView txtTitle;
    private RelativeLayout parentLayout;
    private Context context;

    private static final String TAG = "FragmentStack";
    private int color = android.R.color.darker_gray;
    private String title = "No Title";


    public FragmentStack() {
    }

    public static FragmentStack getInstance(Bundle params) {
        FragmentStack fragment = new FragmentStack();
        fragment.setArguments(params);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stack, container, false);
        txtTitle = (TextView) view.findViewById(R.id.txt_title);
        parentLayout = (RelativeLayout) view.findViewById(R.id.bucket);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (hasValidData(savedInstanceState)) {
            System.out.println(TAG + ":onActivityCreated: savedInstancestate: title:color= " + title + " : " + color);
        } else if (hasValidData(getArguments())) {
            System.out.println(TAG + ":onActivityCreated: getArguments: title:color= " + title + " : " + color);
        }

        txtTitle.setText(title);
        parentLayout.setBackgroundColor(color);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putInt(Extras.COLOR_BG, color);
        outState.putString(Extras.TITLE, title);
    }

    private boolean hasValidData(Bundle params) {
        boolean hasData = params != null && params.containsKey(Extras.COLOR_BG);
        if (hasData) {
            title = params.getString(Extras.TITLE, title);
            color = params.getInt(Extras.COLOR_BG, color);
        }
        return hasData;
    }
}

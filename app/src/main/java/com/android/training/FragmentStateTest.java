package com.android.training;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by laaptu on 4/6/15.
 */
public class FragmentStateTest extends Fragment {

    private String textArg = "FragmentStateTest";
    private TextView textView;
    public static final String TAG = "TextArg";

    public static FragmentStateTest getInstance(Bundle params){
        FragmentStateTest fragment = new FragmentStateTest();
        fragment.setArguments(params);
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        textView = (TextView) view.findViewById(R.id.textView);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            textArg = getArguments().getString(TAG, textArg);
        } else {
            textArg = savedInstanceState.getString(TAG, textArg);
        }

        System.out.println("Frag: onActivityCrated: textArgs = "+textArg);

        textView.setText(textArg);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TAG,textArg);
    }

    public void addText(String textArg) {
        this.textArg = textArg;
        if (textView != null) {
            textView.setText(this.textArg);
        }
    }
}

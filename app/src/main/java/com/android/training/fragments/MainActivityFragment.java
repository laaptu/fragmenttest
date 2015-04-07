package com.android.training.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.training.Extras;
import com.android.training.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private boolean createFromXml = true;
    private TextView textView;
    private String textArgs = "No argument Passed";
    public static final String TAG = "MainActivityFragment";

    public MainActivityFragment() {
    }


    public static MainActivityFragment getInstance(Bundle params) {
        MainActivityFragment mainActivityFragment = new MainActivityFragment();
        mainActivityFragment.setArguments(params);
        mainActivityFragment.setRetainInstance(true);
        return mainActivityFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i(TAG, "onAttach()");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()");
        this.setRetainInstance(true);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(Extras.TEXT,textArgs);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_first, container, false);
        Log.i(TAG, "onCreateView()");


        if (createFromXml)
            return createFromXml(inflater, container, R.layout.fragment_first);
        else
            return createFromDynamicView();
    }




    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated()");
        if (savedInstanceState != null) {
            //createFromXml = savedInstanceState.
            textArgs = savedInstanceState.getString(Extras.TEXT, textArgs);
        } else if (getArguments() != null) {
            textArgs = getArguments().getString(Extras.TEXT, textArgs);
        }

        Log.i("TextArgs",textArgs);
        textView.setText(textArgs);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
    }


    private View createFromXml(LayoutInflater inflater, ViewGroup container, int layoutId) {
        View view = inflater.inflate(layoutId, container, false);
        textView = (TextView) view.findViewById(R.id.textView);
        return view;
    }

    private View createFromDynamicView() {
        textView = new TextView(getActivity());
        return textView;
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach()");
    }


    public void addText(String textArgs) {
        this.textArgs = textArgs;
        if (textView != null) {
            textView.setText(textArgs);
        }
    }
}

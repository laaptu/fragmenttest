package com.android.training;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.training.fragments.FragmentStateTest;

/**
 * Created by laaptu on 4/6/15.
 */
public class ActivityFragmentState extends Activity {

    public static final String TAG_FRAG = "Frag";

    private FragmentStateTest fragment;
    private EditText editText;


    //http://stackoverflow.com/questions/15313598/once-for-all-how-to-correctly-save-instance-state-of-fragments-in-back-stack

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_frag_content);
        editText = (EditText) findViewById(R.id.editText);


        if (savedInstanceState != null && savedInstanceState.containsKey(TAG_FRAG)) {
            System.out.println("Ac:onCreate(): contains value");
            fragment = (FragmentStateTest) getFragmentManager().getFragment(savedInstanceState, TAG_FRAG);
        }

        if (fragment == null) {
            Bundle params = new Bundle();
            params.putString(FragmentStateTest.TAG, "Argument passed text");
            fragment = FragmentStateTest.getInstance(params);
        }
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment, TAG_FRAG);
        fragmentTransaction.commit();
    }

    public void addText(View view) {
        fragment = (FragmentStateTest) getFragmentManager().findFragmentByTag(TAG_FRAG);
        if (fragment != null) {
            fragment.addText(editText.getText().toString());
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        System.out.println("Ac: onSaveInstanceState");
        getFragmentManager().putFragment(outState, TAG_FRAG, fragment);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("ActivityFragmentState: onActivityResult: requestCode,resultCode = "
                + requestCode + ", " + resultCode);
    }
}

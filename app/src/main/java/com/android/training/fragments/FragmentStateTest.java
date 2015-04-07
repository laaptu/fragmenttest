package com.android.training.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.training.DummyActivity;
import com.android.training.Extras;
import com.android.training.R;

/**
 * Created by laaptu on 4/6/15.
 */
public class FragmentStateTest extends Fragment implements View.OnClickListener {

    private String textArg = "FragmentStateTest";
    private TextView textView;
    public static final String TAG = "TextArg";
    private Button button;

    public static FragmentStateTest getInstance(Bundle params) {
        FragmentStateTest fragment = new FragmentStateTest();
        fragment.setArguments(params);
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        textView = (TextView) view.findViewById(R.id.textView);
        button = (Button) view.findViewById(R.id.button);
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

        System.out.println("Frag: onActivityCrated: textArgs = " + textArg);
        System.out.println("Frag: 0x1 value = " + Extras.RETURN_FROM_DUMMY_ACTIVITY);
        textView.setText(textArg);
        button.setOnClickListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TAG, textArg);
    }

    public void addText(String textArg) {
        this.textArg = textArg;
        if (textView != null) {
            textView.setText(this.textArg);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                goToDummyActivity();
                break;
            default:
                break;
        }
    }

    private void goToDummyActivity() {
        Intent intent = new Intent(getActivity(), DummyActivity.class);
        Bundle params = new Bundle();
        params.putInt(Extras.NUM_FIRST, 10);
        params.putInt(Extras.NUM_SECOND, 13);
        intent.putExtras(params);
        //startActivityForResult(intent, Extras.RETURN_FROM_DUMMY_ACTIVITY);
        getActivity().startActivityForResult(intent, Extras.RETURN_FROM_DUMMY_ACTIVITY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("FragStateTest: onActivityResult: requestCode,resultCode =" + requestCode + "," + resultCode);
    }
}

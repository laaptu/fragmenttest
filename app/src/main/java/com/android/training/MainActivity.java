package com.android.training;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends Activity {

    public static final String TAG = "MainActivity";
    public static final String TAG_FRAG = "Frag", TAG_CURRENT_FRAG = "CurrentFrag";
    private EditText someEditText;

    private MainActivityFragment mainActivityFragment;


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        getFragmentManager().putFragment(outState, TAG_CURRENT_FRAG, mainActivityFragment);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()");
        if (true) {
            startActivity(new Intent(this, ActivityFragmentState.class));
            this.finish();
            return;
        }

        if (savedInstanceState != null) {
            mainActivityFragment = (MainActivityFragment) getFragmentManager().getFragment(savedInstanceState, TAG_CURRENT_FRAG);
            if (mainActivityFragment != null)
                return;
        }
        //setContentFromFragmentLayout();
        setContentFromDynamicFragment();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                accessFragmentFromDynamicAddition();

            }
        }, 500);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }

    private void setContentFromDynamicFragment() {
        //setContentView(R.layout.activity_main_dynamic_fragment);
        setContentView(R.layout.activity_main_frag_content);
        someEditText = (EditText) findViewById(R.id.editText);
        //MainActivityFragment mainActivityFragment = getBlankMainActivityFragment();
        mainActivityFragment = getArgumentTakingFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        //fragmentTransaction.replace(R.id.container, mainActivityFragment);
        fragmentTransaction.replace(R.id.container, mainActivityFragment, TAG_FRAG);

        fragmentTransaction.commit();
    }

    private MainActivityFragment getBlankMainActivityFragment() {
        MainActivityFragment mainActivityFragment = new MainActivityFragment();
        return mainActivityFragment;
    }

    private MainActivityFragment getArgumentTakingFragment() {
        Bundle params = new Bundle();
        params.putString(Extras.TEXT, "I am passing it to fragment");
        mainActivityFragment = MainActivityFragment.getInstance(params);
        return mainActivityFragment;
    }

    private void setContentFromFragmentLayout() {
        setContentView(R.layout.activity_main_fragment_layout);
        accessFragmentFromLayout();
    }


    private MainActivityFragment accessFragmentFromLayout() {
        Fragment fragment = getFragmentManager().findFragmentById(R.id.fragment);
        if (fragment instanceof MainActivityFragment) {
            Log.i("fragmentFromLayout()", "Instance of MainActivityFragment");
            MainActivityFragment mainActivityFragment = (MainActivityFragment) fragment;
            return mainActivityFragment;
        }
        return null;
    }

    private MainActivityFragment accessFragmentFromDynamicAddition() {
        Fragment fragment = getFragmentManager().findFragmentByTag(TAG_FRAG);
        if (fragment instanceof MainActivityFragment) {
            Log.i("DynamicAddition()", "Instance of MainActivityFragment");
            MainActivityFragment mainActivityFragment = (MainActivityFragment) fragment;
            return mainActivityFragment;
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private MainActivityFragment getCurrentFragment() {
        MainActivityFragment currentFragment = accessFragmentFromLayout();
        if (currentFragment != null)
            return currentFragment;
        return accessFragmentFromDynamicAddition();

    }

    public void addText(View view) {
        mainActivityFragment = getCurrentFragment();
        if (mainActivityFragment != null) {
            mainActivityFragment.addText(someEditText.getText().toString());
        }

    }

}

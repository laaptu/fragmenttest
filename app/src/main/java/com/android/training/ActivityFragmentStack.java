package com.android.training;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.training.fragments.FragmentStack;

import java.util.Random;

/**
 * Created by laaptu on 4/7/15.
 */
public class ActivityFragmentStack extends Activity {

    private int fragmentCount = 0;

    //how backstack works
    //http://stackoverflow.com/questions/23199881/understanding-fragments-backstack


    private EditText txtFrag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_stack);
        txtFrag = (EditText) findViewById(R.id.txt_frag);
        if (savedInstanceState != null && savedInstanceState.containsKey(Extras.FRAGMENT_COUNT)) {
            fragmentCount = savedInstanceState.getInt(Extras.FRAGMENT_COUNT, fragmentCount);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt(Extras.FRAGMENT_COUNT, fragmentCount);

    }


    /**
     * http://stackoverflow.com/questions/23199881/understanding-fragments-backstack
     * Replace : Means there will only one Fragment Available</b>
     * and when you add by backstack, it will just monitor the fragment type</b>
     * and when popup or back is pressed, it will remove the existing fragment</b>
     * and replace with monitored fragment</b>
     * say A -> B -> C</b>
     * when back or popup</b>
     * C remove, B new instance created  and replaced
     */
    public void replace(View view) {
        add(false);
    }

    /***
     * Diff between add and replace </b>
     * http://stackoverflow.com/questions/18634207/difference-between-add-replace-and-addtobackstack</b>
     * http://stackoverflow.com/questions/20682248/difference-between-fragmenttransaction-add-and-fragmenttransaction-replace</b>
     * */

    /**
     * </b>
     * Fragment Transaction simply means how the fragment is added/replaces</b>
     * the same transaction will be remembered if added to backstack </b>
     * backstack entry simply means a bookkeeping done in finance
     */
    private void add(boolean add) {
        fragmentCount++;
        Bundle params = new Bundle();
        String tag = "Fragment #" + String.valueOf(fragmentCount);
        params.putString(Extras.TITLE, tag);
        params.putInt(Extras.COLOR_BG, getRandomColor());
        FragmentStack fragmentStack = FragmentStack.getInstance(params);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (add) {
            ft.add(R.id.container, fragmentStack, tag);
        } else
            ft.replace(R.id.container, fragmentStack);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(add ? tag : null);
        ft.commit();

    }

    public void add(View view) {
        add(true);
    }

    private int getRandomColor() {
        Random random = new Random();
        int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
        return color;
    }

    public void remove(View view) {
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fragmentCount--;
            fm.popBackStack();
        } else {
            Toast.makeText(this, "No fragment to remove", Toast.LENGTH_LONG).show();
        }

    }

    public void removeAll(View view) {
        //http://stackoverflow.com/questions/6186433/clear-back-stack-using-fragments
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fragmentCount = 0;
            fm.popBackStack(fm.getBackStackEntryAt(0).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
            Toast.makeText(this, "No fragment to remove", Toast.LENGTH_LONG).show();
        }

    }

    public void go(View view) {
        int fragNumber = Integer.valueOf(txtFrag.getText().toString());
        FragmentManager fm = getFragmentManager();
        if (fragNumber == fragmentCount) {
            Toast.makeText(this, "You are viewing it", Toast.LENGTH_SHORT).show();
            return;
        } else if (fragNumber < fragmentCount && fragNumber < fm.getBackStackEntryCount()) {
            fm.popBackStack(fm.getBackStackEntryAt(fragNumber).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentCount = fragNumber;
            return;
        }
        Toast.makeText(this, "No fragment exists", Toast.LENGTH_SHORT).show();

    }

    //http://stackoverflow.com/questions/20737550/bring-fragment-to-front-no-fragment-recreation
    //http://stackoverflow.com/questions/24161035/android-bring-fragment-in-front-from-backstack-without-poping-any-other
    public void show(View view) {
        int fragNumber = Integer.valueOf(txtFrag.getText().toString());
        FragmentManager fm = getFragmentManager();
        if (fragNumber == fragmentCount) {
            Toast.makeText(this, "You are viewing it", Toast.LENGTH_SHORT).show();
            return;
        } else if (fragNumber < fragmentCount && fragNumber < fm.getBackStackEntryCount()) {
            //http://stackoverflow.com/questions/9702216/get-the-latest-fragment-in-backstack
            fragNumber = fragNumber != 0 ? fragNumber - 1 : fragNumber;
            FragmentManager.BackStackEntry backStackEntry = fm.getBackStackEntryAt(fragNumber);
            String tag = backStackEntry.getName();
            System.out.println("FragmentTag " + tag);
            Fragment fragment = fm.findFragmentByTag(tag);
            if (fragment != null) {
                //fragmentCount = fragNumber;
//                Fragment topFragment = fm.findFragmentByTag(fm.getBackStackEntryAt(fm.getBackStackEntryCount() - 1).getName());
//                FragmentTransaction ft =fm.beginTransaction();
//                if (topFragment != null) {
//                    ft.hide(topFragment);
//                }
//                ft.attach(fragment);
//                ft.commit();
                FragmentTransaction ft = fm.beginTransaction();
                for (int i = fragNumber + 1; i < fm.getBackStackEntryCount(); i++) {
                    String tags = fm.getBackStackEntryAt(i).getName();
                    Fragment previousFrag = fm.findFragmentByTag(tags);
                    if (previousFrag != null) {
                        ft.hide(previousFrag);
                    }
                }
                ft.show(fragment);
                ft.commit();

            } else {
                Toast.makeText(this, "null fragment", Toast.LENGTH_SHORT).show();
            }
            //fm.popBackStack(fm.getBackStackEntryAt(fragNumber).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            return;
        }
        Toast.makeText(this, "No fragment exists", Toast.LENGTH_SHORT).show();

    }

    /**
     * Read this </b>
     * https://medium.com/@nuuneoi/probably-be-the-best-way-to-save-restore-android-fragments-state-so-far-c01d98711c2</b>
     *</b>
     * https://corner.squareup.com/2014/10/advocating-against-android-fragments.html
     * </b>
     * https://github.com/rathodchintan/Fragment-Back-Stack</b>
     * http://curioustechizen.blogspot.com/2014/02/nested-fragment-and-backstack-part-2.html</b>
     * */
}

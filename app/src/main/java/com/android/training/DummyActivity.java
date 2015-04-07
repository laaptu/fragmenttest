package com.android.training;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by laaptu on 4/7/15.
 */
public class DummyActivity extends Activity {

    int firstNum = 0, secondNum = 0;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dummy);
        textView = (TextView) findViewById(R.id.txt_info);
        Bundle params = getIntent().getExtras();
        if (params != null && params.containsKey(Extras.NUM_SECOND)) {
            firstNum = params.getInt(Extras.NUM_FIRST);
            secondNum = params.getInt(Extras.NUM_SECOND);
            String sum = "The sum of two nums:  " + firstNum + " + " + secondNum + " = " + String.valueOf(firstNum + secondNum);
            textView.setText(sum);
        } else {
            textView.setText("Nothing has been passed on first num and second num");
        }
    }

    public void finishActivity(View view) {
        Bundle params = new Bundle();
        params.putInt(Extras.NUM_SUM, firstNum + secondNum);
        Intent intent = new Intent();
        intent.putExtras(params);
        setResult(Activity.RESULT_OK, intent);
        this.finish();

    }
}

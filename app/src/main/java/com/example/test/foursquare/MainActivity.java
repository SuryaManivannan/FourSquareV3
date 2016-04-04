package com.example.test.foursquare;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private int[] numberStore = new int[4];
    private String[] operations = {"Add", "Subtract", "Multiply", "Divide"};
    private int target, amountPressed = 0, userChoice1, userChoice2, amountRight, amountWrong;
    private String method, localMethod="", previousMethod = "", slot1="_", slot2="_";
    private CountDownTimer timer;
    private Tracker mTracker;
    Vibrator v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        startLocal();

        //TIMER
        timer = new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                startLocal();
                start();
            }
        }.start();

        Button num1 = (Button)findViewById(R.id.num1);
        Button num2 = (Button)findViewById(R.id.num2);
        Button num3 = (Button)findViewById(R.id.num3);
        Button num4 = (Button)findViewById(R.id.num4);
        final TextView right = (TextView)findViewById(R.id.right);
        final TextView wrong = (TextView)findViewById(R.id.wrong);

        num1.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mTracker.send(new HitBuilders.EventBuilder()
                                .setCategory("Action")
                                .setAction("Share")
                                .build());
                        if (amountPressed == 0) {
                            userChoice1 = numberStore[0];
                            amountPressed++;
                            slot1 = numberStore[0]+"";
                            operationTextSetter();
                        } else if (amountPressed == 1) {
                            userChoice2 = numberStore[0];
                            slot2 = numberStore[0]+"";
                            operationTextSetter();
                            amountPressed = 0;
                            checker();
                        }
                        right.setText("" + amountRight);
                        wrong.setText("" + amountWrong);
                    }
                }
        );
        num2.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mTracker.send(new HitBuilders.EventBuilder()
                                .setCategory("Action")
                                .setAction("Share")
                                .build());
                        if (amountPressed == 0) {
                            userChoice1 = numberStore[1];
                            amountPressed++;
                            slot1 = numberStore[1]+"";
                            operationTextSetter();
                        } else if (amountPressed == 1) {
                            userChoice2 = numberStore[1];
                            slot2 = numberStore[1]+"";
                            operationTextSetter();
                            amountPressed = 0;
                            checker();
                        }
                        right.setText("" + amountRight);
                        wrong.setText("" + amountWrong);
                    }
                }
        );
        num3.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mTracker.send(new HitBuilders.EventBuilder()
                                .setCategory("Action")
                                .setAction("Share")
                                .build());
                        if (amountPressed == 0) {
                            userChoice1 = numberStore[2];
                            amountPressed++;
                            slot1 = numberStore[2]+"";
                            operationTextSetter();
                        } else if (amountPressed == 1) {
                            userChoice2 = numberStore[2];
                            slot2 = numberStore[2]+"";
                            operationTextSetter();
                            amountPressed = 0;
                            checker();
                        }
                        right.setText("" + amountRight);
                        wrong.setText("" + amountWrong);
                    }
                }
        );
        num4.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mTracker.send(new HitBuilders.EventBuilder()
                                .setCategory("Action")
                                .setAction("Share")
                                .build());
                        boolean checkerResult = false;
                        if (amountPressed == 0) {
                            userChoice1 = numberStore[3];
                            amountPressed++;
                            slot1 = numberStore[3]+"";
                            operationTextSetter();
                        } else if (amountPressed == 1) {
                            userChoice2 = numberStore[3];
                            slot2 = numberStore[3]+"";
                            operationTextSetter();
                            amountPressed = 0;
                            checker();
                        }
                        right.setText("" + amountRight);
                        wrong.setText("" + amountWrong);
                    }
                }
        );
    }

    public void startLocal(){
        slot1 = "_";
        slot2 = "_";
        localMethod="";
        amountPressed = 0;
        changeButtonNumbers();
        TextView num1Text = (TextView)findViewById(R.id.num1);
        TextView num2Text = (TextView)findViewById(R.id.num2);
        TextView num3Text = (TextView)findViewById(R.id.num3);
        TextView num4Text = (TextView)findViewById(R.id.num4);

        num1Text.setText(""+numberStore[0]);
        num2Text.setText("" + numberStore[1]);
        num3Text.setText("" + numberStore[2]);
        num4Text.setText("" + numberStore[3]);

        setMethodAndTarget();
    }

    public void setMethodAndTarget(){
        Random rand = new Random();
        while (true) {
            int operationChooser = rand.nextInt(4);
            method = operations[operationChooser];
            if(!method.equals(previousMethod)) {
                break;
            }
        }
        previousMethod = method;
        int a = rand.nextInt(4);
        int b = rand.nextInt(4);

        if (method.equals("Add")) {
            target = numberStore[a] + numberStore[b];
            localMethod = "+";
        } else if (method.equals("Subtract")){
            target = Math.abs(numberStore[a] - numberStore[b]);
            localMethod = "-";
        } else if (method.equals("Multiply")) {
            target = numberStore[a] * numberStore[b];
            localMethod = "*";
        } else if (method.equals("Divide")) {
            if (numberStore[a]!=0 || numberStore[b]!=0) {
                target = numberStore[a] / numberStore[b];
                localMethod = "/";
            }
            else {
                target = numberStore[a] * numberStore[b];
                method = "Multiply";
                localMethod = "*";
            }
        }
        operationTextSetter();
    }

    public void operationTextSetter(){
        TextView operationText = (TextView)findViewById(R.id.operation);
        operationText.setText(Html.fromHtml(slot1 + " <b>" + localMethod + "</b> " + slot2 + " = <b>" + target + "</b>"));
    }
    public boolean checker(){
        final TextView wrong = (TextView)findViewById(R.id.wrong);
        boolean whtReturn = true;
        int checker = 0;
        if (method.equals("Add")) {
            checker = userChoice1 + userChoice2;
        } else if (method.equals("Subtract")){
            checker = Math.abs(userChoice1 - userChoice2);
        } else if (method.equals("Multiply")) {
            checker = userChoice1 * userChoice2;
        } else if (method.equals("Divide")) {
            checker = userChoice1/userChoice2;
        }

        if (checker == target) {
            amountRight++;
            startLocal();
            timer.start();
        } else {
            v.vibrate(300);
            wrong.setTextColor(Color.WHITE);
            amountWrong++;
            amountPressed=0;
            slot1 = "_";
            slot2 = "_";
            operationTextSetter();
            whtReturn = false;
        }
        return whtReturn;
    }

    public void changeButtonNumbers() {
        Random rand = new Random();
        for (int i = 0; i < numberStore.length; i++) {
            int num = rand.nextInt(9);
            for (int k = 0; k <= i; k++) {
                if (i == 0 && k == i && num != 0) {
                    numberStore[i] = num;
                } else if (i != 0 && numberStore[0] != num && numberStore[k] != num && k == i && num != 0) {
                    numberStore[k] = num;
                } else if (i != 0 && numberStore[k] == num || num == 0) {
                    i = i - 1;
                    k = 0;
                }
            }
        }
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
}

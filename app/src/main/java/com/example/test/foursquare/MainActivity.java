package com.example.test.foursquare;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.test.foursquare.R;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private int[] numberStore = new int[4];
    private String[] operations = {"Addition", "Subtraction", "Multiplication", "Division"};
    private int target, amountPressed = 0, userChoice1, userChoice2, amountRight, amountWrong;
    private String method, previousMethod = "";
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        startLocal();

        //TIMER
        timer = new CountDownTimer(4000, 1000) {

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
                        if (amountPressed == 0) {
                            userChoice1 = numberStore[0];
                            amountPressed++;
                        } else if (amountPressed == 1) {
                            userChoice2 = numberStore[0];
                            checker();
                            amountPressed = 0;
                        }
                        right.setText(""+amountRight);
                        wrong.setText(""+amountWrong);
                    }
                }
        );
        num2.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (amountPressed == 0) {
                            userChoice1 = numberStore[1];
                            amountPressed++;
                        } else if (amountPressed == 1) {
                            userChoice2 = numberStore[1];
                            checker();
                            amountPressed = 0;
                        }
                        right.setText(""+amountRight);
                        wrong.setText(""+amountWrong);
                    }
                }
        );
        num3.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (amountPressed == 0) {
                            userChoice1 = numberStore[2];
                            amountPressed++;
                        } else if (amountPressed == 1) {
                            userChoice2 = numberStore[2];
                            checker();
                            amountPressed = 0;
                        }
                        right.setText(""+amountRight);
                        wrong.setText(""+amountWrong);
                    }
                }
        );
        num4.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean checkerResult = false;
                        if (amountPressed == 0) {
                            userChoice1 = numberStore[3];
                            amountPressed++;
                        } else if (amountPressed == 1) {
                            userChoice2 = numberStore[3];
                            checkerResult = checker();
                            amountPressed = 0;
                        }
                        right.setText(""+amountRight);
                        wrong.setText(""+amountWrong);
                    }
                }
        );
    }

    public void startLocal(){
        changeButtonNumbers();
        TextView num1Text = (TextView)findViewById(R.id.num1);
        TextView num2Text = (TextView)findViewById(R.id.num2);
        TextView num3Text = (TextView)findViewById(R.id.num3);
        TextView num4Text = (TextView)findViewById(R.id.num4);

        num1Text.setText(""+numberStore[0]);
        num2Text.setText(""+numberStore[1]);
        num3Text.setText(""+numberStore[2]);
        num4Text.setText(""+numberStore[3]);

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
        TextView operationText = (TextView)findViewById(R.id.operation);
        operationText.setText(method + ":");

        int a = rand.nextInt(4);
        int b = rand.nextInt(4);

        if (method.equals("Addition")) {
            target = numberStore[a] + numberStore[b];
        } else if (method.equals("Subtraction")){
            target = Math.abs(numberStore[a] - numberStore[b]);
        } else if (method.equals("Multiplication")) {
            target = numberStore[a] * numberStore[b];
        } else if (method.equals("Division")) {
            if (numberStore[a]!=0 || numberStore[b]!=0)
                target = numberStore[a] / numberStore[b];
            else {
                target = numberStore[a] * numberStore[b];
                operationText.setText("Multiplication");
            }
        }
        TextView targetText = (TextView)findViewById(R.id.target);
        targetText.setText("  Target = " + String.valueOf(target));
    }

    public boolean checker(){
        boolean whtReturn = true;
        int checker = 0;
        if (method.equals("Addition")) {
            checker = userChoice1 + userChoice2;
        } else if (method.equals("Subtraction")){
            checker = Math.abs(userChoice1 - userChoice2);
        } else if (method.equals("Multiplication")) {
            checker = userChoice1 * userChoice2;
        } else if (method.equals("Division")) {
            checker = userChoice1/userChoice2;
        }

        if (checker == target) {
            System.out.println(true);
            amountRight++;
            startLocal();
            timer.start();
        } else {
            System.out.println(false);
            amountWrong++;
            amountPressed=0;
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

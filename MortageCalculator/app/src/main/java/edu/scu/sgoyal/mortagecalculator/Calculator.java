package edu.scu.sgoyal.mortagecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Calculator extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                EditText principal = (EditText) findViewById(R.id.editText);

//                Toast.makeText(getApplicationContext(), prin, Toast.LENGTH_SHORT).show();
                EditText interest = (EditText) findViewById(R.id.editText2);

//                Toast.makeText(getApplicationContext(), inter, Toast.LENGTH_SHORT).show();

                RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);
                int id = rg.getCheckedRadioButtonId();

                    String prin = principal.getText().toString() ;
                System.out.println(prin);
                    if(prin.length() == 0)
                    {
                        Toast.makeText(getApplicationContext(), "Enter Principal amount", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        System.out.println("in else for principal");
                        double p = Double.parseDouble(prin);

                        String inter = interest.getText().toString();
                        if(inter.length() == 0)
                        {
                            Toast.makeText(getApplicationContext(), "Enter Interest amount", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            double i = Double.parseDouble(inter) ;
                            if(i > 10 || i < 0 )
                            {
                                Toast.makeText(getApplicationContext(), "Invalid Interest amount", Toast.LENGTH_SHORT).show();

                            }
                            else {

                                if (id != -1) {
                                    CheckBox cb = (CheckBox) findViewById(R.id.checkBox);
                                    double t = 0;
                                    if (cb.isChecked()) {
                                        t = (.001 * p);
//                        Toast.makeText(getApplicationContext(), t + "", Toast.LENGTH_SHORT).show();

                                    }

                                    String result = ((RadioButton) findViewById(id)).getText().toString();
                                    int months = 12 * Integer.parseInt(result);
//                    Toast.makeText(getApplicationContext(), months + "", Toast.LENGTH_SHORT).show();

                                    TextView tv = (TextView) findViewById(R.id.textView3);

                                    double out;
                                    if (i == 0) {
                                        out = (p / months) + t;
//                        Toast.makeText(getApplicationContext(), out + "", Toast.LENGTH_SHORT).show();

                                    } else {
                                        i = i / 1200;
                                        out = (p * (i / (1 - Math.pow(1 + i, -1 * months)))) + t;
//                        Toast.makeText(getApplicationContext(), out + "", Toast.LENGTH_SHORT).show();


                                    }

                                    tv.setText("$" + String.format("%.2f", out));


                                } else {
                                    Toast.makeText(getApplicationContext(), "Select Loan term", Toast.LENGTH_SHORT).show();

                                }
                            }

                        }

                    }

                    rg.clearCheck();
                }



        });
    }

    private boolean validation(EditText interest, EditText principal)
    {

        double i , p;
        if(principal.getText().toString().length() == 0)
        {
            return false;
        }
        else
        {
            String prin = principal.getText().toString() ;
            p = Double.parseDouble(prin);

        }
        if(interest.getText().toString().length() == 0)
        {
            return false;
        }
        else
        {
            String inter = interest.getText().toString();
            i = Double.parseDouble(inter) ;

        }
        if(p < 0)
        {
            Toast.makeText(getApplicationContext(), "Invalid Principal amount", Toast.LENGTH_SHORT);
            return false;
        }
        if(i < 0 || i > 10)
        {
            Toast.makeText(getApplicationContext(), "Invalid Interest amount", Toast.LENGTH_SHORT);
            return false;
        }

        return true;
    }
}

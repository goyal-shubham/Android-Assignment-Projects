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
                String prin = principal.getText().toString() ;
                double p = Double.parseDouble(prin);

                Toast.makeText(getApplicationContext(), prin, Toast.LENGTH_SHORT).show();
                EditText interest = (EditText) findViewById(R.id.editText2);
                String inter = interest.getText().toString();
                double i = Double.parseDouble(inter) ;
                Toast.makeText(getApplicationContext(), inter, Toast.LENGTH_SHORT).show();

                RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);
                int id = rg.getCheckedRadioButtonId();

                if(id != -1)
                {
                    CheckBox cb = (CheckBox) findViewById(R.id.checkBox);
                    double t = 0;
                    if(cb.isChecked())
                    {
                        t = (.001 * p);
                        Toast.makeText(getApplicationContext(), t + "", Toast.LENGTH_SHORT).show();

                    }

                    String result =  ((RadioButton) findViewById(id)).getText().toString();
                    int months = 12 * Integer.parseInt(result);
                    Toast.makeText(getApplicationContext(), months + "", Toast.LENGTH_SHORT).show();

                    TextView tv = (TextView) findViewById(R.id.textView3);

                    double out;
                    if(i == 0)
                    {

                        out = ( p / months) + t;
                        Toast.makeText(getApplicationContext(), out + "", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        i = i / 1200;
                        out = (p * ( i / (1 - Math.pow(1 + i, -1 * months)))) + t;
                        Toast.makeText(getApplicationContext(), out + "", Toast.LENGTH_SHORT).show();


                    }
                    tv.setText(out + "");


                }

            }
        });
    }
}

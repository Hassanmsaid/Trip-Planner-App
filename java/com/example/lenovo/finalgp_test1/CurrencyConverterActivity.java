package com.example.lenovo.finalgp_test1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Objects;

public class CurrencyConverterActivity extends AppCompatActivity {

    private EditText Curr ;
    private Double amount ;
    private String Amount ;
    private TextView Converted ;
    private Button convertBtn;

    final Double euro=1.17;
    final Double uk = 1.33;
    final Double egy=0.056;
    final Double yen=0.0091;
    final Double franc = 0.0089;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_currency_converter );

        convertBtn = findViewById(R.id.Convert_currency_converterBtn);
        Curr = findViewById( R.id.enter_amountET );

        Converted = findViewById( R.id.result_convert );

        final Spinner spinnerFrom = findViewById(R.id.currency_from);
        final Spinner spinnerTo = findViewById( R.id.currency_to );

        convertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Amount= Curr.getText().toString();
                String First = spinnerFrom.getSelectedItem().toString();
                if(!(Curr.getText().toString().equals("")))
                {

                    String selectedOption  = spinnerTo.getSelectedItem().toString();
                    Double convertedAmount = 0.0;
                    String total = "";
                    amount = Double.parseDouble(Amount);
                    if((First.equals("Pound(UK)")) &&(selectedOption.equals("Us Dollar")))
                    {
                        convertedAmount = (amount*uk)/1;
                    }
                    else if ((First.equals("Us Dollar")) &&(selectedOption.equals("Pound(UK)")))
                    {
                        convertedAmount = (amount*1)/uk;
                    }
                    else if ((Objects.equals(First, "Us Dollar")) &&(selectedOption.equals("Pound(EGY)")))
                    {
                        convertedAmount = (amount*1)/egy;
                    }
                    else if ((Objects.equals(First, "Pound(EGY)")) &&(selectedOption.equals("Us Dollar")))
                    {
                        convertedAmount = (amount*egy)/1;
                    }
                    else if ((Objects.equals(First, "Pound(EGY)")) &&(selectedOption.equals("Pound(UK)")))
                    {
                        convertedAmount = (amount*egy)/uk;
                    }
                    else if ((Objects.equals(First, "Pound(UK)")) &&(selectedOption.equals("Pound(EGY)")))
                    {
                        convertedAmount = (amount*uk)/egy;
                    }
                    else if ((Objects.equals(First, "Pound(UK)")) &&(selectedOption.equals("Euro")))
                    {
                        convertedAmount = (amount*uk)/euro;
                    }
                    else if ((Objects.equals(First, "Euro")) &&(selectedOption.equals( "Pound(UK)")))
                    {
                        convertedAmount = (amount*euro)/uk;
                    }
                    else if ((Objects.equals(First, "Euro")) &&(selectedOption.equals( "Yen")))
                    {
                        convertedAmount = (amount*euro)/yen;
                    }
                    else if ((Objects.equals(First, "Yen")) &&(selectedOption.equals( "Euro")))
                    {
                        convertedAmount = (amount*yen)/euro;
                    }
                    else if ((Objects.equals(First, "Yen")) &&(selectedOption.equals( "Franc")))
                    {
                        convertedAmount = (amount*yen)/franc;
                    }
                    else if ((Objects.equals(First, "Franc")) &&(selectedOption.equals( "Yen")))
                    {
                        convertedAmount = (amount*franc)/yen;
                    }
                    else if ((Objects.equals(First, "Pound(UK)")) &&(selectedOption.equals( "Yen")))
                    {
                        convertedAmount = (amount*uk)/yen;
                    }
                    else if ((Objects.equals(First, "Yen")) &&(selectedOption.equals( "Pound(UK)")))
                    {
                        convertedAmount = (amount*yen)/uk;
                    }
                    else if ((Objects.equals(First, "Yen")) &&(selectedOption.equals( "Pound(EGY)")))
                    {
                        convertedAmount = (amount*yen)/egy;
                    }
                    else if ((Objects.equals(First, "Pound(EGY)")) &&(selectedOption.equals( "Yen")))
                    {
                        convertedAmount = (amount*egy)/yen;
                    }
                    else if ((Objects.equals(First, "Franc")) &&(selectedOption.equals( "Pound(UK)")))
                    {
                        convertedAmount = (amount*franc)/uk;
                    }
                    else if ((Objects.equals(First, "Pound(UK)")) &&(selectedOption.equals( "Franc")))
                    {
                        convertedAmount = (amount*uk)/franc;
                    }
                    else if ((Objects.equals(First, "Pound(EGY)")) &&(selectedOption.equals("Euro")))
                    {
                        convertedAmount = (amount*egy)/euro;
                    }
                    else if ((Objects.equals(First, "Euro")) &&(selectedOption.equals("Pound(EGY")))
                    {
                        convertedAmount = (amount*euro)/egy;
                    }
                    else if ((Objects.equals(First, "Franc")) &&(selectedOption.equals("Pound(EGY")))
                    {
                        convertedAmount = (amount*franc)/egy;
                    }
                    else if ((Objects.equals(First, "Pound(EGY")) &&(selectedOption.equals("Franc")))
                    {
                        convertedAmount = (amount*egy)/franc;
                    }
                    else if ((Objects.equals(First, "Yen")) &&(selectedOption.equals("Us Dollar")))
                    {
                        convertedAmount = (amount*yen)/1;
                    }
                    else if ((Objects.equals(First, "Us Dollar")) &&(selectedOption.equals("Yen")))
                    {
                        convertedAmount = (amount*1)/yen;
                    }
                    else if ((Objects.equals(First, "Us Dollar")) &&(selectedOption.equals("Franc")))
                    {
                        convertedAmount = (amount*1)/franc;
                    }
                    else if ((Objects.equals(First, "Franc")) &&(selectedOption.equals("Us Dollar")))
                    {
                        convertedAmount = (amount*franc)/1;
                    }
                    else if ((Objects.equals(First, "Franc")) &&(selectedOption.equals("Euro")))
                    {
                        convertedAmount = (amount*franc)/euro;
                    }
                    else if ((Objects.equals(First, "Euro")) &&(selectedOption.equals("Franc")))
                    {
                        convertedAmount = (amount*euro)/franc;
                    }
                    else if ((First.equals("Euro")) &&(selectedOption.equals("Us Dollar")))
                    {
                        convertedAmount = (amount*euro)/1;
                    }
                    else if ((First.equals("Us Dollar")) &&(selectedOption.equals("Euro")))
                    {
                        convertedAmount = (amount*1)/euro;
                    }
                    total= String.format("%.2f", convertedAmount);
                    Converted.setText(String.valueOf(total));
                    //Toast.makeText(CurrencyConverterActivity.this, "total: " + total, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

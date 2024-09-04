package com.example.nic_to_dob;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity {

    private EditText enternic;
    private Button finddob;
    private TextView showdob;
    private TextView showgender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showdob = findViewById(R.id.Viewdob);
        showgender = findViewById(R.id.Viewgen);
        finddob = findViewById(R.id.convert);
        enternic = findViewById(R.id.nic);

        finddob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nic = enternic.getText().toString().trim();
                try {
                    String dob = convertNewNIC(nic);
                    String gender = extractGender(nic);
                    showdob.setText("Date of Birth: " + dob);
                    showgender.setText("Gender: " + gender);
                } catch (IllegalArgumentException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private String convertNewNIC(String newNIC) {
        if (newNIC.length() != 12) {
            throw new IllegalArgumentException("Invalid new NIC format");
        }

        int year = Integer.parseInt(newNIC.substring(0, 4));
        int dayOfYear = Integer.parseInt(newNIC.substring(4, 7));
        if (dayOfYear > 500) {
            dayOfYear -= 500;
        }

        LocalDate dob = LocalDate.ofYearDay(year, dayOfYear);
        return dob.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    private String extractGender(String newNIC) {
        int dayOfYear = Integer.parseInt(newNIC.substring(4, 7));
        return (dayOfYear > 500) ? "Female" : "Male";
    }
}

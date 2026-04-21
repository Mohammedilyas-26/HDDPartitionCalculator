package com.example.hddpartitioncalculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etHDDCapacity, etUsedSpace, etPartitionPercent;
    private TextView tvResults;
    private Button btnCalculate, btnConvert, btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        etHDDCapacity = findViewById(R.id.etHDDCapacity);
        etUsedSpace = findViewById(R.id.etUsedSpace);
        etPartitionPercent = findViewById(R.id.etPartitionPercent);
        tvResults = findViewById(R.id.tvResults);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnConvert = findViewById(R.id.btnConvert);
        btnClear = findViewById(R.id.btnClear);

        // Set click listeners
        btnCalculate.setOnClickListener(v -> calculatePartition());
        btnConvert.setOnClickListener(v -> convertUnits());
        btnClear.setOnClickListener(v -> clearFields());
    }

    private void calculatePartition() {
        try {
            String capacity = etHDDCapacity.getText().toString().trim();
            String used = etUsedSpace.getText().toString().trim();

            if (capacity.isEmpty() || used.isEmpty()) {
                tvResults.setText("Please enter all values");
                return;
            }

            double hddCapacity = Double.parseDouble(capacity);
            double usedSpace = Double.parseDouble(used);

            if (usedSpace > hddCapacity) {
                tvResults.setText("Used space cannot be greater than total capacity!");
                return;
            }

            double freeSpace = hddCapacity - usedSpace;
            double percentUsed = (usedSpace / hddCapacity) * 100;
            double percentFree = 100 - percentUsed;

            String result = String.format(
                "Total Capacity: %.2f GB\n" +
                "Used Space: %.2f GB\n" +
                "Free Space: %.2f GB\n" +
                "Used: %.2f%%\n" +
                "Free: %.2f%%",
                hddCapacity, usedSpace, freeSpace, percentUsed, percentFree
            );

            tvResults.setText(result);
        } catch (NumberFormatException e) {
            tvResults.setText("Invalid input! Please enter valid numbers.");
        }
    }

    private void convertUnits() {
        try {
            String capacity = etHDDCapacity.getText().toString().trim();

            if (capacity.isEmpty()) {
                tvResults.setText("Please enter HDD capacity in GB");
                return;
            }

            double gb = Double.parseDouble(capacity);
            double mb = gb * 1024;
            double tb = gb / 1024;
            double bytes = gb * 1024 * 1024 * 1024;

            String result = String.format(
                "Unit Conversion (%.2f GB):\n" +
                "Bytes: %.0f\n" +
                "MB: %.2f\n" +
                "GB: %.2f\n" +
                "TB: %.4f",
                gb, bytes, mb, gb, tb  // gb is correct here - used for header AND GB line
            );

            tvResults.setText(result);
        } catch (NumberFormatException e) {
            tvResults.setText("Invalid input! Please enter a valid number.");
        }
    }

    private void clearFields() {
        etHDDCapacity.setText("");
        etUsedSpace.setText("");
        etPartitionPercent.setText("");
        tvResults.setText("Results will appear here");
    }
}

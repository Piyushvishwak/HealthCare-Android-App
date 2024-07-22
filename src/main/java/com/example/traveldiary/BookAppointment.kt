package com.example.traveldiary

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar


class BookAppointment : AppCompatActivity() {
    private lateinit var btnAppDate: Button
    private lateinit var btnAppTime: Button
    private lateinit var btnBook: Button
    private lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_appointment)


        val ed0: TextView = findViewById(R.id.editTextAppTitle)
        val ed1: EditText = findViewById(R.id.editTextAppFullName)
        val ed2:EditText = findViewById(R.id.editTextAppAddress)
        val ed3:EditText = findViewById(R.id.editTextAppContNumber)
        val ed4 :EditText = findViewById(R.id.editTextAppFee)


        val doctorName = intent.getStringExtra("doctorName")
        val title = intent.getStringExtra("title")
        if (doctorName != null) {
            ed0.text = title
            ed1.setText(" " + doctorName)
            ed2.setText(" Hosp Address: Varanasi")
            ed3.setText(" Mobile No: 8188020734")
            ed4.setText(" Cons Fees: Rs.300/-")
        }

        btnAppDate = findViewById(R.id.btnAppDate)
        btnAppTime = findViewById(R.id.btnAppTime)
        btnBook = findViewById(R.id.btnBookApp)
        btnBack = findViewById(R.id.buttonBack)

        // Set up the DatePickerDialog
        btnAppDate.setOnClickListener {
            showDatePickerDialog()
        }

        btnAppTime.setOnClickListener {
            showTimePickerDialog()
        }

        btnBack.setOnClickListener {
            startActivity(Intent(this, DoctorDetailsActivity::class.java))
        }
        btnBook.setOnClickListener {
            Toast.makeText(this@BookAppointment, "Appointment Booked", Toast.LENGTH_SHORT).show()
        }

    }
    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "${selectedDay}/${selectedMonth + 1}/${selectedYear}"
                btnAppDate.text = selectedDate
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            { _, selectedHour, selectedMinute ->
                val selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                btnAppTime.text = selectedTime
            },
            hour,
            minute,
            true
        )
        timePickerDialog.show()
    }
}

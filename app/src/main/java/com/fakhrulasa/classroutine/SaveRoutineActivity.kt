package com.fakhrulasa.classroutine

import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.fakhrulasa.classroutine.databinding.ActivitySaveRoutineBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog

class SaveRoutineActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener,
    DatePickerDialog.OnDateSetListener {

    val db = Firebase.firestore

    private lateinit var binding: ActivitySaveRoutineBinding

    @RequiresApi(Build.VERSION_CODES.N)
    var now: Calendar = Calendar.getInstance()

    @RequiresApi(Build.VERSION_CODES.N)
    var dpd: DatePickerDialog = DatePickerDialog.newInstance(
        this,
        now[Calendar.YEAR],  // Initial year selection
        now[Calendar.MONTH],  // Initial month selection
        now[Calendar.DAY_OF_MONTH] // Inital day selection
    )

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaveRoutineBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var classObject = HashMap<String, String>()
        binding.tvClassTime.setOnClickListener {
            dpd.show(supportFragmentManager, "Select your class time")
        }

        binding.button2.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            classObject["Class Name"] = binding.etClassName.text.toString()
            classObject["Class Time"] = binding.tvClassTime.text.toString()
            classObject["Teacher Name"] = binding.etTeacherName.text.toString()

            db.collection("Class")
                .add(classObject)
                .addOnSuccessListener { documentReference ->
                    binding.progressBar.visibility = View.GONE

                    showToastMessage(this, "Saved");
                }
                .addOnFailureListener { e ->
                    binding.progressBar.visibility = View.GONE

                    showToastMessage(this, e.message.toString())
                }
        }
    }

    override fun onTimeSet(view: TimePickerDialog?, hourOfDay: Int, minute: Int, second: Int) {
        binding.tvClassTime.text = hourOfDay.toString() + minute.toString()
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        binding.tvClassTime.text =
            year.toString() + "/" + monthOfYear.toString() + "/" + dayOfMonth.toString()
    }
}
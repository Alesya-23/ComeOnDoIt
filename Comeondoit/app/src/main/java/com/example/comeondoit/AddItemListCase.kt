package com.example.comeondoit

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*


class AddItemListCase : AppCompatActivity() {
    private var isEdit: Boolean = false
    private lateinit var mAuth: FirebaseAuth
    private lateinit var myRef: DatabaseReference

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_item_list_case)
        val title: TextView = findViewById(R.id.in_title)
        val description: TextView = findViewById(R.id.in_description)
        val calDate = Calendar.getInstance()
        val textDate: TextView = findViewById(R.id.in_date)
        var yearNow = calDate.get(Calendar.YEAR)
        val month = calDate.get(Calendar.MONTH)
        val day = calDate.get(Calendar.DAY_OF_MONTH)
        textDate.setOnClickListener {
            val dpd = DatePickerDialog(
                    this,
                    DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                        // Display Selected date in TextView
                        textDate.text =
                                "" + year + "-" + (monthOfYear + 1) / 10 + (monthOfYear + 1) + "-" + dayOfMonth
                        yearNow = year
                    },
                    yearNow,
                    month,
                    day
            )
            dpd.show()
        }
        val textClockStart: TextView = findViewById(R.id.in_time_start)
        textClockStart.setOnClickListener() {
            val timeSetListenerEn =
                    TimePickerDialog(this, OnTimeSetListener { timePicker, hour, minute ->
                        calDate.set(Calendar.HOUR_OF_DAY, hour)
                        calDate.set(Calendar.MINUTE, minute)
                        textClockStart.text = SimpleDateFormat("HH:mm").format(calDate.time)
                    }, Calendar.HOUR_OF_DAY, Calendar.MINUTE, true).show()
        }

        val textClockEnd: TextView = findViewById(R.id.in_time_end)
        textClockEnd.setOnClickListener() {
            val timeSetListenerEn =
                    TimePickerDialog(this, OnTimeSetListener { timePicker, hour, minute ->
                        calDate.set(Calendar.HOUR_OF_DAY, hour)
                        calDate.set(Calendar.MINUTE, minute)
                        textClockEnd.text = SimpleDateFormat("HH:mm").format(calDate.time)
                    }, Calendar.HOUR_OF_DAY, Calendar.MINUTE, true).show()
        }

        val buttonAddCase: Button = findViewById(R.id.add_new_case_full)
        buttonAddCase.setOnClickListener() {
            if (title.text.toString() == "") {
                val toast: Toast = Toast.makeText(
                        applicationContext,
                        "Вы не ввели название заметки",
                        Toast.LENGTH_SHORT
                )
                return@setOnClickListener toast.show()
            }
            if (description.text.toString() == "") {
                val toast: Toast = Toast.makeText(
                        applicationContext,
                        "Вы не ввели описание дела",
                        Toast.LENGTH_SHORT
                )
                return@setOnClickListener toast.show()
            }
            if (textDate.text.toString() == "") {
                val toast: Toast = Toast.makeText(
                        applicationContext,
                        "Вы не выбрали дату",
                        Toast.LENGTH_SHORT
                )
                return@setOnClickListener toast.show()
            }
            if (textClockStart.text.toString() == "") {
                val toast: Toast = Toast.makeText(
                        applicationContext,
                        "Вы не выбрали время начала дела",
                        Toast.LENGTH_SHORT
                )
                return@setOnClickListener toast.show()
            }
            if (textClockEnd.text.toString() == "") {
                val toast: Toast = Toast.makeText(
                        applicationContext,
                        "Вы не выбрали время конца дела",
                        Toast.LENGTH_SHORT
                )
                return@setOnClickListener toast.show()
            }
            if (textClockStart.text.toString() > textClockEnd.text.toString() ||
                    textClockStart.text.toString() == textClockEnd.text.toString()
            ) {
                val toast: Toast = Toast.makeText(
                        applicationContext,
                        "Время окончания не может быть меньше-равно времени начала",
                        Toast.LENGTH_SHORT
                )
                return@setOnClickListener toast.show()
            } else {
                //add case into database
                val map: HashMap<String, Any> =
                        hashMapOf(
                                "title" to title.text.toString(),
                                "description" to description.text.toString(),
                                "date" to textDate.text.toString(),
                                "timeStart" to textClockStart.text.toString(),
                                "timeEnd" to textClockEnd.text.toString()
                        )

                FirebaseAuth.getInstance().signOut()
                //проверка есть ли такой ребёнок в бд

                val rootRef = FirebaseDatabase.getInstance().reference.child(yearNow.toString())
                        .child(textDate.text.toString())
                rootRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.child(textDate.text.toString()).exists()) {
                            isEdit = true
                        }
                    }
                })
                if (!isEdit) {
                    FirebaseDatabase.getInstance().reference.child(yearNow.toString())
                            .child(textDate.text.toString())
                            .child(textClockStart.text.toString()).setValue(map)
                    val toast: Toast = Toast.makeText(
                            applicationContext,
                            "Задача " + title.text + " добавлена",
                            Toast.LENGTH_LONG
                    )
                    return@setOnClickListener toast.show()
                } else {
                    val toast: Toast = Toast.makeText(
                            applicationContext,
                            "Задача " + title.text + "не добавлена, на это время есть дело",
                            Toast.LENGTH_SHORT
                    )
                    return@setOnClickListener toast.show()
                }
            }
        }
    }
}
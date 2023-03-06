package com.example.gradecalculator

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.view.View




class MainActivity : AppCompatActivity() {

    private lateinit var homeworkEditText: EditText
    private lateinit var addHomeworkButton: Button
    private lateinit var showTotalButton: Button
    private lateinit var resetButton: Button
    private lateinit var groupPresentationEditText: EditText
    private lateinit var participationEditText: EditText
    private lateinit var midterm1EditText: EditText
    private lateinit var midterm2EditText: EditText
    private lateinit var finalProjectEditText: EditText
    private lateinit var gradeList: ArrayList<Double>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        homeworkEditText = findViewById(R.id.homework_edit_text)
        addHomeworkButton = findViewById(R.id.add_homework_button)
        showTotalButton = findViewById(R.id.show_total_button)
        resetButton = findViewById(R.id.reset_button)
        groupPresentationEditText = findViewById(R.id.group_presentation_edit_text)
        participationEditText = findViewById(R.id.participation_edit_text)
        midterm1EditText = findViewById(R.id.midterm1_edit_text)
        midterm2EditText = findViewById(R.id.midterm2_edit_text)
        finalProjectEditText = findViewById(R.id.final_project_edit_text)
        gradeList = ArrayList()
        val totalGradeTextView = findViewById<TextView>(R.id.total_grade_text_view)

        // get the shared preferences object
        val sharedPref = getPreferences(Context.MODE_PRIVATE)

        // get the previously saved total grade or default to 0.0
        val savedTotalGrade = sharedPref.getFloat("TOTAL_GRADE", 0.0f)

        // set the total grade text view to the saved value
        totalGradeTextView.text = "Total Grade: ${savedTotalGrade.toString()}"


        addHomeworkButton.setOnClickListener {
            val homeworkGrade = homeworkEditText.text.toString().toDoubleOrNull()

            if (homeworkGrade != null && homeworkGrade in 0.0..100.0 && gradeList.size < 5) {
                gradeList.add(homeworkGrade)
                homeworkEditText.text.clear()
                Toast.makeText(this, "Homework grade added", Toast.LENGTH_SHORT).show()
            } else if (homeworkGrade == null) {
                Toast.makeText(this, "Please enter a valid grade", Toast.LENGTH_SHORT).show()
            } else if (homeworkGrade !in 0.0..100.0) {
                Toast.makeText(this, "Grade should be between 0 and 100", Toast.LENGTH_SHORT).show()
            } else if (gradeList.size >= 5) {
                Toast.makeText(
                    this,
                    "Maximum number of homework grades reached",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        showTotalButton.setOnClickListener {
            val groupPresentationGrade = groupPresentationEditText.text.toString().toDoubleOrNull()
            val participationGrade = participationEditText.text.toString().toDoubleOrNull()
            val midterm1Grade = midterm1EditText.text.toString().toDoubleOrNull()
            val midterm2Grade = midterm2EditText.text.toString().toDoubleOrNull()
            val finalProjectGrade = finalProjectEditText.text.toString().toDoubleOrNull()


            if (groupPresentationGrade != null && groupPresentationGrade in 0.0..100.0 && participationGrade != null && participationGrade in 0.0..100.0 &&
                midterm1Grade != null && midterm1Grade in 0.0..100.0 &&
                midterm2Grade != null && midterm2Grade in 0.0..100.0 &&
                finalProjectGrade != null && finalProjectGrade in 0.0..100.0 && gradeList.isNotEmpty()
            ) {

                val totalHomeworkGrade = gradeList.average()


                val totalGrade =
                    0.1 * groupPresentationGrade + 0.1 * participationGrade + 0.1 * midterm1Grade + 0.2 * midterm2Grade + 0.3 * finalProjectGrade + 0.2 * totalHomeworkGrade
                val formattedTotalGrade = String.format("%.2f", totalGrade.toFloat())
                totalGradeTextView.text = "Total Grade: ${totalGrade.toString()}"

                sharedPref.edit().putFloat("TOTAL_GRADE", totalGrade.toFloat()).apply()

                // set the total grade text view to the calculated value
                totalGradeTextView.text = "Total Grade: ${String.format("%.2f", totalGrade)}"

                //Toast.makeText(this, "Total grade is $formattedTotalGrade", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter valid grades for all fields", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        resetButton.setOnClickListener {
            gradeList.clear()

            Toast.makeText(this, "Homework grades reset", Toast.LENGTH_SHORT).show()
        }
    }
}
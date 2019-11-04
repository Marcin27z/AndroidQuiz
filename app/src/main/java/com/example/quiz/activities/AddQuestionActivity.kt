package com.example.quiz.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import com.example.quiz.ObjectBox
import com.example.quiz.R
import com.example.quiz.entity.Question
import com.example.quiz.entity.Quiz
import com.example.quiz.entity.Quiz_
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import kotlinx.android.synthetic.main.activity_add_question.*

class AddQuestionActivity : AppCompatActivity() {

    private lateinit var answersEditText: List<EditText>
    private lateinit var radioButtons: List<RadioButton>
    private var selectedQuestion = -1
    private val quizBox: Box<Quiz> = ObjectBox.get()!!.boxFor()
    private val questionBox: Box<Question> = ObjectBox.get()!!.boxFor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_question)
        answersEditText = listOf(answer1EditText, answer2EditText, answer3EditText, answer4EditText)
        radioButtons = listOf(answer1RadioButton, answer2RadioButton, answer3RadioButton, answer4RadioButton)
        radioGroup.setOnCheckedChangeListener { _, index: Int ->
            radioButtons.forEachIndexed { i, button -> if (index == button.id) selectedQuestion = i }
        }
        lateinit var question: Question
        addQuestionButton.setOnClickListener {
            val validation = validateInput()
            if ("" == validation) {
                question = Question(
                    questionEditText.text.toString(),
                    answersEditText.map { it.text.toString() } as ArrayList<String>,
                    selectedQuestion
                )
                val quiz = quizBox.query().equal(Quiz_.id, (intent.extras["quiz"] as Int).toLong() + 1).build().findFirst()
                quiz!!.questions.add(question)
                quizBox.put(quiz)
                finish()
            } else {
                Toast.makeText(this, validation, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateInput(): String {
        return when {
            questionEditText.text.isEmpty() -> "Question cannot be emppty"
            answersEditText.any { it.text.isEmpty() } -> "Answer cannot be empty"
            selectedQuestion == -1 -> "Please select correct answer"
            else -> ""
        }
    }
}

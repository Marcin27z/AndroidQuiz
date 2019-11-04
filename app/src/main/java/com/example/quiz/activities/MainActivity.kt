package com.example.quiz.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import com.example.quiz.*
import com.example.quiz.data.Question
import com.example.quiz.data.QuestionRepositoryImpl
import com.example.quiz.entity.Quiz
import com.example.quiz.fragments.AnswersFragment
import com.example.quiz.fragments.QuestionFragment
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity(), AnswersFragment.OnFragmentInteractionListener,
    QuestionFragment.OnFragmentInteractionListener, PopUp.OnTouchListener {

    private lateinit var fragmentQuestion: QuestionFragment
    private lateinit var fragmentAnswers: AnswersFragment
    private lateinit var questions: ArrayList<Question>
    private lateinit var popUp: PopUp

    val app = App

    private var currentQuestion = 0

    override fun onAnswerClicked(index: Int) {
        popUp = PopUp(findViewById(android.R.id.content), this)
        if (questions[currentQuestion].correctAnswer != index) {
            fragmentAnswers.setButtonIncorrect(index)
            popUp.show(this, resources.getString(R.string.incorrect_answer))
        } else {
            fragmentAnswers.setButtonCorrect(index)
            popUp.show(this, resources.getString(R.string.correct_answer))
        }
    }

    override fun tap() {
        popUp.dismiss()
        fragmentAnswers.clearColors()
        setQuestion(++currentQuestion)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.putInt("currentQuestion", currentQuestion)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val quizzesBox: Box<Quiz> = ObjectBox.get()!!.boxFor()
        currentQuestion = savedInstanceState?.getInt("currentQuestion") ?: 0
        questions = QuestionRepositoryImpl(this).getQuestionsFromDB(0)
        fragmentAnswers = AnswersFragment.newInstance(questions[currentQuestion].answers)
        fragmentQuestion = QuestionFragment.newInstance(questions[currentQuestion].question)
        supportFragmentManager.beginTransaction()
            .replace(com.example.quiz.R.id.fragment_question, fragmentQuestion)
            .replace(com.example.quiz.R.id.fragment_answers, fragmentAnswers)
            .commit()
        setContentView(com.example.quiz.R.layout.activity_main)
        nextQuestionButton.setOnClickListener { setNextQuestion() }
        previousQuestionButton.setOnClickListener { setPreviousQuestion() }
        previousQuestionButton.isEnabled = false
        currentQuestionTextView.text = "${currentQuestion + 1} / ${questions.size}"
        when (currentQuestion) {
            questions.size - 2 -> nextQuestionButton.isEnabled = false
            1 -> previousQuestionButton.isEnabled = false
        }

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
//            setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)

        val expandableListAdapter = MyExpandableListAdapter(this)
        expandable_list_view.setAdapter(expandableListAdapter)
        toggle.syncState()
        
        expandable_list_view.setOnChildClickListener { _, v, groupPosition, childPosition, id ->
            setQuestion(childPosition)
            true
        }


        val quizBox: Box<Quiz> = ObjectBox.get()!!.boxFor()
        val questionBox: Box<com.example.quiz.entity.Question> = ObjectBox.get()!!.boxFor()
//
//        val quiz = Quiz("quiz")
//        val question = com.example.quiz.entity.Question("question", "answer1",
//            "answer22", "answer33", "answer44", 3)
//        quiz.questions.add(question)
//        quizBox.put(quiz)
//
//        val quizzes = quizBox.query().build().find()
//        val questionsss = quizzes[1].questions[0]
        val questionss = questionBox.query().build().find()
        val q = questionss[1].quiz.target
        val q2 = questionss[13].quiz.target
        Log.d("nic", "nic")

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawer_layout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setNextQuestion() {
        if (currentQuestion + 2 == questions.size) {
            nextQuestionButton.isEnabled = false
        } else {
            previousQuestionButton.isEnabled = true
        }
        setQuestion(++currentQuestion)
    }

    private fun setPreviousQuestion() {
        if (currentQuestion - 1 == 0) {
            previousQuestionButton.isEnabled = false
        } else {
            nextQuestionButton.isEnabled = true
        }
        setQuestion(--currentQuestion)
    }

    @SuppressLint("SetTextI18n")
    private fun setQuestion(index: Int) {
        val question = questions[index]
        fragmentAnswers.setAnswersOnButtons(question.answers)
        fragmentQuestion.setQuestion(question.question)
        currentQuestion = index
        currentQuestionTextView.text = "${currentQuestion + 1} / ${questions.size}"
    }
}

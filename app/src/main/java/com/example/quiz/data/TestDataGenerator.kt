package com.example.quiz.data

import android.content.Context
import kotlin.random.Random

class TestDataGenerator(val context: Context) {

    private val questionRepository = QuestionRepositoryImpl(context)
    private val quizRepository = QuizRepositoryImpl(context)

    fun clearDatabase() {
        val db = SQLiteDBHelper(context)
        db.dropQuestions(db.writableDatabase)
        db.dropQuizzes(db.writableDatabase)
        db.createQuizzes(db.writableDatabase)
        db.createQuestions(db.writableDatabase)
    }

    fun addQuiz() {
        val quizzes = quizRepository.getNumberOfQuizzes()
        quizRepository.addQuizToDB(Quiz(0, "quiz$quizzes", null))
    }

    fun addQuestion(quiz: Int) {
        val questions = questionRepository.getNumberOfQuestions()
        questionRepository.saveQuestionToDB(Question("question$questions",
            arrayListOf("answer1", "answer2", "answer3", "answer4"),
            quiz, Random.nextInt(0, 3)))
    }
}
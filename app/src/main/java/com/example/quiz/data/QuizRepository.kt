package com.example.quiz.data

/**
 * Created by Marcin on 26.02.2019.
 */
interface QuizRepository {

    fun addQuizToDB(quiz: Quiz)

    fun getQuizFromDB(quizId: Int): Quiz

    fun getNumberOfQuizzes(): Int
}
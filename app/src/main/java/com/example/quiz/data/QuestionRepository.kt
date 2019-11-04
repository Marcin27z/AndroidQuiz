package com.example.quiz.data

/**
 * Created by Marcin on 26.02.2019.
 */
interface QuestionRepository {

    fun saveQuestionToDB(question: Question)

    fun getQuestionsFromDB(quizId: Int): List<Question>

    fun getNumberOfQuestions(): Int
}
package com.example.quiz.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

@Entity
class Question() {

    constructor(question: String, answer1: String, answer2: String, answer3: String, answer4: String, correctAnswer: Int): this() {
        this.question = question
        this.answer1 = answer1
        this.answer2 = answer2
        this.answer3 = answer3
        this.answer4 = answer4
        this.correctAnswer = correctAnswer
    }

    constructor(question: String, answers: ArrayList<String>, correctAnswer: Int):
            this(question, answers[0], answers[1], answers[2], answers[3], correctAnswer)

    @Id
    var id: Long = 0

    lateinit var question: String

    lateinit var answer1: String
    lateinit var answer2: String
    lateinit var answer3: String
    lateinit var answer4: String

    var correctAnswer: Int = 0

    lateinit var quiz: ToOne<Quiz>

    fun getAnswers(): List<String> = arrayListOf(answer1, answer2, answer3, answer4)

    fun getQuizId(): Long = quiz.target.id
}

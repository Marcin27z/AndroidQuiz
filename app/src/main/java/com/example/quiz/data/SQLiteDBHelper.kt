package com.example.quiz.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


/**
 * Created by Marcin on 26.02.2019.
 */
class SQLiteDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        createQuizzes(sqLiteDatabase)
        createQuestions(sqLiteDatabase)
    }


    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
        dropQuestions(sqLiteDatabase)
        dropQuizzes(sqLiteDatabase)
        onCreate(sqLiteDatabase)
    }

    fun createQuizzes(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL(
            "CREATE TABLE $QUIZZES_TABLE_NAME (" +
                    "$QUIZES_COLUMN_ID INTEGER PRIMARY KEY," +
                    "$QUIZES_COLUMN_NAME TEXT," +
                    "$QUIZES_COLUMN_DATE DATE" +
                    ")"
        )
    }

    fun createQuestions(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL(
            "CREATE TABLE  $QUESTIONS_TABLE_NAME (" +
                    "$QUESTIONS_COLUMN_ID  INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$QUESTIONS_COLUMN_QUIZ_ID INTEGER REFERENCES $QUIZZES_TABLE_NAME, " +
                    "$QUESTIONS_COLUMN_QUESTION TEXT, " +
                    "$QUESTIONS_COLUMN_CORRECT_ANSWER INTEGER, " +
                    "${QUESTIONS_COLUMN_ANSWERS[0]} TEXT, " +
                    "${QUESTIONS_COLUMN_ANSWERS[1]} TEXT, " +
                    "${QUESTIONS_COLUMN_ANSWERS[2]} TEXT, " +
                    "${QUESTIONS_COLUMN_ANSWERS[3]} TEXT " +
                    // "FOREGIN KEY($QUESTIONS_COLUMN_QUIZ_ID) REFERENCES $QUIZZES_TABLE_NAME($QUIZES_COLUMN_ID)" +
                    ")"
        )
    }

    fun dropQuizzes(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS $QUIZZES_TABLE_NAME")
    }

    fun dropQuestions(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS $QUESTIONS_TABLE_NAME")
    }

    companion object {

        private const val DATABASE_VERSION = 3
        const val DATABASE_NAME = "quiz"
        const val QUESTIONS_TABLE_NAME = "questions"
        const val QUESTIONS_COLUMN_ID = "id"
        const val QUESTIONS_COLUMN_QUIZ_ID = "quiz_id"
        const val QUESTIONS_COLUMN_QUESTION = "question"
        const val QUESTIONS_COLUMN_CORRECT_ANSWER = "correct_answer"
        val QUESTIONS_COLUMN_ANSWERS = listOf("answer1", "answer2", "answer3", "answer4")

        const val QUIZZES_TABLE_NAME = "quizes"
        const val QUIZES_COLUMN_ID = "id"
        const val QUIZES_COLUMN_NAME = "name"
        const val QUIZES_COLUMN_DATE = "date"

    }
}
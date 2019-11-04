package com.example.quiz.data

import android.content.ContentValues
import android.content.Context

/**
 * Created by Marcin on 26.02.2019.
 */
class QuizRepositoryImpl(private val context: Context) : QuizRepository {

    override fun getNumberOfQuizzes(): Int {
        val database = SQLiteDBHelper(context).readableDatabase

        val projection = arrayOf(
            SQLiteDBHelper.QUIZES_COLUMN_ID
        )

        val cursor = database.query(
            SQLiteDBHelper.QUIZZES_TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )
        val count = cursor.count
        cursor.close()
        return count
    }

    override fun getQuizFromDB(quizId: Int): Quiz {
        val database = SQLiteDBHelper(context).readableDatabase

        val projection = arrayOf(
            SQLiteDBHelper.QUIZES_COLUMN_ID,
            SQLiteDBHelper.QUIZES_COLUMN_NAME,
            SQLiteDBHelper.QUIZES_COLUMN_DATE
        )

        val selection = "${SQLiteDBHelper.QUIZES_COLUMN_ID} = ?"

        val selectionArgs = arrayOf("$quizId")

        val cursor = database.query(
            SQLiteDBHelper.QUESTIONS_TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        cursor.moveToFirst()
        val quiz = Quiz(cursor.getInt(0), cursor.getString(1), null)
        cursor.close()
        return quiz
    }

    override fun addQuizToDB(quiz: Quiz) {
        val database = SQLiteDBHelper(context).writableDatabase
        val values = ContentValues()
        values.put(SQLiteDBHelper.QUIZES_COLUMN_NAME, quiz.name)
        val newRowId = database.insert(SQLiteDBHelper.QUIZZES_TABLE_NAME, null, values)

    }



}
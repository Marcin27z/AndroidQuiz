package com.example.quiz.data

import android.content.ContentValues
import android.content.Context
import android.widget.Toast


/**
 * Created by Marcin on 26.02.2019.
 */
class QuestionRepositoryImpl(private val context: Context) : QuestionRepository {

    override fun getNumberOfQuestions(): Int {
        val database = SQLiteDBHelper(context).readableDatabase

        val projection = arrayOf(
            SQLiteDBHelper.QUESTIONS_COLUMN_ID
        )

        val cursor = database.query(
            SQLiteDBHelper.QUESTIONS_TABLE_NAME,
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

    override fun getQuestionsFromDB(quizId: Int): ArrayList<Question> {

        val database = SQLiteDBHelper(context).readableDatabase

        val projection = arrayOf(
            SQLiteDBHelper.QUESTIONS_COLUMN_QUIZ_ID,
            SQLiteDBHelper.QUESTIONS_COLUMN_ID,
            SQLiteDBHelper.QUESTIONS_COLUMN_QUESTION,
            SQLiteDBHelper.QUESTIONS_COLUMN_CORRECT_ANSWER,
            SQLiteDBHelper.QUESTIONS_COLUMN_ANSWERS[0],
            SQLiteDBHelper.QUESTIONS_COLUMN_ANSWERS[1],
            SQLiteDBHelper.QUESTIONS_COLUMN_ANSWERS[2],
            SQLiteDBHelper.QUESTIONS_COLUMN_ANSWERS[3]
        )

        val selection = SQLiteDBHelper.QUESTIONS_COLUMN_QUIZ_ID + " = ?"

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
        val questions = ArrayList<Question>()
        cursor.moveToFirst()
        while (cursor.moveToNext()) {
            questions.add(Question(cursor.getString(2),
                arrayListOf(cursor.getString(4), cursor.getString(5),
                    cursor.getString(6), cursor.getString(7)),
                cursor.getInt(0), cursor.getInt(3)))
        }
        cursor.close()
        return questions
    }



    override fun saveQuestionToDB(question: Question) {
        val database = SQLiteDBHelper(context).writableDatabase
        val values = ContentValues()
        SQLiteDBHelper.QUESTIONS_COLUMN_ANSWERS.forEachIndexed { index: Int, answer: String ->
            values.put(answer, question.answers[index])
        }
        values.put(SQLiteDBHelper.QUESTIONS_COLUMN_QUESTION, question.question)
        values.put(SQLiteDBHelper.QUESTIONS_COLUMN_QUIZ_ID, question.quizId)
        values.put(SQLiteDBHelper.QUESTIONS_COLUMN_CORRECT_ANSWER, question.correctAnswer)
        val newRowId = database.insert(SQLiteDBHelper.QUESTIONS_TABLE_NAME, null, values)

        Toast.makeText(context, "The new Row Id is $newRowId", Toast.LENGTH_LONG).show()
    }
}
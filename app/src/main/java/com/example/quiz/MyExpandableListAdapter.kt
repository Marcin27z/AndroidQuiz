package com.example.quiz

import android.content.Context
import android.content.Intent
import android.database.DataSetObserver
import android.support.v4.content.ContextCompat.startActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ExpandableListAdapter
import android.widget.TextView
import com.example.quiz.activities.AddQuestionActivity
import com.example.quiz.entity.Question
import com.example.quiz.entity.Quiz
import io.objectbox.Box
import io.objectbox.kotlin.boxFor

class MyExpandableListAdapter(private val context: Context): ExpandableListAdapter {

    private val quizBox: Box<Quiz> = ObjectBox.get()!!.boxFor()
    private val quizzes = quizBox.query().build().find()

    override fun getGroup(groupPosition: Int): Any {
        return quizzes[groupPosition]
    }

    override fun onGroupCollapsed(groupPosition: Int) {
    }

    override fun isEmpty(): Boolean {
        return false
    }

    override fun registerDataSetObserver(observer: DataSetObserver?) {
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return quizzes[groupPosition].questions[childPosition]
    }

    override fun onGroupExpanded(groupPosition: Int) {
    }

    override fun getCombinedChildId(groupId: Long, childId: Long): Long {
        return 0
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        return if (convertView == null) {
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            if (!isLastChild) {
                val question = getChild(groupPosition, childPosition) as Question
                val tempView = layoutInflater.inflate(R.layout.child_items, null)
                val childItem = tempView!!.findViewById(R.id.childItem) as TextView
                childItem.text = question.question
                tempView
            } else {
                val inflater = layoutInflater.inflate(R.layout.child_add, null)
                inflater.findViewById<Button>(R.id.newQuestionButton).setOnClickListener {
                    val intent = Intent(context, AddQuestionActivity::class.java)
                    intent.putExtra("quiz", groupPosition)
                    startActivity(context, intent, null)
                }
                inflater
            }
        } else {
            convertView
        }
    }

    override fun areAllItemsEnabled(): Boolean {
        return false
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getCombinedGroupId(groupId: Long): Long {
        return 0
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        val view: View?
        view = if (convertView == null) {
            val inf = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            inf.inflate(R.layout.group_items, null)
        } else {
            convertView
        }

        val heading = view!!.findViewById(R.id.heading) as TextView
        heading.text = (getGroup(groupPosition) as Quiz).name
        return view
    }

    override fun unregisterDataSetObserver(observer: DataSetObserver?) {
    }

    override fun getGroupCount(): Int {
        return quizzes.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return quizzes[groupPosition].questions.size + 1
    }
}
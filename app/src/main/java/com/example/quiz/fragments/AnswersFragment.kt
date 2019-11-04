package com.example.quiz.fragments

import android.content.Context
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.quiz.R
import kotlinx.android.synthetic.main.fragment_answers.view.*


private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AnswersFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AnswersFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class AnswersFragment : Fragment() {

    private lateinit var answers: ArrayList<String>

    private var listener: OnFragmentInteractionListener? = null
    private lateinit var inflatedView: View

    private lateinit var answerButtons: List<Button>

    fun setAnswersOnButtons(answers: ArrayList<String>) {
        if (answers.size < 4)
            return
        this.answers = answers
        answerButtons.forEachIndexed { index: Int, button: Button -> button.text = answers[index] }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            answers = it.getStringArrayList(ARG_PARAM1)
        }
    }

    fun setButtonCorrect(index: Int) {
        answerButtons[index].background.setColorFilter(0xFF00FF00.toInt(), PorterDuff.Mode.MULTIPLY)
    }

    fun setButtonIncorrect(index: Int) {
        answerButtons[index].background.setColorFilter(0xFFFF0000.toInt(), PorterDuff.Mode.MULTIPLY)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        inflatedView = inflater.inflate(R.layout.fragment_answers, container, false)
        answerButtons = arrayListOf(inflatedView.answer1, inflatedView.answer2, inflatedView.answer3, inflatedView.answer4)
        attachButtonsListeners()
        setAnswersOnButtons(answers)
        return inflatedView
    }

    private fun attachButtonsListeners() {
        answerButtons.forEachIndexed { index: Int, button: Button ->
            button.setOnClickListener { listener?.onAnswerClicked(index) }
        }
    }

    fun clearColors() {
        answerButtons.forEach {
            it.background.clearColorFilter()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {

        fun onAnswerClicked(index: Int)

        fun tap()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param answer1 Parameter 1.
         * @param answer2 Parameter 2.
         * @return A new instance of fragment AnswersFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(answers: ArrayList<String>?) =
            AnswersFragment().apply {
                arguments = Bundle().apply {
                    putStringArrayList(ARG_PARAM1, answers)
                }
            }
    }
}

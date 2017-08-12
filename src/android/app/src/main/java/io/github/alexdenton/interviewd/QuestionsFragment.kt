package io.github.alexdenton.interviewd

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.alexdenton.interviewd.question.Question

class QuestionsFragment : Fragment() {

    val numCols = 2
    var questions: List<Question> = listOf(Question("Question 1", "This is a question", 15),
            Question("Question 2", "This is another question", 10),
            Question("Question 3", "This is yet another question", 15),
            Question("Question 4", "You guessed it, another question", 30),
            Question("Question 5", "Wow! Look at all the questions!", 30),
            Question("Question 6", "This is a question that involves some text", 10),
            Question("Question 7", "This is a question that involves some more text", 5),
            Question("Question 8", "This is another question that involves even more text", 30),
            Question("Question 9", "Can you see a pattern here?", 30),
            Question("Question 10", "This question quite possibly involves the largest amount of text yet. It is specifically designed to fill up a lot of space.", 45),
            Question("Question 11", "Back to short ones", 5),
            Question("Question 12", "Some questions require lots of thought", 35),
            Question("Question 13", "Some questions don't", 5),
            Question("Question 14", "It's up to you to figure it out", 10))

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_questions, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.questions_recyclerView)

        recyclerView.layoutManager = GridLayoutManager(context, numCols)
        recyclerView.adapter = QuestionsAdapter(questions)


        return view
    }

}

package io.github.alexdenton.interviewd


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import io.github.alexdenton.interviewd.api.TemplateRetrofitRepository
import io.github.alexdenton.interviewd.interview.Template


/**
 * A simple [Fragment] subclass.
 */
class TemplatesFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var errorTextView: TextView
    var templates: List<Template> = emptyList()
    val numCols = 2
    val presenter = TemplatesPresenter(TemplateRetrofitRepository(), this)

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_templates, container, false)

        recyclerView = view.findViewById(R.id.templates_recyclerView)
        progressBar = view.findViewById(R.id.templates_progressBar)
        errorTextView = view.findViewById(R.id.templates_failedToGetTemplatesText)

        recyclerView.layoutManager = GridLayoutManager(context, numCols)
        recyclerView.adapter = TemplatesAdapter(templates)

        presenter.getAllTemplates()

        return view
    }

    fun onCouldNotConnect() {
        recyclerView.visibility = View.GONE
        progressBar.visibility = View.GONE
        errorTextView.visibility = View.VISIBLE
    }


    fun onFoundTemplates(list: List<Template>) {
        templates = list
        recyclerView.adapter.notifyDataSetChanged()
        progressBar.visibility = View.GONE
    }

}

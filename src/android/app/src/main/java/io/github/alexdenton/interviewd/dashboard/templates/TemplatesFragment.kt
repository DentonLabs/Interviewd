package io.github.alexdenton.interviewd.dashboard.templates


import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.api.TemplateRetrofitRepository
import io.github.alexdenton.interviewd.createtemplate.CreateTemplateActivity
import io.github.alexdenton.interviewd.interview.Template


/**
 * A simple [Fragment] subclass.
 */
class TemplatesFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var errorTextView: TextView
    lateinit var addTemplateFab: FloatingActionButton
    var templates: List<Template> = emptyList()
    val numCols = 2
    lateinit var presenter: TemplatesPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_templates, container, false)

        presenter = TemplatesPresenter(TemplateRetrofitRepository(context), this)

        recyclerView = view.findViewById(R.id.templates_recyclerView)
        progressBar = view.findViewById(R.id.templates_progressBar)
        errorTextView = view.findViewById(R.id.templates_failedToGetTemplatesText)
        addTemplateFab = view.findViewById(R.id.templates_addTemplateFab)

        addTemplateFab.setOnClickListener { startActivity(Intent(context, CreateTemplateActivity::class.java)) }

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
        recyclerView.adapter = TemplatesAdapter(list)
        recyclerView.adapter.notifyDataSetChanged()
        progressBar.visibility = View.GONE
    }



}

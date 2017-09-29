package io.github.alexdenton.interviewd.dashboard.templates


import android.arch.lifecycle.ViewModelProviders
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
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.appKodein
import com.jakewharton.rxbinding2.view.clicks
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.createtemplate.CreateTemplateActivity
import io.github.alexdenton.interviewd.interview.Template
import io.github.rfonzi.rxaware.BaseFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


/**
 * A simple [Fragment] subclass.
 */
class TemplatesFragment : BaseFragment() {

    lateinit var vm: TemplatesViewModel

    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var errorTextView: TextView
    lateinit var addTemplateFab: FloatingActionButton
    val adapter = TemplatesAdapter(mutableListOf())
    val numCols = 2

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_templates, container, false)

        vm = ViewModelProviders.of(this).get(TemplatesViewModel::class.java)
        vm.initWith(LazyKodein(appKodein))

        recyclerView = view.findViewById(R.id.templates_recyclerView)
        progressBar = view.findViewById(R.id.templates_progressBar)
        errorTextView = view.findViewById(R.id.templates_failedToGetTemplatesText)
        addTemplateFab = view.findViewById(R.id.templates_addTemplateFab)

        recyclerView.layoutManager = GridLayoutManager(context, numCols)
        recyclerView.adapter = adapter

        vm.exposeAddFab(addTemplateFab.clicks())
        vm.exposeItemClicks(adapter.getItemClicks())
        vm.getTemplatesObservable()
                .timeout(5, TimeUnit.SECONDS)
                .firstElement()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({foundTemplates(it)},
                        {couldNotConnect()})
                .lifecycleAware()


        return view
    }

    fun couldNotConnect() {
        recyclerView.visibility = View.GONE
        progressBar.visibility = View.GONE
        errorTextView.visibility = View.VISIBLE
    }


    fun foundTemplates(list: List<Template>) {
        adapter.templates.clear()
        adapter.templates.addAll(list)
        adapter.notifyDataSetChanged()
        progressBar.visibility = View.GONE
    }



}

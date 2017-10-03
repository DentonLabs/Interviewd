package io.github.alexdenton.interviewd.detailtemplate


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.TextView
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.interview.Template
import io.github.rfonzi.rxaware.RxAwareFragment

class TemplateDetailShowFragment : RxAwareFragment() {

    lateinit var vm: TemplateDetailViewModel

    lateinit var templateName: TextView
    lateinit var templateEst: TextView
    lateinit var questionRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_template_detail_show, container, false)
        (activity as TemplateDetailActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        vm = ViewModelProviders.of(activity).get(TemplateDetailViewModel::class.java)

        templateName = view.findViewById(R.id.templateDetail_name)
        templateEst = view.findViewById(R.id.templateDetail_estimate)
        questionRecyclerView = view.findViewById(R.id.templateDetail_recyclerView)

        vm.getTemplateObservable()
                .subscribe { setupTemplate(it) }
                .lifecycleAware()

        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_edit, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.menu_edit -> vm.beginEditingTemplate()
        }

        return super.onOptionsItemSelected(item)
    }

    fun setupTemplate(template: Template){
        val adapter = QuestionDetailAdapter(template.questions)
        questionRecyclerView.adapter = adapter
        questionRecyclerView.layoutManager = LinearLayoutManager(context)

        templateName.text = template.name
        templateEst.text = resources.getString(R.string.est, template.questions.sumBy { it.timeEstimate })
    }

}

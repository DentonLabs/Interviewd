package io.github.alexdenton.interviewd.template.detail


import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.entities.Template
import io.github.alexdenton.interviewd.template.templateform.AddEditTemplateActivity
import io.github.rfonzi.rxaware.RxAwareFragment

class TemplateDetailFragment : RxAwareFragment() {

    lateinit var vm: TemplateDetailViewModel

    lateinit var templateName: TextView
    lateinit var templateEst: TextView
    lateinit var questionRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_template_detail, container, false)
        (activity as TemplateDetailActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        vm = ViewModelProviders.of(activity).get(TemplateDetailViewModel::class.java)

        templateName = view.findViewById(R.id.templateDetail_name)
        templateEst = view.findViewById(R.id.templateDetail_estimate)
        questionRecyclerView = view.findViewById(R.id.templateDetail_recyclerView)

        setHasOptionsMenu(true)

        return view
    }

    override fun onResume() {
        super.onResume()
        vm.fetchTemplate(vm.id)
                .subscribe { template -> setupTemplate(template) }
                .lifecycleAware()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_edit, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.menu_edit -> {
                startActivity(Intent(context, AddEditTemplateActivity::class.java)
                        .apply {
                            putExtra("editing_template", vm.id)
                        })
            }
            R.id.menu_delete -> showDeleteConfirmation()
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

    fun showDeleteConfirmation() = MaterialDialog.Builder(context)
            .content("Are you sure you want to delete the ${templateName.text} template?")
            .positiveText("Okay")
            .negativeText("Cancel")
            .onPositive { dialog, which ->
                vm.deleteTemplate()
                        .subscribe { success -> onDeleteTemplate(success) }
                        .lifecycleAware()
            }
            .onNegative { dialog, which -> dialog.dismiss() }
            .build()
            .show()

    fun onDeleteTemplate(template: Template) {
        toast("Deleted ${template.name} template")
        navigateUp()
    }

}

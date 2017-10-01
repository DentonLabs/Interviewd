package io.github.alexdenton.interviewd.createtemplate.templateform


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.textChanges

import io.github.alexdenton.interviewd.R
import io.github.rfonzi.rxaware.bus.RxBus
import io.github.rfonzi.rxaware.bus.events.*
import io.github.alexdenton.interviewd.createtemplate.CreateTemplateViewModel
import io.github.rfonzi.rxaware.RxAwareFragment


/**
 * A simple [Fragment] subclass.
 */
class TemplateFormFragment : RxAwareFragment() {

    lateinit var titleField: EditText
    lateinit var estText: TextView
    lateinit var recyclerView: RecyclerView
    lateinit var addQuestionButton: Button

    lateinit var adapter: TemplateFormAdapter
    lateinit var touchHelper: TemplateFormTouchHelper

    lateinit var vm: CreateTemplateViewModel

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        vm = ViewModelProviders.of(activity).get(CreateTemplateViewModel::class.java)


        val view = inflater!!.inflate(R.layout.fragment_template_form, container, false)
        titleField = view.findViewById(R.id.createTemplate_titleField)
        estText = view.findViewById(R.id.createTemplate_estDurationText)
        recyclerView = view.findViewById(R.id.createTemplate_recyclerView)
        addQuestionButton = view.findViewById(R.id.createTemplate_addQuestionButton)
        adapter = TemplateFormAdapter(mutableListOf())
        touchHelper = TemplateFormTouchHelper(adapter)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        touchHelper.attachToRecyclerView(recyclerView)

        vm.chosenQuestions.subscribe {
            adapter.bankedQuestions.clear()
            adapter.bankedQuestions.addAll(it)
            adapter.notifyDataSetChanged()
        }.lifecycleAware()

        vm.exposeNameField(titleField.textChanges())
        vm.exposeAddQuestionButton(addQuestionButton.clicks())

        retainInstance = true
        setHasOptionsMenu(true)

        return view
    }

    fun onUpdateSuccess() {
        recyclerView.adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_submit, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.menu_submit -> vm.submitTemplate()
        }

        return super.onOptionsItemSelected(item)
    }


}

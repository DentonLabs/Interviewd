package io.github.alexdenton.interviewd.interview.addedit

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jakewharton.rxrelay2.PublishRelay
import io.github.alexdenton.interviewd.R
//import io.github.rfonzi.rxaware.bus.RxBus
//import io.github.rfonzi.rxaware.bus.events.TemplateSelectedEvent
import io.github.alexdenton.interviewd.entities.Template
import io.reactivex.Observable

/**
 * Created by ryan on 9/16/17.
 */
class LoadTemplateAdapter(val templates: MutableList<Template>) : RecyclerView.Adapter<LoadTemplateAdapter.LoadTemplateViewholder>() {

    private val itemClicks: PublishRelay<Template> = PublishRelay.create()

    fun getItemsClicked(): Observable<Template> = itemClicks

    override fun onBindViewHolder(holder: LoadTemplateViewholder?, position: Int) {
        holder?.templateNameView?.text = templates[position].name

        val est = templates[position].questions.sumBy { it.timeEstimate }

        holder?.templateEstView?.text = holder?.itemView?.resources?.getString(R.string.est, est)

        holder?.itemView?.setOnClickListener {
            itemClicks.accept(templates[position])
        }
    }

    override fun getItemCount(): Int = templates.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): LoadTemplateViewholder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.list_load_template, parent, false)

        return LoadTemplateViewholder(view)
    }

    class LoadTemplateViewholder(itemview: View) : RecyclerView.ViewHolder(itemview){
        var templateNameView: TextView = itemview.findViewById(R.id.listLoadTemplate_templateName)
        var templateEstView: TextView = itemview.findViewById(R.id.listLoadTemplate_templateEstimate)
    }
}
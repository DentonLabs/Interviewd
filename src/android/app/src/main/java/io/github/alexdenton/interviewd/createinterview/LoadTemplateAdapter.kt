package io.github.alexdenton.interviewd.createinterview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.bus.RxBus
import io.github.alexdenton.interviewd.bus.events.TemplateSelectedEvent
import io.github.alexdenton.interviewd.interview.Template

/**
 * Created by ryan on 9/16/17.
 */
class LoadTemplateAdapter(val templates: MutableList<Template>) : RecyclerView.Adapter<LoadTemplateAdapter.LoadTemplateViewholder>() {
    override fun onBindViewHolder(holder: LoadTemplateViewholder?, position: Int) {
        holder?.templateNameView?.text = templates[position].name

        val est = templates[position].questions.sumBy { it.timeEstimate }

        holder?.templateEstView?.text = holder?.itemView?.resources?.getString(R.string.est, est)

        holder?.itemView?.setOnClickListener {
            RxBus.post(TemplateSelectedEvent(templates[position]))
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
package io.github.alexdenton.interviewd.dashboard.templates

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.interview.Template


/**
 * Created by ryan on 8/14/17.
 */
class TemplatesAdapter(var templates: List<Template>) : RecyclerView.Adapter<TemplatesAdapter.TemplateViewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TemplateViewholder {
        val view: View = LayoutInflater.from(parent?.context).inflate(R.layout.card_template, parent, false)

        return TemplateViewholder(view)
    }

    override fun onBindViewHolder(holder: TemplateViewholder?, position: Int) {
        holder?.nameView?.text = templates[position].name
    }

    override fun getItemCount(): Int = templates.size


    class TemplateViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameView: TextView = itemView.findViewById(R.id.card_templateName)
    }
}
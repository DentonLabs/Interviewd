package io.github.alexdenton.interviewd.createtemplate.templateform

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import io.github.alexdenton.interviewd.question.Question

/**
 * Created by ryan on 8/28/17.
 */
class TemplateFormTouchHelper(val adapter: TemplateFormAdapter)
    : ItemTouchHelper(object : SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0) {
    override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
        val from = viewHolder?.adapterPosition!!
        val to = target?.adapterPosition!!
        adapter.bankedQuestions.swap(from, to)
        adapter.notifyItemMoved(from, to)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isLongPressDragEnabled() = false


    private fun MutableList<Question>.swap(index1: Int, index2: Int) {
        val tmp = this[index1] // 'this' corresponds to the list
        this[index1] = this[index2]
        this[index2] = tmp
    }

}){
    init {
        adapter.touchHelper = this
    }
}
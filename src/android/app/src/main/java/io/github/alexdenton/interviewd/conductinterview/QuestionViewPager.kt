package io.github.alexdenton.interviewd.conductinterview

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent



/**
 * Created by ryan on 9/27/17.
 */
class QuestionViewPager : ViewPager {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    var swipeEnabled: Boolean = false

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return swipeEnabled && super.onTouchEvent(event)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return swipeEnabled && super.onInterceptTouchEvent(event)
    }
}
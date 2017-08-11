package io.github.alexdenton.interviewd

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class PlaceholderFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater?.inflate(R.layout.fragment_placeholder, container, false)!!

        return view
    }

}

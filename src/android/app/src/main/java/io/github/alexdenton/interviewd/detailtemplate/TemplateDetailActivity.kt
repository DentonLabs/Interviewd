package io.github.alexdenton.interviewd.detailtemplate

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.appKodein
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.interview.Template
import io.github.rfonzi.rxaware.RxAwareActivity

class TemplateDetailActivity : RxAwareActivity() {

    lateinit var vm: TemplateDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_template_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        vm = ViewModelProviders.of(this).get(TemplateDetailViewModel::class.java)

        if (savedInstanceState == null) {
            vm.initWith(LazyKodein(appKodein))
            vm.use(receive() as Int)

            fragmentTransaction {
                add(R.id.templateDetail_fragmentContainer, TemplateDetailShowFragment())
            }
        }


    }
}

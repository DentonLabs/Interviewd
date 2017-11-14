package io.github.alexdenton.interviewd.template.detail

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.appKodein
import io.github.alexdenton.interviewd.R
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
            vm.useId(receive() as Long)

            fragmentTransaction {
                add(R.id.templateDetail_fragmentContainer, TemplateDetailFragment())
            }
        }


    }
}

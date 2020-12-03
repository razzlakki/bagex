package com.tezsol.bagex

import android.os.Bundle
import android.transition.Explode
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.tezsol.bagex.util.ScanDetailsUtil
import kotlinx.android.synthetic.main.app_bar_all.*

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(window) {
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
            // set set the transition to be shown when the user enters this activity
            enterTransition = Explode()
            // set the transition to be shown when the user leaves this activity
            exitTransition = Explode()
        }
        onPreOnCreate()
        setContentView(getRootResource())
        initDefaults()
        onPostOnCreate()
    }

    private fun initDefaults() {
        backIcon?.setOnClickListener { finish() }
    }

    abstract fun onPostOnCreate()


    protected fun onPreOnCreate() {
    }

    protected abstract fun getRootResource(): Int


    override fun onDestroy() {
        super.onDestroy()
        ScanDetailsUtil.instance.clearOrderInfo()
    }
}
package com.tezsol.bagex

import android.content.Intent
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_jobs.*
import kotlinx.android.synthetic.main.app_bar_all.*


abstract class DashboardItmBaseActivity : BaseActivity() {

    override fun onPostOnCreate() {
        backIcon?.setOnClickListener { finish() }
        val selectedItem = intent.getIntExtra("selectedItem", 0);
        if (selectedItem != 0)
            bottom_nav?.selectedItemId = selectedItem;
        bottom_nav?.setOnNavigationItemSelectedListener { menuItem: MenuItem ->
            navigateToScreen(menuItem.itemId)
        }
    }

    fun navigateToScreen(itemId: Int): Boolean {
        when (itemId) {
            R.id.action_home -> finish()
            R.id.action_job -> navigate(JobsActivity::class.java, R.id.action_job)
            R.id.action_exception -> navigate(ExceptionActivity::class.java, R.id.action_exception)
            R.id.action_more -> navigate(JobsActivity::class.java, R.id.action_more)
        }
        finish()
        return false
    }

    private fun navigate(destination: Class<*>, action: Int) {
        val intent = Intent(this, destination)
        intent.putExtra("selectedItem", action)
        startActivity(intent)
    }


}
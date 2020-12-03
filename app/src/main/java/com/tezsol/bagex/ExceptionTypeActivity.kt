package com.tezsol.bagex

import android.content.Intent
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.tezsol.bagex.adapter.ExceptionListAdapter
import com.tezsol.bagex.model.response.ExceptionType
import com.tezsol.bagex.util.DataUtil
import kotlinx.android.synthetic.main.activity_exception_type.*
import kotlinx.android.synthetic.main.app_bar_all.*
import kotlinx.android.synthetic.main.scan_fragment.view.*

class ExceptionTypeActivity : BaseActivity() {

    private var selectedException: ExceptionType? = null;

    override fun onPostOnCreate() {
        headerText?.text = "Exception Type"
        exceptionList?.apply {
            layoutManager = LinearLayoutManager(this@ExceptionTypeActivity)
            adapter = ExceptionListAdapter(DataUtil.getInstance().exceptionTypes,
                object : ExceptionListAdapter.OnExceptionChangeListener {
                    override fun onExceptionSelected(exceptionType: ExceptionType) {
                        selectedException = exceptionType
                    }
                })
        }

        addException.setOnClickListener {
            if (selectedException != null) {
                val responseIntent = Intent()
                responseIntent.putExtra("exceptionType", selectedException?.name)
                setResult(RESULT_OK, responseIntent)
                finish()
            }
            else
                Toast.makeText(this,"Please Select An Exception",Toast.LENGTH_SHORT).show()

        }

    }

    override fun getRootResource(): Int {
        return R.layout.activity_exception_type
    }
}
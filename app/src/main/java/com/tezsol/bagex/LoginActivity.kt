package com.tezsol.bagex

import android.content.Intent
import android.content.pm.PackageManager
import android.view.View
import android.widget.Toast
import com.tezsol.bagex.api.APIClient
import com.tezsol.bagex.constants.APIConstants
import com.tezsol.bagex.model.request.LoginReq
import com.tezsol.bagex.model.response.LoginResponse
import com.tezsol.bagex.sharedutil.SharedUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BaseActivity() {



    override fun onPostOnCreate() {
        login_btn_view.setOnClickListener {
            if (validate()) {
                doLogin(usernameEdit.text.toString(), passwordEdit.text.toString())
            }
        }

        updatePackageDetails()

    }

    private fun updatePackageDetails() {
        val manager = this.packageManager
        val info = manager.getPackageInfo(this.packageName, PackageManager.GET_ACTIVITIES)
        version_name.text =
            String.format(resources.getString(R.string.version_txt), info.versionName)
    }

    private fun validate(): Boolean {
        return true
    }

    override fun getRootResource(): Int {
        return R.layout.activity_login;
    }

    private fun doLogin(userName: String, password: String) {
        progress.visibility = View.VISIBLE
        val req = LoginReq();
        req.principalid = APIConstants.PRINCIPAL_ID
        req.username = (userName != "").let { userName }.run { "testadmin@test.com" }
        req.password = (password != "").let { password }.run { "Test@123" }
        disposable = APIClient.create()
            .login(req)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> checkRes(result) },
                { error -> showError(error.message) }
            )
    }

    private fun showError(message: String?) {
        progress.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun checkRes(result: LoginResponse?) {
        progress.visibility = View.GONE
        if (result != null && result.data.isNotEmpty()) {
            SharedUtil.instance.putBoolean(SharedUtil.IS_LOGIN, true)
            SharedUtil.instance.putString(SharedUtil.LOGIN_TOKEN, result.data[0].token)
            SharedUtil.instance.putString(SharedUtil.USER_ID, result.data[0].userId)
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        } else {
            showError("Unable to login")
        }
    }


    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}
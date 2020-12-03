package com.tezsol.bagex

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.os.Handler
import android.provider.Settings
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.tezsol.bagex.sharedutil.SharedUtil

class SplashActivity : BaseActivity() {

    private val SPLASH_TIME_OUT: Long = 3000 // 1 sec

    override fun getRootResource(): Int {
        return R.layout.activity_splash;
    }

    override fun onPostOnCreate() {

//        Handler().postDelayed({
//            AnimateUtil.instance.zoomAnimation(bagXLogo);
//        }, 2700)
//      SharedUtil.instance.putBoolean(SharedUtil.IS_LOGIN, false)

        Dexter.withActivity(this@SplashActivity)
            .withPermission(Manifest.permission.CAMERA)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    Handler().postDelayed({
                        navigateToNext()
                    }, SPLASH_TIME_OUT)
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    showSettingsDialog()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    token!!.continuePermissionRequest()
                }

            })
            .onSameThread()
            .check()

    }

    private fun showSettingsDialog() {
        val builder = AlertDialog.Builder(this@SplashActivity)
        builder.setTitle("Need Permissions")
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.")
        builder.setPositiveButton(
            "GOTO SETTINGS"
        ) { dialog, which ->
            dialog.cancel()
            openSettings()
        }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog, which -> dialog.cancel() }
        builder.show()
    }

    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_SETTINGS)
        startActivityForResult(intent, 101)
    }

    private fun navigateToNext() {
        if (SharedUtil.instance.getBoolean(SharedUtil.IS_LOGIN)!!) {
            startActivity(Intent(this, DashboardActivity::class.java))
        } else
            startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101) {
            onPostOnCreate()
        }
    }

}
package com.tezsol.bagex

import android.app.Application
import com.tezsol.bagex.api.APIClient
import com.tezsol.bagex.sharedutil.SharedUtil

class BageXApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        SharedUtil.instance.initPref(this)
        APIClient.create()
    }

}
package com.tezsol.bagex.sharedutil

import android.content.Context
import android.content.SharedPreferences


class SharedUtil private constructor() {

    private lateinit var context: Context;

    private val SHARED_PREFERENCE_KEY = "com.tezsol.bagex.prefrence"


    private object HOLDER {
        val INSTANCE = SharedUtil()
    }

    companion object {
        const val USER_ID = "user_id"
        const val LOGIN_TOKEN = "login_token"
        const val IS_LOGIN = "is_login"
        val instance: SharedUtil by lazy { HOLDER.INSTANCE }
    }


    fun initPref(context: Context) {
        this.context = context
    }


    private fun getPref(): SharedPreferences? {
        return context.getSharedPreferences(
            SHARED_PREFERENCE_KEY,
            Context.MODE_PRIVATE
        )
    }

    fun putString(
        key: String?,
        value: String?
    ) {
        getPref()?.edit()?.putString(key, value)?.apply()
    }

    fun getString(key: String?): String? {
        return getPref()?.getString(key, null)
    }


    fun putInt(
        key: String?,
        value: Int?
    ) {
        getPref()?.edit()?.putInt(key, value!!)?.apply()
    }

    fun putLong(
        key: String?,
        value: Long?
    ) {
        getPref()?.edit()?.putLong(key, value!!)?.apply()
    }

    fun getInt(key: String?): Int? {
        return getPref()?.getInt(key, 0)
    }

    fun getLong(key: String?): Long? {
        return getPref()?.getLong(key, 0)
    }

    fun clearAll() {
        getPref()?.edit()?.clear()?.apply();
    }


    fun getBoolean(key: String?): Boolean? {
        return getPref()
            ?.getBoolean(key, false)
    }

    fun putBoolean(
        key: String?,
        value: Boolean?
    ) {
        getPref()?.edit()?.putBoolean(key, value!!)?.apply()
    }


}
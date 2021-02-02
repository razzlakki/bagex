package com.tezsol.bagex.api

import com.tezsol.bagex.constants.APIConstants
import com.tezsol.bagex.model.request.LoginReq
import com.tezsol.bagex.sharedutil.SharedUtil
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route


class Authenticator : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code() == 401) {
            val req = LoginReq();
            req.principalid = APIConstants.PRINCIPAL_ID
            req.username = "testadmin@test.com"
            req.password = "Test@123"
            val authTokenResponse =
                APIClient.create().getAuthenticationToken(req).execute().body()!!
            if (authTokenResponse?.code == 200) {
                SharedUtil.instance.putString(
                    SharedUtil.LOGIN_TOKEN,
                    authTokenResponse.data[0].token
                )
                return response.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${authTokenResponse.data[0].token}")
                    .build()
            }
        }
        return response.request().newBuilder().build()
    }

}
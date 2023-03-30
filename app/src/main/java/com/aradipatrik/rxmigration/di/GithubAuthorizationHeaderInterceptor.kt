package com.aradipatrik.rxmigration.di

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class GithubAuthorizationHeaderInterceptor @Inject constructor(
    @ApiSecret
    private val apiSecret: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // add apiSecret as Bearer token to the header of the request
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $apiSecret")
        return chain.proceed(newRequest.build())
    }
}
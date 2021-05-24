package com.subzoron.ninecodingchallenge.network

import com.subzoron.ninecodingchallenge.datamodels.ShowsResponse
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


// Given that there aren't multiple pages from this api, I am using RxJava to handle the results
// If pagination was required, I would use Paging library 3 + an MVVM style of architecture
interface ShowsInterface {
    @GET("sample_request.json")
    fun getShows(): Observable<ShowsResponse>

    companion object{
        fun create(): ShowsInterface {
            val baseURL = "http://codingchallenge.nine.com.au/"
            val httpClient = OkHttpClient.Builder()

            // appears to need an accept header
            httpClient.addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val original = chain.request()
                    val response: Response
                    val request = original.newBuilder()
                        .addHeader("Accept", "application/json")
                        .build()
                    response = chain.proceed(request);
                    return response;
                }
            })

            val retrofit = Retrofit.Builder()
                //.client(httpClient.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseURL)
                .build()

            return retrofit.create(ShowsInterface::class.java)
        }
    }
}
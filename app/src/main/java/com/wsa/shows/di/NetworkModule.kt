package com.wsa.shows.di

import android.content.Context
import android.util.Log
import com.wsa.shows.network.NetworkHelper
import com.wsa.shows.network.api.ApiService
import com.wsa.shows.network.api.ApiConstants.BASE_URL
import com.wsa.shows.network.api.ApiConstants.KEY_API
import com.wsa.shows.network.api.ApiConstants.VALUE_API
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.Interceptor.*
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideAuthInterceptorOkHttpClient(authInterceptor: Interceptor
    ) = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()


    @Provides
    @Singleton
    fun provideInterceptor() = Interceptor { chain ->
        val original = chain.request()
        val originalHttpUrl = original.url

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter(KEY_API, VALUE_API)
            .addQueryParameter("language" , "en-US")
            .build()
        val requestBuilder = original.newBuilder()
            .url(url)
        val request: Request = requestBuilder.build()
        Log.i("URL ==>", "${request.url}")
        chain.proceed(request)


    }


    @Provides
    @Singleton
    fun provideApiService(okHttpClient: OkHttpClient) =
        Retrofit.Builder()
             .baseUrl(BASE_URL)
             .client(okHttpClient)
            //.baseUrl("https://www.boredapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideNetworkHelper( @ApplicationContext context: Context)=
        NetworkHelper(context)

}
package com.example.cryptocurrency.di
import com.example.cryptocurrency.api_service.BlockSpanApi
import com.example.cryptocurrency.api_service.CryptoApi
import com.example.cryptocurrency.api_service.CryptoNewsApi
import com.example.cryptocurrency.api_service.GeckoApi
import com.example.cryptocurrency.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    @Named("CoinMarketCap")
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request()
            val newRequest = request.newBuilder()
                .addHeader("X-CMC_PRO_API_KEY", Constants.COIN_MARKET_CAP_API_KEY)
                .build()
            chain.proceed(newRequest)
        }
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideRetrofitCoinMarketCap(@Named("CoinMarketCap") okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.COIN_MARKET_CAP_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideCryptoApi(retrofit: Retrofit): CryptoApi = retrofit.create(CryptoApi::class.java)

    @Provides
    @Singleton
    @Named("CryptoNews")
    fun provideOkHttpClient1(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request()
            val newRequest = request.newBuilder()
                .addHeader("x-api-key",Constants.NEWS_API_KEY)
                .build()
            chain.proceed(newRequest)
        }
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideRetrofitCryptoNews(@Named("CryptoNews") okHttpClient: OkHttpClient): CryptoNewsApi =
        Retrofit.Builder()
            .baseUrl(Constants.NEWS_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(CryptoNewsApi::class.java)

    @Provides
    @Singleton
    @Named("CoinGecko")
    fun provideOkHttpClient2(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request()
            val newRequest = request.newBuilder()
                .build()
            chain.proceed(newRequest)
        }
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideRetrofitCoinGecko(@Named("CoinGecko") okHttpClient: OkHttpClient): GeckoApi =
        Retrofit.Builder()
            .baseUrl(Constants.COIN_GECKO_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(GeckoApi::class.java)

    @Provides
    @Singleton
    @Named("BlockSpan")
    fun provideOkHttpClient3(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request()
            val newRequest = request.newBuilder()
                .addHeader("x-api-key", Constants.BLOCK_SPAN_URL)
                .build()
            chain.proceed(newRequest)
        }
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideRetrofitBlockSpan(@Named("BlockSpan") okHttpClient: OkHttpClient): BlockSpanApi =
        Retrofit.Builder()
            .baseUrl(Constants.BLOCK_SPAN_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(BlockSpanApi::class.java)

}
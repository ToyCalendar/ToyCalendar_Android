package yapp.co.kr.toycalendar.util

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import javax.security.cert.CertificateException

var API_DEV_URL = ""
var API_REMOTE_URL = ""

lateinit var client: OkHttpClient

lateinit var devRetrofit: Retrofit
lateinit var remoteRetrofit: Retrofit

lateinit var devInterface: NetworkInterface
lateinit var remoteInterface: NetworkInterface


fun networkInit() {
    var logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    devRetrofit = Retrofit.Builder()
            .baseUrl(API_DEV_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()

    remoteRetrofit = Retrofit.Builder()
        .baseUrl(API_REMOTE_URL)
        .client(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()

    devInterface = devRetrofit.create(NetworkInterface::class.java)
    remoteInterface = remoteRetrofit.create(NetworkInterface::class.java)
}

fun getInstance(isTestMode: Boolean) = if (isTestMode) devInterface else remoteInterface

// https 접속을 위한
fun getUnsafeOkHttpClient(): OkHttpClient.Builder {
    try {
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {

            override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                return arrayOf()
            }

            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
            }
        })

        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())

        val sslSocketFactory = sslContext.getSocketFactory()

        val builder = OkHttpClient.Builder()
        builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        builder.hostnameVerifier { hostname, session -> true }
        return builder
    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}
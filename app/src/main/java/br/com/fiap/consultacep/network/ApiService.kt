package br.com.fiap.consultacep.network

import br.com.fiap.consultacep.model.Logradouro
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


private const val BASE_URL = "https://viacep.com.br/ws/"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface CepApiService {

    @GET("{cep}/json")
    suspend fun getLogradouro(@Path("cep") cep: String): Logradouro
}

object CepApi {
    val retrofitService: CepApiService by lazy {
        retrofit.create(CepApiService::class.java)
    }
}
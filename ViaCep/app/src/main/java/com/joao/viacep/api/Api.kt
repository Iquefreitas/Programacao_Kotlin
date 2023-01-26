package com.joao.viacep.api

import com.joao.viacep.model.Endereco
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import java.security.cert.CertPath

interface Api {

    @GET("ws/{cep}/json/")
    fun setEndereco(@Path("cep") cep: String): Call<Endereco>
}
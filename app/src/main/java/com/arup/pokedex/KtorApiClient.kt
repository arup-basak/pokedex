package com.arup.pokedex

import android.app.Activity
import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.URLBuilder
import io.ktor.serialization.gson.gson

class KtorApiClient(
    private val activity: Activity
) {
    private val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            gson()
        }
    }
    suspend fun getPokemonList(offset: Int, limit: Int): PokemonList {
        val baseUrl = activity.getString(R.string.pokeapi_base_url)
        val url = URLBuilder(baseUrl).apply {
            parameters.append("offset", offset.toString())
            parameters.append("limit", limit.toString())
        }.build()

        val response: HttpResponse = httpClient
            .get(url)

        return if(response.status.value in 200..299) {
            try {
                response.body<PokemonList>()
            }
            catch (e: Exception) {
                Log.d(activity.callingPackage, e.message.toString())
                getEmptyPokemonList()
            }
        } else {
            getEmptyPokemonList()
        }
    }
}
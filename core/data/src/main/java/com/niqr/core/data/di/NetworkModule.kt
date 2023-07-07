package com.niqr.core.data.di

import com.niqr.core.data.NetworkConstants.BASE_URL
import com.niqr.core.data.NetworkConstants.LIST
import com.niqr.core.di.AppScope
import dagger.Module
import dagger.Provides
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json

@Module
object NetworkModule {

    @Provides
    @AppScope
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json()
            }
            defaultRequest {
                //header(GENERATE_FAILS, "10")
                contentType(ContentType.Application.Json)
                url(BASE_URL + LIST)
            }
        }
    }
}

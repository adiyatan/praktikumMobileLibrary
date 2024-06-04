package id.ac.unpas.agenda.networks

import com.skydoves.sandwich.ApiResponse
import id.ac.unpas.agenda.models.BookRequest
import id.ac.unpas.agenda.networks.responses.BookRequestGetResponse
import id.ac.unpas.agenda.networks.responses.BookDeleteResponse
import id.ac.unpas.agenda.networks.responses.BookPostResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface BookRequestApi {
    @GET("book-request")
    suspend fun findAll(): ApiResponse<BookRequestGetResponse>

    @POST("todo")
    @Headers("Content-Type: application/json")
    suspend fun insert(@Body todo: BookRequest): ApiResponse<BookPostResponse>

    @PUT("todo/{id}")
    @Headers("Content-Type: application/json")
    suspend fun update(@Path("id") id: String, @Body todo: BookRequest): ApiResponse<BookPostResponse>

    @DELETE("todo/{id}")
    suspend fun delete(@Path("id") id: String): ApiResponse<BookDeleteResponse>
}
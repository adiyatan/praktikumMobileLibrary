package id.ac.unpas.agenda.networks

import com.skydoves.sandwich.ApiResponse
import id.ac.unpas.agenda.models.Todo
import id.ac.unpas.agenda.networks.responses.TodoDeleteResponse
import id.ac.unpas.agenda.networks.responses.TodoGetResponse
import id.ac.unpas.agenda.networks.responses.TodoPostResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TodoApi {
    @GET("todo")
    suspend fun findAll(): ApiResponse<TodoGetResponse>

    @POST("todo")
    @Headers("Content-Type: application/json")
    suspend fun insert(@Body todo: Todo): ApiResponse<TodoPostResponse>

    @PUT("todo/{id}")
    @Headers("Content-Type: application/json")
    suspend fun update(@Path("id") id: String, @Body todo: Todo): ApiResponse<TodoPostResponse>

    @DELETE("todo/{id}")
    suspend fun delete(@Path("id") id: String): ApiResponse<TodoDeleteResponse>
}
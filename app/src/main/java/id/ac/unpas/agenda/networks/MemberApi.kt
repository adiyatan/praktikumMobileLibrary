package id.ac.unpas.agenda.networks

import com.skydoves.sandwich.ApiResponse
import id.ac.unpas.agenda.models.Member
import id.ac.unpas.agenda.networks.responses.MemberGetResponse
import id.ac.unpas.agenda.networks.responses.BookDeleteResponse
import id.ac.unpas.agenda.networks.responses.BookPostResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MemberApi {
    @GET("member")
    suspend fun findAll(): ApiResponse<MemberGetResponse>

    @POST("todo")
    @Headers("Content-Type: application/json")
    suspend fun insert(@Body todo: Member): ApiResponse<BookPostResponse>

    @PUT("todo/{id}")
    @Headers("Content-Type: application/json")
    suspend fun update(@Path("id") id: String, @Body todo: Member): ApiResponse<BookPostResponse>

    @DELETE("todo/{id}")
    suspend fun delete(@Path("id") id: String): ApiResponse<BookDeleteResponse>
}
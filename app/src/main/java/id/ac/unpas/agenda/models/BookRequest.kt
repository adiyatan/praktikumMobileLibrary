package id.ac.unpas.agenda.models

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "book_request",
    foreignKeys = [
        ForeignKey(
            entity = Book::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("library_book_id"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Member::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("library_member_id"),
            onDelete = ForeignKey.CASCADE
        )
    ],
)
@Immutable
data class BookRequest(
    @PrimaryKey
    val id: String,
    val library_book_id: String,
    val library_member_id: String,
    @SerializedName("start_date")
    val start_date: String,
    @SerializedName("end_date")
    val end_date: String,
    val status: String,
    val created_at: String,
    val updated_at: String,
)

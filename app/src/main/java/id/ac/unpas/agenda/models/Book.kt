package id.ac.unpas.agenda.models

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
@Immutable
data class Book(
    @PrimaryKey
    val id: String,
    val title: String,
    val author: String,
    @SerializedName("released_date")
    val released_date: String,
    val stock: Int,
    val created_at: String,
    val updated_at: String,
)

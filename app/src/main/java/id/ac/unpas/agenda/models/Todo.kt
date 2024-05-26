package id.ac.unpas.agenda.models

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
@Immutable
data class Todo(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    @SerializedName("due_date")
    val dueDate: String
)

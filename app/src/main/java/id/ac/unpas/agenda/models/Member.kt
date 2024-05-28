package id.ac.unpas.agenda.models

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
@Immutable
data class Member(
    @PrimaryKey
    val id: String,
    val name: String,
    val address: String,
    val phone: String,
    val created_at: String,
    val updated_at: String,
)

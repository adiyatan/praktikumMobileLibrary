package id.ac.unpas.agenda.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import java.util.UUID
import androidx.hilt.navigation.compose.hiltViewModel
import id.ac.unpas.agenda.models.Book
import java.time.LocalDateTime

@Composable
fun BookAddDialog(onDismiss: () -> Unit, onSave: (Book) -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val viewModel: BookViewModel = hiltViewModel()

    // Define state variables
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var releasedDate by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Tambah Buku") },
        text = {
            Column {
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Judul Buku") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = author,
                    onValueChange = { author = it },
                    label = { Text("Penulis") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = releasedDate,
                    onValueChange = { releasedDate = it },
                    label = { Text("Tahun Terbit") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = stock,
                    onValueChange = { stock = it },
                    label = { Text("Stok") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                if (errorMessage != null) {
                    Text(errorMessage!!, color = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                if (title.isBlank() || author.isBlank() || releasedDate.isBlank() || stock.isBlank() || stock.toIntOrNull() == null) {
                    errorMessage = "Please fill all fields correctly."
                } else {
                    coroutineScope.launch {
                        val id = UUID.randomUUID().toString()
                        val createdAt = LocalDateTime.now().toString()
                        val updatedAt = LocalDateTime.now().toString()
                        val book = Book(
                            id = id,
                            title = title,
                            author = author,
                            released_date = releasedDate,
                            stock = stock.toInt(),
                            created_at = createdAt,
                            updated_at = updatedAt
                        )
                        viewModel.insert(
                            id = id,
                            title = title,
                            author = author,
                            released_date = releasedDate,
                            stock = stock.toInt(),
                            created_at = createdAt,
                            update_at = updatedAt
                        )
                        onSave(book)
                        onDismiss()
                    }
                }
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

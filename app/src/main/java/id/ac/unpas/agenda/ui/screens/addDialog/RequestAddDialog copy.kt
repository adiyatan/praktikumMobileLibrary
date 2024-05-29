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



@Composable
fun RequestAddDialog(onDismiss: () -> Unit, onSave: (BookViewModel) -> Unit) {
    val coroutineScope = rememberCoroutineScope()

    data class BookViewModel(
    val id: String,
    val fullName: String,
    val bookName: String,
    val borrowingDate: Int,
    val returnDate: Int
)

    // Define state variables
    var fullName by remember { mutableStateOf("") }
    var bookName by remember { mutableStateOf("") }
    var borrowingDate by remember { mutableStateOf("") }
    var returnDate by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Form Peminjaman") },
        text = {
            Column {
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    label = { Text("Nama Anggota") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = bookName,
                    onValueChange = { bookName = it },
                    label = { Text("Nama Buku") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = borrowingDate,
                    onValueChange = { borrowingDate = it },
                    label = { Text("Tanggal Peminjaman") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = returnDate,
                    onValueChange = { returnDate = it },
                    label = { Text("Tanggal Pengembalian") },
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
                if (fullName.isBlank() || bookName.isBlank() || borrowingDate.isBlank() || returnDate.isBlank() || returnDate.toIntOrNull() == null) {
                    errorMessage = "Please fill all fields correctly."
                } else {
                    val book = BookViewModel(
                        id = UUID.randomUUID().toString(),
                        fullName = fullName,
                        bookName = bookName,
                        borrowingDate = borrowingDate.toInt(),
                        returnDate = returnDate.toInt()
                    )
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

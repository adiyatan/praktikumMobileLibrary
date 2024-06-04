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
import id.ac.unpas.agenda.models.Member
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun MemberAddDialog(onDismiss: () -> Unit, onSave: (Member) -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val viewModel: MemberViewModel = hiltViewModel()

    // Define state variables
    var fullName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    fun sanitizeInput(input: String): String {
        return input.replace(Regex("[^A-Za-z0-9\\s]"), "")
    }

    fun capitalizeWords(input: String): String {
        return input.split(" ").joinToString(" ") { it.lowercase().replaceFirstChar { char -> char.uppercase() } }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Tambah Anggota") },
        text = {
            Column {
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = fullName,
                    onValueChange = { fullName = capitalizeWords(sanitizeInput(it)) },
                    label = { Text("Nama Anggota") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = address,
                    onValueChange = { address = sanitizeInput(it) },
                    label = { Text("Alamat") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Nomor Telepon") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )
                if (errorMessage != null) {
                    Text(errorMessage!!, color = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                if (fullName.isBlank() || address.isBlank() || phone.isBlank()) {
                    errorMessage = "Please fill all fields correctly."
                } else {
                    coroutineScope.launch {
                        val id = UUID.randomUUID().toString()
                        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
                        val createdAt = sdf.format(Date())
                        val updatedAt = sdf.format(Date())
                        val member = Member(
                            id = id,
                            name = fullName,
                            address = address,
                            phone = phone,
                            created_at = createdAt,
                            updated_at = updatedAt
                        )
                        viewModel.insert(
                            id = id,
                            name = fullName,
                            address = address,
                            phone = phone,
                            created_at = createdAt,
                            update_at = updatedAt
                        )
                        onSave(member)
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

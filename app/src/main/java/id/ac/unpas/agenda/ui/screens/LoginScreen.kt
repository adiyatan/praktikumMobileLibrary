package id.ac.unpas.agenda.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.ac.unpas.agenda.R
import id.ac.unpas.agenda.ui.theme.Blue40
import id.ac.unpas.agenda.ui.theme.Blue60
import id.ac.unpas.agenda.ui.theme.White

@Composable
fun LoginScreen(modifier: Modifier = Modifier, onLoginClick: () -> Unit) {
    val password = remember { mutableStateOf("") }
    val openDialog = remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Image section
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
        ) {
            Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Login Logo", modifier = Modifier
                .width(500.dp),)
        }

        // Input fields and buttons section
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
        ) {
            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                placeholder = { Text(text = "Password",  color = Color.Gray) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = roundedOutlinedTextFieldModifier(12.dp).fillMaxWidth().background(color = White),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Blue60
                ),
                shape = RoundedCornerShape(12.dp)
            )
            Row(modifier = Modifier.padding(20.dp).fillMaxWidth(),) {
                Button(
                    modifier = Modifier.weight(20f).width(150.dp).height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Blue40,
                        contentColor = White
                    ),
                    onClick = {
                        if (password.value == "admin") {
                            onLoginClick()
                        } else {
                            openDialog.value = true
                        }
                    }
                ) {
                    Text(text = "LOG IN",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W800,
                    )
                }
            }
        }

        // AlertDialog for login failure
        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = { openDialog.value = false },
                confirmButton = {
                    Button(onClick = {
                        password.value = ""
                        openDialog.value = false
                    }) {
                        Text(text = "OK")
                    }
                },
                title = { Text(text = "Login Gagal") },
                text = { Text(text = "Password salah") }
            )
        }
    }
}

fun roundedOutlinedTextFieldModifier(cornerRadius: Dp) = Modifier
    .clip(RoundedCornerShape(cornerRadius))

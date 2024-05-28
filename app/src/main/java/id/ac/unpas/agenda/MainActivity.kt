package id.ac.unpas.agenda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import id.ac.unpas.agenda.ui.screens.MainScreen
import id.ac.unpas.agenda.ui.theme.AgendaTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AgendaTheme {
                // A surface container using the 'background' color from the theme
                MainScreen(onExitClick = {
                    finish()
                })
            }
        }
    }
}
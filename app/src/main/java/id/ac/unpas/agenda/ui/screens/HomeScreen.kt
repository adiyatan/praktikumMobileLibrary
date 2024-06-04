package id.ac.unpas.agenda.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import id.ac.unpas.agenda.R

@Composable
fun HomeScreen(navController: NavController, modifier: Modifier = Modifier, onLihat: () -> Unit) {
    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Image(painter = painterResource(id = R.drawable.header), contentDescription = "Header", modifier = Modifier
            .width(500.dp).height(200.dp),)
        Box(
            modifier = Modifier
                .padding(20.dp).fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "BUKU TERPOPULER KAMI",
                fontSize = 24.sp,
                fontWeight = FontWeight.W800,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly, // Mengatur jarak merata di antara gambar
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.populer1),
                contentDescription = "Populer 1",
                modifier = Modifier.width(100.dp).height(100.dp) // Sesuaikan lebar sesuai kebutuhan
            )
            Image(
                painter = painterResource(id = R.drawable.populer2),
                contentDescription = "Populer 2",
                modifier = Modifier.width(100.dp).height(100.dp) // Sesuaikan lebar sesuai kebutuhan
            )
            Image(
                painter = painterResource(id = R.drawable.populer3),
                contentDescription = "Populer 3",
                modifier = Modifier.width(100.dp).height(100.dp) // Sesuaikan lebar sesuai kebutuhan
            )
        }


//        Button(onClick = {
//            navController.navigate(NavScreen.Add.route)
//        }, modifier = Modifier.fillMaxWidth()) {
//            Text(text = "Tambah")
//        }
//        Button(onClick = {
//            onLihat()
//        }, modifier = Modifier.fillMaxWidth()) {
//            Text(text = "Lihat")
//        }
    }
}
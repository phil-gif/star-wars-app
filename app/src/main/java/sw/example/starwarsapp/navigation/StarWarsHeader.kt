package sw.example.starwarsapp.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import sw.example.starwarsapp.R

@Composable
fun StarWarsHeader(
    modifier: Modifier = Modifier,
    showBackButton: Boolean = false,
    onBackClicked: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {


        if (showBackButton) {
            IconButton(
                onClick = onBackClicked,
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Zurück",
                    tint = Color.Yellow,
                )
            }
        }

        Image(
            painter = painterResource(id = R.drawable.starwars),
            contentDescription = "Star Wars Symbol",
            modifier = Modifier
                .weight(if (showBackButton) 10f else 8f)
                .fillMaxHeight(),
            contentScale = ContentScale.Fit
        )


        if (showBackButton) Spacer(modifier = Modifier.weight(1f))
    }
}

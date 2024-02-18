package sw.example.starwarsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import sw.example.starwarsapp.classes.Person
import sw.example.starwarsapp.classes.Planet
import sw.example.starwarsapp.classes.Starship
import sw.example.starwarsapp.services.CacheManager
import sw.example.starwarsapp.ui.theme.StarWarsAppTheme
import sw.example.starwarsapp.services.StarWarsAPIService


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StarWarsAppTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
                    val starWarsAPIService = StarWarsAPIService()
                    var items = remember { mutableStateListOf<Any>() }

                    MainScreen(starWarsAPIService, items)
                }
            }
        }
    }
}

@Composable
fun MainScreen(starWarsAPIService: StarWarsAPIService, items: SnapshotStateList<Any>) {
    Column {
        StarWarsHeader()
        Divider(color = Color.White, thickness = 1.dp)
        SelectionButtons(starWarsAPIService, items)
        ItemsSlider(items)
    }
}


@Composable
fun SelectionButtons(starWarsAPIService: StarWarsAPIService, items: SnapshotStateList<Any>) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = {
                // Überprüfe zuerst, ob die Daten im Cache vorhanden sind
                val cachedPeople = CacheManager.get("people")
                if (cachedPeople != null) {
                    items.clear()
                    items.addAll(cachedPeople as List<Person>)
                    println("Personen aus dem Cache geladen")
                } else {
                    starWarsAPIService.fetchPeopleData { peopleList, error ->
                        if (error != null) {
                            println("Fehler beim Abrufen der Daten: ${error.message}")
                        } else {
                            items.clear()
                            peopleList?.let {
                                items.addAll(it)
                                CacheManager.put("people", it)
                                println("Personen vom Netzwerk abgerufen und im Cache gespeichert")
                            }
                        }
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text("Personen", color = Color.Black)
        }
        Button(
            onClick = {
                val cachedPlanets = CacheManager.get("planets")
                if (cachedPlanets != null) {
                    items.clear()
                    items.addAll(cachedPlanets as List<Planet>)
                    println("Planeten aus dem Cache geladen")
                } else {
                    starWarsAPIService.fetchPlanetsData { planetList, error ->
                        if (error != null) {
                            println("Fehler beim Abrufen der Daten: ${error.message}")
                        } else {
                            items.clear()
                            planetList?.let {
                                items.addAll(it)
                                CacheManager.put("planets", it)
                                println("Planeten vom Netzwerk abgerufen und im Cache gespeichert")
                            }
                        }
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text("Planeten", color = Color.Black)
        }

        Button(
            onClick = {
                val cachedStarships = CacheManager.get("starships")
                if (cachedStarships != null) {
                    items.clear()
                    items.addAll(cachedStarships as List<Starship>)
                    println("Starships aus dem Cache geladen")
                } else {
                    starWarsAPIService.fetchStarshipsData { starshipList, error ->
                        if (error != null) {
                            println("Fehler beim Abrufen der Daten: ${error.message}")
                        } else {
                            items.clear()
                            starshipList?.let {
                                items.addAll(it)
                                CacheManager.put("starships", it)
                                println("Starships vom Netzwerk abgerufen und im Cache gespeichert")
                            }
                        }
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text("Starships", color = Color.Black)
        }
    }
}

@Composable
fun ItemsSlider(items: List<Any>) {
    LazyRow(modifier = Modifier.padding(8.dp)) {
        items(items.size) { index ->
            val item = items[index]
            Card(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .width(200.dp),
                shape = RoundedCornerShape(8.dp), // Abgerundete Ecken
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), // Subtiler Schatten
                colors = CardDefaults.cardColors(containerColor = Color(0xFF333333)) // Dunkelgrau, passend zum Star-Wars-Thema
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp) // Schwarzer Hintergrund für den Inhalt
                ) {
                    when (item) {
                        is Person -> {
                            ItemTitle(text = item.name)
                            ItemAttribute(label = "Height", value = item.height)
                            ItemAttribute(label = "Gender", value = item.gender)
                        }

                        is Planet -> {
                            ItemTitle(text = item.name)
                            ItemAttribute(label = "Climate", value = item.climate)
                            ItemAttribute(label = "Population", value = item.population)
                        }

                        is Starship -> {
                            ItemTitle(text = item.name)
                            ItemAttribute(label = "Model", value = item.model)
                            ItemAttribute(label = "Manufacturer", value = item.manufacturer)
                        }
                    }
                    Button(
                        onClick = {
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Text("Details", color = Color.Black)
                    }
                }
            }
        }
    }
}

@Composable
fun ItemTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium.copy(
            color = Color.Yellow,
            fontSize = 20.sp // Setze die Schriftgröße auf 20sp
        ),
        modifier = Modifier.padding(bottom = 4.dp)
    )
}


@Composable
fun ItemAttribute(label: String, value: String) {
    Row(modifier = Modifier.padding(vertical = 2.dp)) {
        Text(
            text = "$label: ",
            color = Color.LightGray,
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = value,
            color = Color.White,
            style = MaterialTheme.typography.bodySmall
        )
    }
}


@Composable
fun StarWarsHeader(modifier: Modifier = Modifier) {
    // Adjust the size of the Image here. Replace "R.drawable.star_wars_symbol" with your actual drawable resource name.
    Image(
        painter = painterResource(id = R.drawable.starwars),
        contentDescription = "Star Wars Symbol",
        modifier = modifier
            .fillMaxWidth() // Image will fill the width of its container
            .height(60.dp), // Limiting the height of the Image
        contentScale = ContentScale.Fit // Adjust the scaling to fit within the specified dimensions
    )
}

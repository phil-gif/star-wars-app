package sw.example.starwarsapp.subpages

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import sw.example.starwarsapp.classes.Person
import sw.example.starwarsapp.classes.Planet
import sw.example.starwarsapp.classes.Starship
import sw.example.starwarsapp.navigation.StarWarsHeader
import sw.example.starwarsapp.ui.theme.StarWarsAppTheme

class DetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val item = intent.getSerializableExtra("item")
        setContent {
            StarWarsAppTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
                    if (item != null) {
                        DetailsScreen(item)
                    }else{
                        //TODO implement not available screen
                    }
                }
            }
        }
    }
}

@Composable
fun DetailsScreen(item: Any) {
    val activity = LocalContext.current as? Activity
    Column {
       StarWarsHeader(
           showBackButton = true,
           onBackClicked = {
               activity?.finish()
           })
        Divider(color = Color.White, thickness = 1.dp)
        ItemDetails(item = item)

    }
}


@Composable
fun ItemDetails(item: Any) {
    Column(modifier = Modifier.padding(16.dp)) {
        when (item) {
            is Person -> {
                Text("Person Details", style = MaterialTheme.typography.headlineSmall.copy(color = Color.Yellow))
                Spacer(Modifier.height(8.dp))
                DetailAttribute("Name", item.name)
                DetailAttribute("Height", item.height)
                DetailAttribute("Mass", item.mass)
                DetailAttribute("Hair Color", item.hair_color)
                DetailAttribute("Skin Color", item.skin_color)
                DetailAttribute("Eye Color", item.eye_color)
                DetailAttribute("Birth Year", item.birth_year)
                DetailAttribute("Gender", item.gender)
                DetailAttribute("Homeworld", item.homeworld)
                DetailListAttribute("Films", item.films)
                DetailListAttribute("Species", item.species)
                DetailListAttribute("Vehicles", item.vehicles)
                DetailListAttribute("Starships", item.starships)
                DetailAttribute("Created", item.created)
                DetailAttribute("Edited", item.edited)
                DetailAttribute("URL", item.url)
            }
            is Planet -> {
                Text("Planet Details", style = MaterialTheme.typography.headlineSmall.copy(color = Color.Yellow))
                Spacer(Modifier.height(8.dp))
                DetailAttribute("Name", item.name)
                DetailAttribute("Rotation Period", item.rotation_period)
                DetailAttribute("Orbital Period", item.orbital_period)
                DetailAttribute("Diameter", item.diameter)
                DetailAttribute("Climate", item.climate)
                DetailAttribute("Gravity", item.gravity)
                DetailAttribute("Terrain", item.terrain)
                DetailAttribute("Surface Water", item.surface_water)
                DetailAttribute("Population", item.population)
                DetailListAttribute("Residents", item.residents)
                DetailListAttribute("Films", item.films)
                DetailAttribute("Created", item.created)
                DetailAttribute("Edited", item.edited)
                DetailAttribute("URL", item.url)
            }
            is Starship -> {
                Text("Starship Details", style = MaterialTheme.typography.headlineSmall.copy(color = Color.Yellow))
                Spacer(Modifier.height(8.dp))
                DetailAttribute("Name", item.name)
                DetailAttribute("Model", item.model)
                DetailAttribute("Manufacturer", item.manufacturer)
                DetailAttribute("Cost in Credits", item.cost_in_credits)
                DetailAttribute("Length", item.length)
                DetailAttribute("Max Atmosphering Speed", item.max_atmosphering_speed)
                DetailAttribute("Crew", item.crew)
                DetailAttribute("Passengers", item.passengers)
                DetailAttribute("Cargo Capacity", item.cargo_capacity)
                DetailAttribute("Consumables", item.consumables)
                DetailAttribute("Vehicle Class", item.vehicle_class)
                DetailListAttribute("Pilots", item.pilots)
                DetailListAttribute("Films", item.films)
                DetailAttribute("Created", item.created)
                DetailAttribute("Edited", item.edited)
                DetailAttribute("URL", item.url)
            }
        }
    }
}

@Composable
fun DetailAttribute(label: String, value: String) {
    Text("$label: $value", style = MaterialTheme.typography.bodyLarge.copy(color = Color.White), modifier = Modifier.padding(2.dp))
}

@Composable
fun DetailListAttribute(label: String, values: List<String>) {
    //TODO Need to implement API calls on list items
    Text("$label:", style = MaterialTheme.typography.bodyLarge.copy(color = Color.White), modifier = Modifier.padding(2.dp))
    values.forEach { value ->
        Text("- $value", style = MaterialTheme.typography.bodyMedium.copy(color = Color.LightGray), modifier = Modifier.padding(start = 16.dp, bottom = 2.dp))
    }
}


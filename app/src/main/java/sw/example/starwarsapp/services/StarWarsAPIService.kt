package sw.example.starwarsapp.services

import retrofit2.Call
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import sw.example.starwarsapp.classes.Person
import sw.example.starwarsapp.classes.Planet
import sw.example.starwarsapp.classes.Starship

interface StarWarsAPI {
    @GET("people/")
    fun getPeopleData(): Call<PeopleResponse>
}
interface StarshipsAPI {
    @GET("starships/")
    fun getStarshipsData(): Call<StarshipsResponse>
}

interface PlanetsAPI {
    @GET("planets/")
    fun getPlanetsData(): Call<PlanetsResponse>
}

class StarWarsAPIService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://swapi.dev/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val starshipsAPI = retrofit.create(StarshipsAPI::class.java)
    private val planetsAPI = retrofit.create(PlanetsAPI::class.java)
    private val starWarsAPI = retrofit.create(StarWarsAPI::class.java)

    fun fetchPeopleData(callback: (List<Person>?, Throwable?) -> Unit) {
        starWarsAPI.getPeopleData().enqueue(object : Callback<PeopleResponse> {
            override fun onResponse(call: Call<PeopleResponse>, response: Response<PeopleResponse>) {
                if (response.isSuccessful) {
                    val peopleResponse = response.body()
                    callback(peopleResponse?.results ?: emptyList(), null)
                } else {
                    callback(null, Exception("Failed to fetch data"))
                }
            }

            override fun onFailure(call: Call<PeopleResponse>, t: Throwable) {
                callback(null, t)
            }
        })
    }

    fun fetchStarshipsData(callback: (List<Starship>?, Throwable?) -> Unit) {
        starshipsAPI.getStarshipsData().enqueue(object : Callback<StarshipsResponse> {
            override fun onResponse(call: Call<StarshipsResponse>, response: Response<StarshipsResponse>) {
                if (response.isSuccessful) {
                    val starshipsResponse = response.body()
                    callback(starshipsResponse?.results ?: emptyList(), null)
                } else {
                    callback(null, Exception("Failed to fetch starships data"))
                }
            }

            override fun onFailure(call: Call<StarshipsResponse>, t: Throwable) {
                callback(null, t)
            }
        })
    }

    fun fetchPlanetsData(callback: (List<Planet>?, Throwable?) -> Unit) {
        planetsAPI.getPlanetsData().enqueue(object : Callback<PlanetsResponse> {
            override fun onResponse(call: Call<PlanetsResponse>, response: Response<PlanetsResponse>) {
                if (response.isSuccessful) {
                    val planetsResponse = response.body()
                    callback(planetsResponse?.results ?: emptyList(), null)
                } else {
                    callback(null, Exception("Failed to fetch planets data"))
                }
            }

            override fun onFailure(call: Call<PlanetsResponse>, t: Throwable) {
                callback(null, t)
            }
        })
    }
}

data class PeopleResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Person>
)

data class StarshipsResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Starship>
)

data class PlanetsResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Planet>
)
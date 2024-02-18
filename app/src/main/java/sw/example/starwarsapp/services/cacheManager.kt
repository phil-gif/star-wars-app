package sw.example.starwarsapp.services

object CacheManager {
    private val cache = mutableMapOf<String, List<Any>>()

    fun put(key: String, data: List<Any>) {
        cache[key] = data
    }

    fun get(key: String): List<Any>? {
        return cache[key]
    }

    fun has(key: String): Boolean {
        return cache.containsKey(key)
    }
}

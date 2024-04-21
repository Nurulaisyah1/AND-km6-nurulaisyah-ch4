import kotlinx.coroutines.*

fun main() {
    // Create a coroutine scope
    runBlocking {
        // Launch a coroutine
        launch {
            // Coroutine code here
            println("Coroutine is running")
        }
    }
}

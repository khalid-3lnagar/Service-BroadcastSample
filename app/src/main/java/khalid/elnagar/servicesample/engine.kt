package khalid.elnagar.servicesample

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

suspend fun backgroundWork(TAG: String, onFinish: () -> Unit = {}, work: (Int) -> Unit) {

    Log.d(TAG, "launching the corourtine at Thread ${currentThreadName()}")
    repeat(50) {
        delay(250)
        Log.d(TAG, "delaying the corourtine at Thread ${currentThreadName()}")

        withContext(Dispatchers.Main) {
            Log.d(TAG, "showing toast at Thread ${currentThreadName()}")
            work(it)
        }
        delay(250)

        Log.d(TAG, " another delaying the corourtine at Thread ${currentThreadName()}")


    }
    Log.d(TAG, " the corourtine is finished at Thread ${currentThreadName()}")
    onFinish()
}

fun currentThreadName() = Thread.currentThread().name
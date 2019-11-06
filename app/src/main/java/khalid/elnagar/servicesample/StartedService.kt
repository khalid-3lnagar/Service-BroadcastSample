package khalid.elnagar.servicesample

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.*

class StartedService : Service() {
    private val TAG = javaClass.name
    private val job = SupervisorJob()
    private var toast: Toast? = null
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand at Thread ${currentTreadName()}")

        showToast()

        return START_STICKY
    }

    private fun showToast() {
        GlobalScope.launch(job) {

            Log.d(TAG, "launching the corourtine at Thread ${currentTreadName()}")
            repeat(1000) {
                delay(250)
                Log.d(TAG, "delaying the corourtine at Thread ${currentTreadName()}")

                withContext(Dispatchers.Main) {
                    Log.d(TAG, "showing toast at Thread ${currentTreadName()}")
                    toast?.cancel()
                    toast = Toast.makeText(this@StartedService, "countind $it", Toast.LENGTH_SHORT)
                    toast?.show()
                }
                delay(250)

                Log.d(TAG, " another delaying the corourtine at Thread ${currentTreadName()}")


            }
        }
    }

    override fun onBind(p0: Intent?): IBinder? = null
    override fun onDestroy() {
        Log.d(TAG, "onDestroy at Thread ${currentTreadName()}")
        job.cancel()
        super.onDestroy()
    }
}

fun currentTreadName() = Thread.currentThread().name
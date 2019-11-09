package khalid.elnagar.servicesample

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class StartedService : Service() {
    private val TAG = javaClass.name
    private val job = SupervisorJob()
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand at Thread ${currentThreadName()}")

        GlobalScope.launch(job) { backgroundWork(TAG) { sendToastBroadcast(it) } }

        return START_STICKY
    }


    override fun onBind(p0: Intent?): IBinder? = null
    override fun onDestroy() {
        Log.d(TAG, "onDestroy at Thread ${currentThreadName()}")
        job.cancel()
        super.onDestroy()
    }
}


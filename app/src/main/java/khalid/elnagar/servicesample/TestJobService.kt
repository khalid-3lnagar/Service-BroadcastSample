package khalid.elnagar.servicesample

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class TestJobService : JobService() {
    private val TAG = javaClass.name
    private val job = SupervisorJob()
    private var toast: Toast? = null
    override fun onStartJob(jobParameters: JobParameters?): Boolean {
        Log.d(TAG, "onStartJob at Thread ${currentThreadName()}")
        val onFinish = { jobFinished(jobParameters, false) }
        GlobalScope.launch(job) { backgroundWork(TAG, onFinish, ::showToast) }

        return true
    }

    private fun showToast(it: Int) {
        toast?.cancel()
        toast = Toast.makeText(this@TestJobService, "counting $it", Toast.LENGTH_SHORT)
        toast?.show()
    }

    override fun onStopJob(jobParameters: JobParameters?): Boolean {
        Log.d(TAG, "onStopJob at Thread ${currentThreadName()}")
        job.cancel()
        return true
    }

}

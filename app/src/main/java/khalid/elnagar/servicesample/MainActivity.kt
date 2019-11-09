package khalid.elnagar.servicesample

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

private const val TEST_JOB_ID = 0

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_start_service.setOnClickListener {

            Intent(this, StartedService::class.java).run(::startService)

        }
        btn_stop_service.setOnClickListener {

            Intent(this, StartedService::class.java).run(::stopService)
        }

        btn_schedule_job.setOnClickListener { scheduleTestJob() }
    }

    private fun scheduleTestJob() {
        val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        ComponentName(this, TestJobService::class.java)
            .let { name -> JobInfo.Builder(TEST_JOB_ID, name) }
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .build()
            .run(jobScheduler::schedule)
    }

}

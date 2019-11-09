package khalid.elnagar.servicesample

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

private const val TEST_JOB_ID = 0

class MainActivity : AppCompatActivity() {
    private val showToastBroadcastReceiver by lazy { ShowToastBroadcastReceiver() }
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
        btn_alarm.setOnClickListener { setToastAlarm() }
        IntentFilter(ACTION_SHOW_TOAST_EVENT)
            .also { filter -> registerReceiver(showToastBroadcastReceiver, filter) }
    }

    private fun setToastAlarm() {

        val pendingIntent = Intent(this, ShowToastBroadcastReceiver::class.java)
            .putExtra(EXTRA_COUNT, ALARM_MANGER_TOAST)
            .let { PendingIntent.getBroadcast(this, 0, it, PendingIntent.FLAG_UPDATE_CURRENT) }

        val alarmTime = SystemClock.elapsedRealtime().plus(5 * 1000)
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.ELAPSED_REALTIME, alarmTime, pendingIntent)
    }


    private fun scheduleTestJob() {
        val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        ComponentName(this, TestJobService::class.java)
            .let { name -> JobInfo.Builder(TEST_JOB_ID, name) }
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .build()
            .run(jobScheduler::schedule)
    }

    override fun onDestroy() {
        unregisterReceiver(showToastBroadcastReceiver)
        super.onDestroy()

    }
}

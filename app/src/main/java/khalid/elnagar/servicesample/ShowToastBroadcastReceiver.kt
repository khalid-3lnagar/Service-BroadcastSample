package khalid.elnagar.servicesample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

const val ACTION_SHOW_TOAST_EVENT = "khalid.elnagar.servicesample.action.SHOW_TOAST"
const val EXTRA_COUNT = "khalid.elnagar.servicesample.extra.EXTRA_COUNT"
const val ALARM_MANGER_TOAST = -1

class ShowToastBroadcastReceiver : BroadcastReceiver() {
    //extra

    //toast
    private var toast: Toast? = null

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val count = intent.getIntExtra(EXTRA_COUNT, 0)
        if (count == ALARM_MANGER_TOAST)
            alarmToast(context)
        else
            showToast(context, count)

    }

    private fun alarmToast(context: Context) {
        GlobalScope.launch {
            withTimeout(3000) {
                backgroundWork("AlarmManger") { showToast(context, it) }
            }
        }
    }

    private fun showToast(context: Context, count: Int) {
        toast?.cancel()
        toast = Toast.makeText(context, "counting.. $count", Toast.LENGTH_SHORT)
        toast?.show()
    }

}

//sending the broadCast
fun Context.sendToastBroadcast(count: Int): Unit = Intent(ACTION_SHOW_TOAST_EVENT)
    .putExtra(EXTRA_COUNT, count)
    .run(::sendBroadcast)

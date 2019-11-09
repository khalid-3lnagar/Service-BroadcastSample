package khalid.elnagar.servicesample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

const val ACTION_SHOW_TOAST_EVENT = "khalid.elnagar.servicesample.action.SHOW_TOAST"
const val EXTRA_COUNT = "khalid.elnagar.servicesample.extra.EXTRA_COUNT"

class ShowToastBroadcastReceiver : BroadcastReceiver() {
    //extra

    //toast
    private var toast: Toast? = null

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val count = intent.getIntExtra(EXTRA_COUNT, 0)
        toast?.cancel()
        toast = Toast.makeText(context, "counting.. $count", Toast.LENGTH_SHORT)
        toast?.show()

    }

}

//sending the broadCast
fun Context.sendToastBroadcast(count: Int): Unit = Intent(ACTION_SHOW_TOAST_EVENT)
    .putExtra(EXTRA_COUNT, count)
    .run(::sendBroadcast)

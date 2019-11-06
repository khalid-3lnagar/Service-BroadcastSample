package khalid.elnagar.servicesample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

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
    }

}

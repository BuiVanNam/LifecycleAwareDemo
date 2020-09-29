package com.nambv.demo.lifecycleaware

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NetworkConnectLifecycle(this, lifecycle).run {
            networkChange = { handleNetworkChange(it) }
        }
    }

    private fun handleNetworkChange(connected: Boolean) {
        runOnUiThread {
            if (connected) {
                Snackbar.make(content_main, R.string.network_connect, Snackbar.LENGTH_LONG).show()
                status_network.run {
                    setText(R.string.status_network_connect)
                    setCompoundDrawablesWithIntrinsicBounds(
                        ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_emotions_happy),
                        null,
                        null,
                        null
                    )
                }
            } else {
                Snackbar.make(content_main, R.string.network_disconnect, Snackbar.LENGTH_LONG)
                    .show()
                status_network.run {
                    setText(R.string.status_network_disconnect)
                    setCompoundDrawablesWithIntrinsicBounds(
                        ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_emotions_sad),
                        null,
                        null,
                        null
                    )
                }
            }
        }
    }

}
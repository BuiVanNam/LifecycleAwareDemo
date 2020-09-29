package com.nambv.demo.lifecycleaware

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class NetworkConnectLifecycle(private val context: Context, private val lifecycle: Lifecycle) :
    LifecycleObserver,
    ConnectivityManager.NetworkCallback() {

    private val mConnectActivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    var networkChange: ((Boolean) -> Unit)? = null

    init {
        lifecycle.addObserver(this)
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        networkChange?.invoke(true)
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        networkChange?.invoke(false)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        if (!context.isOnline()) {
            networkChange?.invoke(false)
        }
        mConnectActivityManager.registerDefaultNetworkCallback(this)
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        mConnectActivityManager.unregisterNetworkCallback(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        lifecycle.removeObserver(this)
    }

}
package com.group.github

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.group.base.ktor.ServerFactory
import com.group.base.ktor.ServerFactory.SERVER_SERVICE_HTTP_PORT
import com.group.base.modular.github.server.ktor.routes
import io.ktor.server.engine.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.logging.Logger

class KtorMockService : Service() {
    private val logger = Logger.getLogger("KtorService")
    private val coroutineContext = Dispatchers.IO
    private var HTTP_PORT = 5555
    private val server : ApplicationEngine by lazy {
        ServerFactory.getServer(HTTP_PORT)
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.e("KtorMockService", "Show onBind Intent : $intent ")
        logger.info("Show onBind Intent : $intent ")
        intent?.let {
            Log.e("KtorService", "Show onBind Intent extra  : ${it.extras} ")
            it.extras?.let { bundle ->
                val http_prot = bundle.getInt(SERVER_SERVICE_HTTP_PORT)
                Log.e("KtorService", "Show onBind Intent extra port : $http_prot ")
            }
        }
        //        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return null
    }

    override fun onCreate() {
        Log.e("KtorService", "onCreate")
        logger.info("onCreate")
        super.onCreate()
    }

    override fun onStart(intent: Intent?, startId: Int) {
        Log.e("KtorService", "Show onStart Intent : $intent ")
        intent?.let {
            Log.e("KtorService", "Show onStart Intent extra  : ${it.extras} ")
            it.extras?.let { bundle ->
                HTTP_PORT = bundle.getInt(SERVER_SERVICE_HTTP_PORT)
                Log.e("KtorService", "Show onStart Intent extra port : $HTTP_PORT ")
            }
        }
        super.onStart(intent, startId)
        CoroutineScope(coroutineContext).launch {
            logger.info("Starting server...")
            server.application.apply {
                routes()
            }
            server.start(wait = true)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("KtorService", "Show onStartCommand Intent : $intent ")
        logger.info("Show onStartCommand Intent : $intent ")
        intent?.let {
            Log.e("KtorService", "Show onStartCommand Intent extra  : ${it.extras} ")
            it.extras?.let { bundle ->
                HTTP_PORT = bundle.getInt(SERVER_SERVICE_HTTP_PORT)
                Log.e("KtorService", "Show onStartCommand Intent extra port : $HTTP_PORT ")
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        Log.e("KtorService", "Show onDestroy ")
        logger.info("Stopping server")
        server.stop(1_000, 2_000)
        super.onDestroy()
    }
}
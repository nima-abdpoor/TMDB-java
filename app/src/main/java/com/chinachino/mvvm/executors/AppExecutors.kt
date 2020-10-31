package com.chinachino.mvvm.executors

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

object AppExecutors {
    val executorService: ScheduledExecutorService = Executors.newScheduledThreadPool(3)
}
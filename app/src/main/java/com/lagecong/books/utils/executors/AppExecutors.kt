package com.lagecong.books.utils.executors

import android.os.Handler
import android.os.Looper
import androidx.annotation.VisibleForTesting
import java.util.concurrent.Executor

/**
 * Created by Andi Tenroaji Ahmad on 9/24/2019.
 */
class AppExecutors @VisibleForTesting
private constructor(private val mDiskIO: Executor, private val mMainThread: Executor) {

    constructor() : this(DiskIOThreadExecutor(), MainThreadExecutor()) {}

    fun diskIO(): Executor {
        return mDiskIO
    }

    fun mainThread(): Executor {
        return mMainThread
    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}

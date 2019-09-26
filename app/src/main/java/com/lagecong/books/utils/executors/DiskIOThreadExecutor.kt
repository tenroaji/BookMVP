package com.lagecong.books.utils.executors

import java.util.concurrent.Executor
import java.util.concurrent.Executors


/**
 * Created by Andi Tenroaji Ahmad on 9/24/2019.
 */
class DiskIOThreadExecutor : Executor {

    private val mDiskIO: Executor

    init {
        mDiskIO = Executors.newSingleThreadExecutor()
    }

    override fun execute(command: Runnable) {
        mDiskIO.execute(command)
    }
}

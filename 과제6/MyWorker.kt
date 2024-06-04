package com.example.s2071321

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.io.File

class MyWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        val file = File(applicationContext.getExternalFilesDir(null), "init_value.txt")
        val currentValue = if (file.exists()) {
            file.readText().toIntOrNull() ?: 0
        } else {
            0
        }

        val newValue = currentValue + 1

        val workerFile = File(applicationContext.getExternalFilesDir(null), "worker.txt")
        workerFile.writeText(newValue.toString())

        return Result.success()
    }
}
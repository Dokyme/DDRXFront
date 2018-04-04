package com.ddrx.ddrxfront.Controller

import android.content.Context
import android.os.Handler
import android.os.Message
import android.util.Log
import com.ddrx.ddrxfront.Model.MemoryMasterDatabase


/**
 * Created by dokym on 2018/4/4.
 */
class TrainingController(var handler: Handler, var context: Context) {

    companion object {
        val GET_MEMORY_CARDS = 0
    }

    fun getNeedTrainingMemoryCard(cwId: Long) {
        Thread(Runnable {
            val msg = Message()
            msg.what = GET_MEMORY_CARDS
            msg.obj = MemoryMasterDatabase.getInstance(context)
                    .cardTrainingRecordDAO
                    .queryNeedTrainingMemoryCardByCWId(cwId)
            handler.sendMessage(msg)
        }).start()
    }

    fun incrementTrainingCount(cwId: Long, ccId: Long) {
        Log.d("ddrx", "cwId:" + cwId + "  ccId:" + ccId)
        Thread(Runnable {
            val training = MemoryMasterDatabase.getInstance(context)
                    .cardTrainingRecordDAO
                    .queryTrainRecord(cwId, ccId)
            training.training_count++
            MemoryMasterDatabase.getInstance(context)
                    .cardTrainingRecordDAO
                    .updateCardsTrainingRecords(training)
        }).start()
    }
}
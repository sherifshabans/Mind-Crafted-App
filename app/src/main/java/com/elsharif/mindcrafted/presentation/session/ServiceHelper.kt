package com.elsharif.mindcrafted.presentation.session

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import com.elsharif.mindcrafted.MainActivity
import com.elsharif.mindcrafted.util.Constants.CLICK_REQUEST_CODE

object ServiceHelper {


    fun clickPendingIntent(context: Context):PendingIntent{
        val deepLingIntent =Intent(
            Intent.ACTION_VIEW,
            "mind_crafted://dashboard/session".toUri(),
            context,
            MainActivity::class.java
        )

        return TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(deepLingIntent)
            getPendingIntent(
                CLICK_REQUEST_CODE,
                PendingIntent.FLAG_IMMUTABLE
            )
        }
    }


    fun triggerForegroundService(context: Context,action:String){
        Intent(context,StudySessionTimerServices::class.java).apply {
             this.action =action
            context.stopService(this)
        }
    }
}
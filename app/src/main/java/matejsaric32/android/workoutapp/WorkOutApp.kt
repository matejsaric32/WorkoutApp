package matejsaric32.android.workoutapp

import android.app.Application

class WorkOutApp : Application(){
    val db by lazy { HistoryDatabase.getInstance(this) }
}
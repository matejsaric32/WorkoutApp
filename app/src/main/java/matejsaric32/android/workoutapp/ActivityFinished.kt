package matejsaric32.android.workoutapp

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import matejsaric32.android.workoutapp.databinding.ActivityExerciseBinding
import matejsaric32.android.workoutapp.databinding.ActivityFinishedBinding
import java.util.*

class ActivityFinished : AppCompatActivity() {

    private var binding: ActivityFinishedBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishedBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarFinnishActivity)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolbarFinnishActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.btnFinish?.setOnClickListener {
            finish()
        }

        val dao = (application as WorkOutApp).db.getHistoryDao()
        binding?.btnFinish ?.setOnClickListener {
            addToDataBase(dao)
        }

    }

    private fun addToDataBase(historyDao: HistoryDao) {

        val c = Calendar.getInstance()
        val dateTime = c.time
        Log.i("Date:", "addToDataBase: $dateTime")

        val dateFormatter = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date = dateFormatter.format(dateTime)
        Log.e("DateFormatted:", "addToDataBase: $date")

        lifecycleScope.launch{
            historyDao.insertHistory(HistoryEntity(date))
            Log.e("DateFormatted:", "addToDataBase: $date")
        }
    }
}
package matejsaric32.android.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import matejsaric32.android.workoutapp.databinding.ActivityHistoryBinding

class History_Activity : AppCompatActivity() {

    private var binding: ActivityHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarHistoryActivity)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "HISTORY"
        }

        binding?.toolbarHistoryActivity?.setNavigationOnClickListener { onBackPressed() }

        getAllCompletedDates((application as WorkOutApp).db.getHistoryDao())

    }

    private fun getAllCompletedDates(historyDao: HistoryDao) {
        lifecycleScope.launch{
            historyDao.getAllHistory().collect {
                allDatesList ->

                if (allDatesList.isEmpty()){
                    binding?.rvHistory?.visibility = View.INVISIBLE
                    binding?.tvHistory?.visibility = View.INVISIBLE
                    binding?.tvNoDataAvailable?.visibility = View.VISIBLE
                } else {
                    binding?.rvHistory?.visibility = View.VISIBLE
                    binding?.tvHistory?.visibility = View.VISIBLE
                    binding?.tvNoDataAvailable?.visibility = View.INVISIBLE

                    binding?.rvHistory?.layoutManager = LinearLayoutManager(this@History_Activity)

                    val dates = ArrayList<String>()

                    for (date in allDatesList){
                        dates.add(date.date)
                    }

                    val historyAdapter = HistoryAdapter(dates)
                    binding?.rvHistory?.adapter = historyAdapter

                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
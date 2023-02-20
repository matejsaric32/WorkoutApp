package matejsaric32.android.workoutapp

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import matejsaric32.android.workoutapp.databinding.ActivityExerciseBinding
import matejsaric32.android.workoutapp.databinding.ActivityMainBinding
import matejsaric32.android.workoutapp.databinding.DialogCustomBackToStartBinding
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding: ActivityExerciseBinding? = null

    private var restTimer : CountDownTimer? = null
    private var restProgress = 0

    private var exerciseTimer : CountDownTimer? = null
    private var exerciseProgress = 0

    private var exerciseList: ArrayList<Exercise>? = null
    private var currentExercisePosition = -1

    private var tts : TextToSpeech? = null
    private var player: MediaPlayer? = null

    private var exerciseAdapter: ExerciseStatusAdapter? = null

    private var restTimeDuration : Long = 1
    private var exerciseTimeDuration : Long = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        exerciseList = Constants.defaultExerciseList()
        tts = TextToSpeech(this, this)


        binding?.toolbarExercise?.setNavigationOnClickListener {
            Log.i("ExerciseActivity", "Back button clicked")
            customDialogForBackButton()
        }
        binding?.tvUpcomingExerciseTitle?.text = exerciseList!![currentExercisePosition + 1].getName()

        setRestProgressBar()
        setUpExerciseStatusRecyclerView()
    }

    private fun customDialogForBackButton(){
        val customDialog = Dialog(this)
        val dialogBinding = DialogCustomBackToStartBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.tvYes.setOnClickListener {
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.tvNo.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }


    private fun setUpExerciseStatusRecyclerView() {
        binding?.rvExerciseStatus?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter = exerciseAdapter
    }


    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
               Log.i("TTS", "The language specified is not supported!")
            }
        } else {
            Log.i("TTS", "Initialization failed!")
        }
    }

    private fun speakOut(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun setRestProgressBar() {
        binding?.progressBar?.progress = restProgress
        restTimer = object : CountDownTimer(restTimeDuration * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter?.notifyDataSetChanged()
                setUpExerciseView()
            }
        }.start()
    }

    private fun setExerciseProgressBar() {
        binding?.progressBarExercise?.progress = exerciseProgress
        exerciseTimer = object : CountDownTimer(exerciseTimeDuration * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding?.progressBarExercise?.progress = 30 - exerciseProgress
                binding?.tvTimerExercise?.text = (30 - exerciseProgress).toString()
            }

            override fun onFinish() {
                exerciseList!![currentExercisePosition].setIsSelected(false)
                exerciseList!![currentExercisePosition].setIsCompleted(true)
                exerciseAdapter?.notifyDataSetChanged()
                setUpExerciseView()
               if (currentExercisePosition < exerciseList?.size!! - 1) {
                   setUpRestView()
               } else {
                   finish()
                   val intent = Intent(this@ExerciseActivity, ActivityFinished::class.java)
                   startActivity(intent)
               }
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }

        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        if (tts != null) {
            tts?.stop()
            tts?.shutdown()
        }

        if (player != null) {
            player?.stop()
        }
    }

    private fun setUpRestView() {

        try {
            val soundURI = Uri.parse(
                "android.resource://matejsaric32.android.workoutapp/" + R.raw.app_src_main_res_raw_press_start)

            player = MediaPlayer.create(applicationContext, soundURI)
            player?.isLooping = false
            player?.start()

        }catch (e: Exception) {
            e.printStackTrace()
        }

        binding?.flTimer?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE

        binding?.flTimerExercise?.visibility = View.INVISIBLE
        binding?.tvExerciseTitle?.visibility = View.INVISIBLE
        binding?.ivExercisePicture?.visibility = View.INVISIBLE

        binding?.tvUpcomingExerciseLabel?.visibility = View.VISIBLE
        binding?.tvUpcomingExerciseTitle?.visibility = View.VISIBLE


        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }

        binding?.tvUpcomingExerciseTitle?.text = exerciseList!![currentExercisePosition + 1].getName()


        setRestProgressBar()
    }

    private fun setUpExerciseView() {
        binding?.flTimer?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE

        binding?.flTimerExercise?.visibility = View.VISIBLE
        binding?.tvExerciseTitle?.visibility = View.VISIBLE
        binding?.ivExercisePicture?.visibility = View.VISIBLE

        binding?.tvUpcomingExerciseLabel?.visibility = View.INVISIBLE
        binding?.tvUpcomingExerciseTitle?.visibility = View.INVISIBLE

        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

//        speakOut(exerciseList!![currentExercisePosition].getName())

        binding?.ivExercisePicture?.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding?.tvExerciseTitle?.text = exerciseList!![currentExercisePosition].getName()

        setExerciseProgressBar()
    }
}
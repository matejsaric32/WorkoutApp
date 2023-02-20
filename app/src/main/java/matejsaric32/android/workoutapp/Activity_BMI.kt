package matejsaric32.android.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import matejsaric32.android.workoutapp.databinding.ActivityBmiBinding
import java.math.BigDecimal

class Activity_BMI : AppCompatActivity() {

    private var binding: ActivityBmiBinding? = null

    companion object{
        const val METRIC_UNITS_VIEW = "METRIC_UNITS_VIEW"
        const val IMPERIAL_UNITS_VIEW = "IMPERIAL_UNITS_VIEW"
    }

    private var currentVisibleView: String = METRIC_UNITS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBMIActivity)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "BMI calculator"
        }
        binding?.toolbarBMIActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.btnCalculateUnits?.setOnClickListener {
           calculateBMI()
        }

        makeVisibleMetricUnitsView()

        binding?.rgUnits?.setOnCheckedChangeListener{ _, checkedId ->
            if (checkedId == binding?.rbMetricUnits?.id) {
                makeVisibleMetricUnitsView()
            } else {
                makeVisibleImperialUnitsView()
            }

        }
    }

    fun calculateBMI() {
        if (validateMetricUnits()) {
            val heightValue: Float = binding?.etHeightMetricUnit?.text.toString().toFloat() / 100
            val weightValue: Float = binding?.etWeightMetricUnit?.text.toString().toFloat()

            val bmi = weightValue / (heightValue * heightValue)

            displayBMIResult(bmi)
        } else {
            if (validateImperialUnits()){
                val heightValueInches: Float = binding?.etHeightInchesImperialUnit?.text.toString().toFloat() * 2.54.toFloat()
                val heightValueFoot: Float = binding?.etHeightImperialUnit?.text.toString().toFloat() * 30.48.toFloat()
                val weightValue: Float = binding?.etWeightImperialUnit?.text.toString().toFloat() / 2.2.toFloat()


                val height = (heightValueInches + heightValueFoot)/100
                val bmi = weightValue / (height * height)

                Log.i("Activity_BMI", "heightValueInches: $heightValueInches " +
                        "heightValueFoot: $heightValueFoot height: $height weightValue: $weightValue bmi: $bmi")

                displayBMIResult(bmi)
            }else{
                Toast.makeText(this, "Please enter valid values", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun makeVisibleMetricUnitsView() {
        currentVisibleView = METRIC_UNITS_VIEW
        binding?.llMetricUnits?.visibility = android.view.View.VISIBLE
        binding?.llImperialUnits?.visibility = android.view.View.INVISIBLE

        binding?.etHeightImperialUnit?.text?.clear()
        binding?.etHeightInchesImperialUnit?.text?.clear()
        binding?.etWeightImperialUnit?.text?.clear()

        binding?.llDiplayBMIResult?.visibility = android.view.View.INVISIBLE

    }

    fun makeVisibleImperialUnitsView() {
        currentVisibleView = IMPERIAL_UNITS_VIEW
        binding?.llMetricUnits?.visibility = android.view.View.INVISIBLE
        binding?.llImperialUnits?.visibility = android.view.View.VISIBLE

        binding?.etHeightMetricUnit?.text?.clear()
        binding?.etWeightMetricUnit?.text?.clear()

        binding?.llDiplayBMIResult?.visibility = android.view.View.INVISIBLE
    }

    fun validateMetricUnits(): Boolean {
        var isValid = true
        if (binding?.etWeightMetricUnit?.text.toString().isEmpty()) {
            isValid = false
        } else if (binding?.etWeightMetricUnit?.text.toString().isEmpty()) {
            isValid = false
        }
        return isValid
    }

    fun validateImperialUnits(): Boolean {
        var isValid = true
        if (binding?.etHeightImperialUnit?.text.toString().isEmpty()) {
            isValid = false
        } else if (binding?.etHeightInchesImperialUnit?.text.toString().isEmpty()) {
            isValid = false
        } else if (binding?.etWeightImperialUnit?.text.toString().isEmpty()) {
            isValid = false
        }
        return isValid
    }

    fun displayBMIResult(bmi : Float){

        binding?.llDiplayBMIResult?.visibility = android.view.View.VISIBLE
        binding?.tvBMIValue?.text =
            BigDecimal(bmi.toDouble()).setScale(2, BigDecimal.ROUND_HALF_EVEN).toString()

        if (bmi < 15f) {
            binding?.tvBMIType?.text = "Very severely underweight"
            binding?.tvBMIDescription?.text = "You are very severely underweight, you should see a doctor immediately."
        }else if (bmi < 16f) {
            binding?.tvBMIType?.text = "Severely underweight"
            binding?.tvBMIDescription?.text = "You are severely underweight, you should see a doctor immediately or watch on your ."
        } else if (bmi < 18.5f) {
            binding?.tvBMIType?.text = "Underweight"
            binding?.tvBMIDescription?.text = "You are underweight, you should have bigger daily caloric intake."
        } else if (bmi < 25) {
            binding?.tvBMIType?.text = "Normal"
            binding?.tvBMIDescription?.text = "You are normal, keep it up."
        } else if (bmi < 30) {
            binding?.tvBMIType?.text = "Overweight"
            binding?.tvBMIDescription?.text = "You are overweight, you should have smaller daily caloric intake."
        } else if (bmi < 35){
            binding?.tvBMIType?.text = "Moderately obese"
            binding?.tvBMIDescription?.text = "You are moderately obese, you should have smaller daily caloric intake and take some activity."
        }else if(bmi < 40) {
            binding?.tvBMIType?.text = "Severely obese"
            binding?.tvBMIDescription?.text = "You are severely obese, you should see a doctor immediately."
        } else {
            binding?.tvBMIType?.text = "Very severely obese"
            binding?.tvBMIDescription?.text = "You are very severely obese, you should see a doctor immediately."
        }

    }
}
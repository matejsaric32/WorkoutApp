package matejsaric32.android.workoutapp

object Constants {

    fun defaultExerciseList() : ArrayList<Exercise>{
        val exerciseList = ArrayList<Exercise>()
        val jumpingJacks = Exercise(1, "Jumping Jacks", R.drawable.exercise_picture_1, false, false)
        val legRaise = Exercise(2, "Leg Raise", R.drawable.exercise_picture_2, false, false)
        val singleLegToeTouch = Exercise(3, "Plank", R.drawable.exercise_picture_3, false, false)
        val burpeeWithPushUp = Exercise(4, "Burpee with Push Up", R.drawable.exercise_picture_4, false, false)
        val singleLegHipRaise = Exercise(5, "Single Leg Hip Raise", R.drawable.exercise_picture_5, false, false)
        val splitSquat = Exercise(6, "Split Squat", R.drawable.exercise_picture_6, false, false)
        val plank = Exercise(7, "Plank", R.drawable.exercise_picture_7, false, false)
        val mountainClimber = Exercise(8, "Mountain Climber", R.drawable.exercise_picture_8, false, false)
        val pushUp = Exercise(9, "Push Up", R.drawable.exercise_picture_9, false, false)
        val squat = Exercise(10, "Squat", R.drawable.exercise_picture_10, false, false)

        exerciseList.add(jumpingJacks)
        exerciseList.add(legRaise)
        exerciseList.add(singleLegToeTouch)
        exerciseList.add(burpeeWithPushUp)
        exerciseList.add(singleLegHipRaise)
        exerciseList.add(splitSquat)
        exerciseList.add(plank)
        exerciseList.add(mountainClimber)
        exerciseList.add(pushUp)
        exerciseList.add(squat)

        return exerciseList
    }
}
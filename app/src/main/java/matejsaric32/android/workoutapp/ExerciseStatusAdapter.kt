package matejsaric32.android.workoutapp

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import matejsaric32.android.workoutapp.databinding.ActivityExerciseBinding
import matejsaric32.android.workoutapp.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter(val items: ArrayList<Exercise>) : RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemExerciseStatusBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvItem = binding.tvItem
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemExerciseStatusBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: Exercise = items[position]
        Log.i("ExerciseStatusAdapter", model.getId().toString())
        holder.tvItem.text = (position + 1).toString()

        when{
            model.getIsSelected()-> {
                holder.tvItem.background =
                    holder.tvItem.context.getDrawable(R.drawable.item_circular_color_accent_bg_selected)
                holder.tvItem.setTextColor(
                    holder.tvItem.context.getColor(R.color.bg_color))
            }
            model.getIsCompleted()-> {
                holder.tvItem.background =
                    holder.tvItem.context.getDrawable(R.drawable.item_circular_color_accent_bg_complited)
                holder.tvItem.setTextColor(
                    holder.tvItem.context.getColor(R.color.white))
            }
            else -> {
                holder.tvItem.background =
                    holder.tvItem.context.getDrawable(R.drawable.item_circular_color_accent_bg)
                holder.tvItem.setTextColor(
                    holder.tvItem.context.getColor(R.color.white))
            }
        }

//        holder.tvItem.text = model.getId().toString()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

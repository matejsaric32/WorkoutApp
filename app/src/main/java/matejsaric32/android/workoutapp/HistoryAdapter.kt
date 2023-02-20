package matejsaric32.android.workoutapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import matejsaric32.android.workoutapp.databinding.HistoryEntryBinding

class HistoryAdapter(val items : ArrayList<String>):
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(bind: HistoryEntryBinding): RecyclerView.ViewHolder(bind.root) {
        val tvItem = bind?.tvItem
        val llHistoryEntry = bind?.llHistoryItemMain
        val tvPosistion = bind?.tvPosition
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            HistoryEntryBinding.inflate(
            LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: String = items[position]

        holder.tvItem?.text = model
        holder.tvPosistion?.text = (position + 1).toString()

        if (position % 2 == 0) {
            holder.llHistoryEntry?.setBackgroundColor(
                holder.llHistoryEntry?.context?.getColor(R.color.white)!!
            )
        } else {
            holder.llHistoryEntry?.setBackgroundColor(
                holder.llHistoryEntry?.context?.getColor(R.color.stripped)!!
            )
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


}
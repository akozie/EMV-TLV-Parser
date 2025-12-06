package com.isw.emvtlvparser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.isw.emvtlvparser.R
import com.isw.emvtlvparser.model.TLV
import com.isw.emvtlvparser.utils.Utils.getTagColor


class TLVAdapter(private var items: List<TLV>) : RecyclerView.Adapter<TLVAdapter.TLVViewHolder>() {

    class TLVViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tagText: TextView = view.findViewById(R.id.tagText)
        val lengthText: TextView = view.findViewById(R.id.lengthText)
        val valueText: TextView = view.findViewById(R.id.valueText)
        val interpretationText: TextView = view.findViewById(R.id.interpretationText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TLVViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tlv, parent, false)
        return TLVViewHolder(view)
    }

    override fun onBindViewHolder(holder: TLVViewHolder, position: Int) {
        val node = items[position]
        val color = getTagColor(node.tag)

        // Set colors
        holder.tagText.setTextColor(color)
        holder.lengthText.setTextColor(color)
        holder.valueText.setTextColor(color)
        holder.interpretationText.setTextColor(color)

        // Add prefixes for clarity
        holder.tagText.text = "Tag: ${node.tag}"
        holder.lengthText.text = "Length: ${node.length}"
        holder.valueText.text = "Value: ${node.value}"
        holder.interpretationText.text = "Interpretation: ${node.interpretation.ifEmpty { "---" }}"
    }


    override fun getItemCount() = items.size

    fun updateData(newItems: List<TLV>) {
        items = newItems
        notifyDataSetChanged()
    }
}

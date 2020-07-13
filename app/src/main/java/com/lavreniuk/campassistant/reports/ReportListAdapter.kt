package com.lavreniuk.campassistant.reports

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lavreniuk.campassistant.R

class ReportListAdapter(
    private var reports: List<Report>
) : RecyclerView.Adapter<ReportListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class ViewHolder(
        inflater: LayoutInflater,
        private val parent: ViewGroup
    ) : RecyclerView.ViewHolder(inflater.inflate(R.layout.report_list_item, parent, false))
}

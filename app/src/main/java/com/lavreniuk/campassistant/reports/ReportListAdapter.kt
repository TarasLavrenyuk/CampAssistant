package com.lavreniuk.campassistant.reports

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lavreniuk.campassistant.R

class ReportListAdapter(
    private var reports: List<Report>
) : RecyclerView.Adapter<ReportListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context), parent)

    override fun getItemCount(): Int = reports.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(reports[position])

    class ViewHolder(
        inflater: LayoutInflater,
        private val parent: ViewGroup
    ) : RecyclerView.ViewHolder(inflater.inflate(R.layout.report_list_item, parent, false)) {

        private val reportIcon: ImageView = itemView.findViewById(R.id.report_list_item_report_icon)
        private val reportName: TextView = itemView.findViewById(R.id.report_list_item_report_name)
        private val reportDate: TextView = itemView.findViewById(R.id.report_list_item_report_date)

        fun bind(report: Report) {
            reportIcon.setImageResource(R.drawable.ic_report)
            reportName.text = report.reportName
            reportDate.text = ReportUtils.getReportDate(report.updated)
        }
    }
}

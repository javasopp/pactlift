package com.sopp.pactlift.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sopp.pactlift.R
import com.sopp.pactlift.data.DataManager
import com.sopp.pactlift.model.CheckIn
import com.sopp.pactlift.model.Habit
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RecordsFragment : Fragment() {
    
    private lateinit var dataManager: DataManager
    private lateinit var recordsAdapter: RecordsAdapter
    private lateinit var titleTextView: TextView
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_records, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        dataManager = DataManager.getInstance(requireContext())
        
        titleTextView = view.findViewById(R.id.textViewTitle)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewRecords)
        
        recordsAdapter = RecordsAdapter(getActiveHabitCheckIns())
        
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = recordsAdapter
        
        updateTitle()
    }
    
    private fun updateTitle() {
        val activeHabits = dataManager.getHabits().filter { it.isActive }
        titleTextView.text = "当前活跃习惯记录 (${activeHabits.size}项)"
    }
    
    private fun getActiveHabitCheckIns(): List<CheckInWithHabitName> {
        val checkIns = dataManager.getCheckIns()
        val activeHabits = dataManager.getHabits().filter { it.isActive }
        
        // 只返回活跃习惯的打卡记录
        return checkIns
            .filter { checkIn -> 
                activeHabits.any { it.id == checkIn.habitId } 
            }
            .map { checkIn ->
                val habit = activeHabits.find { it.id == checkIn.habitId }
                CheckInWithHabitName(
                    checkIn = checkIn,
                    habitName = habit?.name ?: "未知习惯"
                )
            }
            .sortedByDescending { it.checkIn.timestamp }
    }
    
    // 当Fragment重新回到前台时刷新数据
    override fun onResume() {
        super.onResume()
        recordsAdapter.updateData(getActiveHabitCheckIns())
        updateTitle()
    }
    
    // 数据类，包含打卡记录和习惯名称
    data class CheckInWithHabitName(
        val checkIn: CheckIn,
        val habitName: String
    )
    
    // 打卡记录适配器
    inner class RecordsAdapter(
        private var records: List<CheckInWithHabitName>
    ) : RecyclerView.Adapter<RecordsAdapter.ViewHolder>() {
        
        fun updateData(newRecords: List<CheckInWithHabitName>) {
            records = newRecords
            notifyDataSetChanged()
        }
        
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_record, parent, false)
            return ViewHolder(view)
        }
        
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val record = records[position]
            holder.bind(record)
        }
        
        override fun getItemCount() = records.size
        
        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            
            fun bind(record: CheckInWithHabitName) {
                val habitNameTextView = itemView.findViewById<TextView>(R.id.textViewHabitName)
                val dateTextView = itemView.findViewById<TextView>(R.id.textViewDate)
                val noteTextView = itemView.findViewById<TextView>(R.id.textViewNote)
                
                habitNameTextView.text = record.habitName
                
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                val dateString = sdf.format(Date(record.checkIn.timestamp))
                dateTextView.text = dateString
                
                // 简化备注显示
                noteTextView.visibility = View.GONE
            }
        }
    }
} 
package com.sopp.pactlift.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sopp.pactlift.R
import com.sopp.pactlift.data.DataManager
import com.sopp.pactlift.model.CheckIn
import com.sopp.pactlift.model.Habit
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CheckInFragment : Fragment() {
    
    private lateinit var dataManager: DataManager
    private lateinit var habitSpinner: Spinner
    private lateinit var checkInButton: FloatingActionButton
    private lateinit var dateTimeTextView: TextView
    private lateinit var statusTextView: TextView
    
    private val habits = mutableListOf<Habit>()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_check_in, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        dataManager = DataManager.getInstance(requireContext())
        
        habitSpinner = view.findViewById(R.id.spinnerHabit)
        checkInButton = view.findViewById(R.id.fabCheckIn)
        dateTimeTextView = view.findViewById(R.id.textViewDateTime)
        statusTextView = view.findViewById(R.id.textViewStatus)
        
        // 设置当前日期时间
        updateDateTime()
        
        // 加载习惯列表
        loadHabits()
        
        // 更新状态
        updateStatus()
        
        // 设置打卡按钮点击事件
        checkInButton.setOnClickListener {
            performCheckIn()
        }
    }
    
    private fun updateDateTime() {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val currentDateAndTime = sdf.format(Date())
        dateTimeTextView.text = "当前时间: $currentDateAndTime"
    }
    
    private fun loadHabits() {
        habits.clear()
        habits.addAll(dataManager.getHabits().filter { it.isActive })
        
        // 如果没有习惯，就创建一个默认习惯
        if (habits.isEmpty()) {
            val defaultHabit = Habit(
                name = "每日打卡",
                description = "每天坚持打卡",
                targetDays = 30
            )
            dataManager.addHabit(defaultHabit)
            habits.add(defaultHabit)
        }
        
        val habitNames = habits.map { it.name }
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            habitNames
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        habitSpinner.adapter = adapter
        
        checkInButton.isEnabled = true
    }
    
    private fun updateStatus() {
        if (habits.isEmpty()) {
            statusTextView.text = ""
            return
        }
        
        // 检查今天是否已经打卡
        val today = getStartOfDay(System.currentTimeMillis())
        val allCheckIns = dataManager.getCheckIns()
        
        val todayCheckIns = allCheckIns.filter { 
            getStartOfDay(it.timestamp) == today 
        }
        
        if (todayCheckIns.isNotEmpty()) {
            val habitNames = todayCheckIns.mapNotNull { checkIn -> 
                habits.find { it.id == checkIn.habitId }?.name 
            }.joinToString(", ")
            
            statusTextView.text = "今天已经完成打卡: $habitNames"
        } else {
            statusTextView.text = "您今天还没有打卡"
        }
    }
    
    private fun getStartOfDay(timestamp: Long): Long {
        val calendar = java.util.Calendar.getInstance()
        calendar.timeInMillis = timestamp
        calendar.set(java.util.Calendar.HOUR_OF_DAY, 0)
        calendar.set(java.util.Calendar.MINUTE, 0)
        calendar.set(java.util.Calendar.SECOND, 0)
        calendar.set(java.util.Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }
    
    private fun performCheckIn() {
        if (habits.isEmpty()) {
            Toast.makeText(context, "无法打卡", Toast.LENGTH_SHORT).show()
            return
        }
        
        val selectedPosition = habitSpinner.selectedItemPosition
        if (selectedPosition < 0 || selectedPosition >= habits.size) {
            Toast.makeText(context, "请选择一个习惯", Toast.LENGTH_SHORT).show()
            return
        }
        
        val selectedHabit = habits[selectedPosition]
        
        // 检查今天是否已经为这个习惯打过卡
        val today = getStartOfDay(System.currentTimeMillis())
        val habitCheckIns = dataManager.getCheckInsForHabit(selectedHabit.id)
        
        val alreadyCheckedIn = habitCheckIns.any { 
            getStartOfDay(it.timestamp) == today 
        }
        
        if (alreadyCheckedIn) {
            Toast.makeText(context, "今天已经为\"${selectedHabit.name}\"打过卡了", Toast.LENGTH_SHORT).show()
            return
        }
        
        // 创建打卡记录
        val checkIn = CheckIn(
            habitId = selectedHabit.id,
            note = "" // 不需要备注
        )
        
        dataManager.addCheckIn(checkIn)
        
        Toast.makeText(context, "打卡成功", Toast.LENGTH_SHORT).show()
        
        // 更新状态
        updateStatus()
        updateDateTime()
    }
    
    override fun onResume() {
        super.onResume()
        loadHabits()
        updateStatus()
        updateDateTime()
    }
} 
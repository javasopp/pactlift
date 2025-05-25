package com.sopp.pactlift.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sopp.pactlift.R
import com.sopp.pactlift.data.DataManager
import com.sopp.pactlift.model.Habit
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.widget.EditText
import android.widget.LinearLayout
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import android.widget.Button

class HabitFragment : Fragment() {
    
    private lateinit var habitAdapter: HabitAdapter
    private lateinit var dataManager: DataManager
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_habit, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        dataManager = DataManager.getInstance(requireContext())
        
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val addButton = view.findViewById<FloatingActionButton>(R.id.fabAddHabit)
        
        habitAdapter = HabitAdapter(dataManager.getHabits().toMutableList()) { habit ->
            // 点击习惯项的处理，显示详情或切换状态
            toggleHabitStatus(habit)
        }
        
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = habitAdapter
        
        addButton.setOnClickListener {
            showAddHabitDialog()
        }
    }
    
    private fun toggleHabitStatus(habit: Habit) {
        // 切换习惯的活跃状态
        habit.isActive = !habit.isActive
        dataManager.updateHabit(habit)
        
        // 显示状态切换的提示
        val statusText = if (habit.isActive) "已激活" else "已暂停"
        Toast.makeText(context, "习惯\"${habit.name}\"${statusText}", Toast.LENGTH_SHORT).show()
        
        // 刷新列表
        habitAdapter.updateData(dataManager.getHabits())
    }
    
    private fun showAddHabitDialog() {
        try {
            val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_habit, null)
            val nameEditText = dialogView.findViewById<EditText>(R.id.editTextHabitName)
            val descriptionEditText = dialogView.findViewById<EditText>(R.id.editTextHabitDescription)
            val targetDaysEditText = dialogView.findViewById<EditText>(R.id.editTextTargetDays)
            val btnConfirm = dialogView.findViewById<Button>(R.id.btnConfirm)
            val btnCancel = dialogView.findViewById<Button>(R.id.btnCancel)
            
            val dialog = MaterialAlertDialogBuilder(requireContext())
                .setView(dialogView)
                .setCancelable(true)
                .create()
            
            btnCancel.setOnClickListener {
                dialog.dismiss()
            }
            
            btnConfirm.setOnClickListener {
                try {
                    val name = nameEditText.text.toString().trim()
                    val description = descriptionEditText.text.toString().trim()
                    val targetDaysStr = targetDaysEditText.text.toString().trim()
                    
                    if (name.isEmpty()) {
                        nameEditText.error = "请输入习惯名称"
                        return@setOnClickListener
                    }
                    
                    val targetDays = if (targetDaysStr.isEmpty()) 30 else targetDaysStr.toInt()
                    
                    val habit = Habit(
                        name = name,
                        description = description,
                        targetDays = targetDays
                    )
                    
                    dataManager.addHabit(habit)
                    habitAdapter.updateData(dataManager.getHabits())
                    dialog.dismiss()
                    
                    Toast.makeText(requireContext(), "习惯添加成功", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "添加习惯失败: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
            
            dialog.show()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "显示对话框失败: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    
    // 当Fragment重新回到前台时刷新数据
    override fun onResume() {
        super.onResume()
        habitAdapter.updateData(dataManager.getHabits())
    }
    
    // 习惯列表适配器
    inner class HabitAdapter(
        private var habits: MutableList<Habit>,
        private val onItemClick: (Habit) -> Unit
    ) : RecyclerView.Adapter<HabitAdapter.ViewHolder>() {
        
        fun updateData(newHabits: List<Habit>) {
            habits.clear()
            habits.addAll(newHabits)
            notifyDataSetChanged()
        }
        
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_habit, parent, false)
            return ViewHolder(view)
        }
        
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val habit = habits[position]
            holder.bind(habit)
        }
        
        override fun getItemCount() = habits.size
        
        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            
            fun bind(habit: Habit) {
                val habitNameView = itemView.findViewById<TextView>(R.id.btnHabitName)
                val progressView = itemView.findViewById<TextView>(R.id.btnProgress)
                val statusView = itemView.findViewById<TextView>(R.id.textViewStatus)
                val progressBarView = itemView.findViewById<View>(R.id.viewProgress)
                val progressRemainingView = itemView.findViewById<View>(R.id.viewProgressRemaining)
                val headerLayout = itemView.findViewById<LinearLayout>(R.id.layoutHeader)
                
                habitNameView.text = habit.name
                progressView.text = "${habit.completedDays}/${habit.targetDays}"
                
                // 设置状态文本和颜色
                if (habit.isActive) {
                    statusView.text = "活跃中"
                    statusView.setTextColor(resources.getColor(android.R.color.holo_green_dark, null))
                    headerLayout.setBackgroundColor(resources.getColor(android.R.color.holo_blue_light, null))
                    progressView.setTextColor(resources.getColor(android.R.color.holo_blue_dark, null))
                } else {
                    statusView.text = "已暂停"
                    statusView.setTextColor(resources.getColor(android.R.color.darker_gray, null))
                    headerLayout.setBackgroundColor(resources.getColor(android.R.color.darker_gray, null))
                    progressView.setTextColor(resources.getColor(android.R.color.black, null))
                }
                
                // 计算完成百分比
                val percentage = if (habit.targetDays > 0) {
                    (habit.completedDays * 100 / habit.targetDays).coerceAtMost(100)
                } else {
                    0
                }
                
                // 根据完成百分比和活跃状态设置进度颜色
                val progressColor = when {
                    !habit.isActive -> android.R.color.darker_gray
                    percentage >= 100 -> android.R.color.holo_green_dark
                    percentage >= 50 -> android.R.color.holo_green_light
                    percentage >= 25 -> android.R.color.holo_orange_light
                    else -> android.R.color.holo_red_light
                }
                
                // 设置进度条
                val totalWidth = 150 // 进度条总宽度
                val progressWidth = (totalWidth * percentage / 100).coerceAtMost(totalWidth).coerceAtLeast(5)
                val remainingWidth = totalWidth - progressWidth
                
                val layoutParams = progressBarView.layoutParams
                layoutParams.width = progressWidth
                progressBarView.layoutParams = layoutParams
                progressBarView.setBackgroundColor(resources.getColor(progressColor, null))
                
                val remainingLayoutParams = progressRemainingView.layoutParams
                remainingLayoutParams.width = remainingWidth
                progressRemainingView.layoutParams = remainingLayoutParams
                
                itemView.setOnClickListener {
                    onItemClick(habit)
                }
            }
        }
    }
} 
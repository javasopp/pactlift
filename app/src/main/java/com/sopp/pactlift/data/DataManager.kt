package com.sopp.pactlift.data

import android.content.Context
import android.content.SharedPreferences
import com.sopp.pactlift.model.CheckIn
import com.sopp.pactlift.model.Habit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * 数据管理类，负责保存和读取数据
 */
class DataManager private constructor(context: Context) {
    
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()
    
    // 单例模式
    companion object {
        private const val PREF_NAME = "pactlift_data"
        private const val KEY_HABITS = "habits"
        private const val KEY_CHECK_INS = "check_ins"
        
        @Volatile
        private var instance: DataManager? = null
        
        fun getInstance(context: Context): DataManager {
            return instance ?: synchronized(this) {
                instance ?: DataManager(context.applicationContext).also { instance = it }
            }
        }
    }
    
    // 保存习惯列表
    fun saveHabits(habits: List<Habit>) {
        val json = gson.toJson(habits)
        sharedPreferences.edit().putString(KEY_HABITS, json).apply()
    }
    
    // 获取习惯列表
    fun getHabits(): List<Habit> {
        val json = sharedPreferences.getString(KEY_HABITS, null) ?: return emptyList()
        val type = object : TypeToken<List<Habit>>() {}.type
        return gson.fromJson(json, type)
    }
    
    // 保存打卡记录列表
    fun saveCheckIns(checkIns: List<CheckIn>) {
        val json = gson.toJson(checkIns)
        sharedPreferences.edit().putString(KEY_CHECK_INS, json).apply()
    }
    
    // 获取打卡记录列表
    fun getCheckIns(): List<CheckIn> {
        val json = sharedPreferences.getString(KEY_CHECK_INS, null) ?: return emptyList()
        val type = object : TypeToken<List<CheckIn>>() {}.type
        return gson.fromJson(json, type)
    }
    
    // 添加习惯
    fun addHabit(habit: Habit) {
        val habits = getHabits().toMutableList()
        habits.add(habit)
        saveHabits(habits)
    }
    
    // 更新习惯
    fun updateHabit(habit: Habit) {
        val habits = getHabits().toMutableList()
        val index = habits.indexOfFirst { it.id == habit.id }
        if (index != -1) {
            habits[index] = habit
            saveHabits(habits)
        }
    }
    
    // 删除习惯
    fun deleteHabit(habitId: String) {
        val habits = getHabits().toMutableList()
        habits.removeIf { it.id == habitId }
        saveHabits(habits)
        
        // 同时删除相关的打卡记录
        val checkIns = getCheckIns().toMutableList()
        checkIns.removeIf { it.habitId == habitId }
        saveCheckIns(checkIns)
    }
    
    // 添加打卡记录
    fun addCheckIn(checkIn: CheckIn) {
        val checkIns = getCheckIns().toMutableList()
        checkIns.add(checkIn)
        saveCheckIns(checkIns)
        
        // 更新习惯的完成天数
        val habits = getHabits().toMutableList()
        val habit = habits.find { it.id == checkIn.habitId }
        if (habit != null) {
            habit.completedDays++
            saveHabits(habits)
        }
    }
    
    // 获取指定习惯的打卡记录
    fun getCheckInsForHabit(habitId: String): List<CheckIn> {
        return getCheckIns().filter { it.habitId == habitId }
    }
} 
package com.sopp.pactlift.model

import java.util.UUID

/**
 * 习惯数据模型
 */
data class Habit(
    val id: String = UUID.randomUUID().toString(),
    var name: String,
    var description: String = "",
    var targetDays: Int = 30,
    var completedDays: Int = 0,
    var isActive: Boolean = true,
    var createdAt: Long = System.currentTimeMillis()
) 
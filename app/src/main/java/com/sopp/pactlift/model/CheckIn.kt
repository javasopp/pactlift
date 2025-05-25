package com.sopp.pactlift.model

import java.util.UUID

/**
 * 打卡记录数据模型
 */
data class CheckIn(
    val id: String = UUID.randomUUID().toString(),
    val habitId: String,
    val timestamp: Long = System.currentTimeMillis(),
    var note: String = ""
) 
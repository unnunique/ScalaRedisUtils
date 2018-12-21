package com.sydney.dream.dianshang.redis.test.model

/**
  * 红绿灯枚举
  */
object TrafficLightColor extends Enumeration {
    type TraffiLightColor = Value
    val Red = Value(0, "stop")
    val YELLOW = Value(10)
    val GREEN = Value("go")
}

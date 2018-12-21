package com.sydney.dream.dianshang.redis.test.model

/**
  * 枚举类
  */
object Margin extends Enumeration{
    type Margin = Value
    val TOP, BOTTOM, LEFT, RIGHT = Value

    // 模式匹配
    def inWhere(direction:Margin):String  = direction match {
        case Margin.BOTTOM => "bottom"
        case Margin.TOP => "top"
        case Margin.LEFT => "left"
        case Margin.RIGHT => "right"
        case _ => "nothing"
    }
}

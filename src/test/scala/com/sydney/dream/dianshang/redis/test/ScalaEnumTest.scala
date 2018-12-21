package com.sydney.dream.dianshang.redis.test

import com.sydney.dream.dianshang.redis.test.model.{Margin, TrafficLightColor}
import org.scalatest.FunSuite

/**
  * scala 枚举类的应用
  *
  * @Autor 小生有画说
  */
class ScalaEnumTest extends FunSuite{



    test("枚举类Margin相关测试") {
        // 对枚举类 进行遍历的方法
        println("遍历所有的枚举的值，以及下标。")
        for (s <- Margin.values) {
            println("id: " + s.id  + ", value: " + s)
        }
        // 模式匹配
        val direction = Margin.BOTTOM
        val pipei = "bottom".equals(Margin.inWhere(direction))
        println("模式匹配情况： " + pipei)
        assert(pipei)
    }

    test("枚举类TrafficLightColor ") {
        val color = TrafficLightColor.GREEN
        val result = doWhat(color)
        assert("go".equals(result))
    }

    def doWhat(color: TrafficLightColor.Value):String =  {
        if (color == TrafficLightColor.Red) "stop"
        else if (color == TrafficLightColor.YELLOW) "hurry up" else "go"
    }
}







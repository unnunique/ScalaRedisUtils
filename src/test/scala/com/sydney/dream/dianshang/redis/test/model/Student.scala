package com.sydney.dream.dianshang.redis.test.model

import io.protostuff.Tag

/**
  * Created by Edianzu on 2018/12/20.
  */
class Student {
    // 姓名
    @Tag(1)
    var name:String = _

    @Tag(2)
    var studentNo:String = _

    @Tag(3)
    var age:Int = _

    @Tag(4)
    var schoolName:String = _


    override def toString = s"Student($name, $studentNo, $age, $schoolName)"

    override def equals(obj: scala.Any): Boolean = {
        val that = obj.asInstanceOf[Student]
        if ((this.name != null && this.name.equals(that.name)) &&
            (this.schoolName != null && this.schoolName.equals(that.schoolName)) &&
            (this.studentNo != null && this.studentNo.equals(that.studentNo)) &&
            (this.age == that.age)) {
            true
        }  else {
            false
        }
    }
}

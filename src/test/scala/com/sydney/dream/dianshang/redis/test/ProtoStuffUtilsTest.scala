package com.sydney.dream.dianshang.redis.test

import com.sydney.dream.dianshang.redis.test.model.Student
import com.sydney.dream.dianshang.utils.ProtoStuffUtils
import org.scalatest.FunSuite

/**
  * 序列化和反序列化的测试
  *
  * @Autor 小生有画说
  */
class ProtoStuffUtilsTest extends FunSuite{

    test("serializer an deserializer") {
        val student = new Student
        student.name = "jingjiu"
        student.studentNo = "abcdefg"
        student.age = 20
        student.schoolName = "xihudaxue"

        val serialResult = ProtoStuffUtils.serializer(student)

        val deserialResult = ProtoStuffUtils.deserializer(serialResult, classOf[Student])

        assert(student.equals(deserialResult))
    }
}




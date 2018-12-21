package com.sydney.dream.dianshang.redis.test

import com.sydney.dream.dianshang.redis.test.model.Student
import com.sydney.dream.dianshang.redis.{IKVRedisClient, KVProtoStuffRedisClient}
import org.scalatest.FunSuite

/**
  * 关于KV 对的操作抽象接口，在scala 中叫做trait， 类似于java interface,
  * 对具体的实现进行测试
  * byte 的情况
  *
  * @Autor 小生有画说
  */
class KVProtoStuffRedisClientTest  extends FunSuite{

    private val iKVRedisCleint:IKVRedisClient[Student] = new KVProtoStuffRedisClient

    private val redisKey = "tubudemoTest"

    val student = new Student
    student.name = "jingjiu"
    student.studentNo = "abcdefg"
    student.age = 20
    student.schoolName = "xihudaxue"

    test("set method ") {
        // 先保存student 对象
        val result = iKVRedisCleint.set(redisKey, student)

        // 然后从redis 中取出对象
        val resultAfterSet = iKVRedisCleint.get(redisKey, classOf[Student])

        // del
        val delResult = iKVRedisCleint.del(redisKey)

        // 然后
        assert(student.equals(resultAfterSet.value.asInstanceOf[Student])
            && result.value)

    }


    test("del mothod ") {
        // 先保存student 对象
        val result = iKVRedisCleint.set(redisKey, student)

        // 然后从redis 中取出对象
        val resultAfterSet = iKVRedisCleint.get(redisKey, classOf[Student])

        // del
        val delResult = iKVRedisCleint.del(redisKey)


        // after del
        val afterDelResult = iKVRedisCleint.get(redisKey, classOf[Student])

        // 然后
        assert(student.equals(resultAfterSet.value.asInstanceOf[Student])
            && result.value  && afterDelResult.value == null)
    }

    test("put method ") {
        val result = iKVRedisCleint.put(redisKey, student, 3)

        // 然后从redis 中取出对象
        val resultAfterSet = iKVRedisCleint.get(redisKey, classOf[Student])

        // del
        val delResult = iKVRedisCleint.del(redisKey)


        // after del
        val afterDelResult = iKVRedisCleint.get(redisKey, classOf[Student])

        // 然后
        assert(student.equals(resultAfterSet.value.asInstanceOf[Student])
            && result.value  && afterDelResult.value == null && delResult.value)
    }
}

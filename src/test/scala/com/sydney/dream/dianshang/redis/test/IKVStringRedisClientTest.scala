package com.sydney.dream.dianshang.redis.test

import com.sydney.dream.dianshang.factory.RedisClienntFactory
import org.scalatest.FunSuite
/**
  * 关于KV 对的操作抽象接口，在scala 中叫做trait， 类似于java interface,
  * 对具体的实现进行测试
  * String 的情况
  *
  * @Autor 小生有画说
  */
class IKVStringRedisClientTest extends FunSuite{
    private val iKVRedisClient = RedisClienntFactory.getKVStringReidsClient()
    private val redisKey = "IKVRedisClientTest-tubudemov1"
    private val redisValue = "tubudemov1 values, the max size can be 512M."

    // 对set -----> get expireKey 的测试通过
    test("kVStringRedisClient.set, redisClient set 功能.") {
        // 存值
        iKVRedisClient.set(redisKey, redisValue)
        // 取值
        val result = iKVRedisClient.get(redisKey, classOf[String]).value
        // 清除环境内容
        iKVRedisClient.del(redisKey)
        // 判断是否相等
        assert(redisValue.equals(result))
    }

    // 对 put ----> get 进行测试
    test("kVStringRedisClient.put, redisClient put 功能.") {
        iKVRedisClient.put(redisKey,
            redisValue, 2)

        val result = iKVRedisClient.get(redisKey, classOf[String]).value

        Thread.sleep(3000)

        val finalResult = iKVRedisClient.get(redisKey, classOf[String])

        iKVRedisClient.del(redisKey)

        assert(redisValue.equals(result) && finalResult.value == null)

    }

    test("test del method.") {
        // 存值
        iKVRedisClient.set(redisValue, redisValue)
        // 取值
        val result = iKVRedisClient.get(redisValue, classOf[String]).value
        // 删除key
        iKVRedisClient.del(redisValue)
        // 删除后取值
        val afterDelResult = iKVRedisClient.get(redisValue, classOf[String]).value

        assert(redisValue.equals(result) && afterDelResult == null)
    }

    test("test incr method") {
        iKVRedisClient.incr(redisKey, 2)
        // result
        val result = iKVRedisClient.get(redisKey, classOf[String]).value
        iKVRedisClient.incr(redisKey, 2)
        val result2 = iKVRedisClient.get(redisKey, classOf[String]).value
        //
        Thread.sleep(3000)
        val result3= iKVRedisClient.get(redisKey, classOf[String]).value

        assert("1".equals(result) && "2".equals(result2) && result3 == null)
    }


    test("kVStringRedisClient.get, redisClient get 功能.") {
        // 存值
        iKVRedisClient.set(redisKey, redisValue)
        // 取值
        val result = iKVRedisClient.get(redisKey, classOf[String]).value
        // 清除环境内容
        iKVRedisClient.del(redisKey)
        // 判断是否相等
        assert(redisValue.equals(result))
    }



}

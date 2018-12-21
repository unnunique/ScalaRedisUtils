package com.sydney.dream.dianshang.redis
import java.util

import scala.util.control.Breaks._

import com.sydney.dream.dianshang.model.{Result, ResultCode}
import redis.clients.jedis.ShardedJedis


/**
  * Redis sort set  有关的操作
  * @Autor 小生有画说
  */
//TODO 日记处理
//TODO 参数验证
class SortSetStringRedisClient extends AbsRedisClient with ISortSetRedisClient[String]{
    /**
      * 按照得分，把数据进行返回
      *
      * @param key       具体的key
      * @param minScore  指定最小的score
      * @param maxScore  指定最大的score
      * @param classType 返回值的类型
      * @return 按照得分范围，返回到的值
      */
    override def zrangeByScore(key: String,
                               minScore: Double,
                               maxScore: Double,
                               classType: Class[String]): Result[util.Set[String]] = {
        var jedis:ShardedJedis = null
        var broken = false
        try {
            jedis = shardedJedisPool.getResource
            Result.createSuccess(jedis.zrangeByScore(key, minScore, maxScore))
        } catch {
            case ex:Exception =>
                ex.printStackTrace()
                broken = handleException(ex)
                Result.createError(ResultCode.REDIS_ERROR.toString,
                    ResultCode.REDIS_ERROR.id)
        } finally {
            closeResource(jedis, broken)
        }

    }

    /**
      * 保存值，
      *
      * @param key   值的key
      * @param score 值对应的得分，用来派逊
      * @param value 具体的值
      * @return 当前， 注意返回0 表示保存失败，1，表示保存成功
      */
override def zadd(key: String, score: Double, value: String): Result[Long] = {
    var jedis:ShardedJedis = null
    var broken = false
    try {
        jedis = shardedJedisPool.getResource
        val result = jedis.zadd(key, score, value)
        if (result == 0 || result == 1) {
            Result.createSuccess(result)
        } else {
            // 重试
            var retryFailed = true
            var finalResult:Long = -1
            breakable {
                for (i <- 0 until 3 ) {
                    var resultRetry = jedis.zadd(key, score, value)
                    if (resultRetry == 0 || resultRetry == 1) {
                        retryFailed = false
                        finalResult = resultRetry
                        break
                    }
                }
            }
            if (retryFailed) {
                Result.createError(ResultCode.REDIS_ERROR.toString,
                    ResultCode.REDIS_ERROR.id)
            } else {
                Result.createSuccess(finalResult)
            }
        }
    } catch {
        case ex:Exception =>
            ex.printStackTrace()
            broken = handleException(ex)
            Result.createError(ResultCode.REDIS_ERROR.toString,
                ResultCode.REDIS_ERROR.id)
    } finally {
        closeResource(jedis, broken)
    }
}

    /**
      * 删除set 中的某个值
      *
      * @param key   具体的key
      * @param value 具体的值
      * @return 需要返回的内容
      */
    override def zrem(key: String, value: String): Result[Long] = {
        var jedis:ShardedJedis = null
        var broken = false
        try {
            jedis = shardedJedisPool.getResource
            Result.createSuccess(jedis.zrem(key, value))
        } catch {
            case ex:Exception =>
                ex.printStackTrace()
                broken = handleException(ex)
                Result.createError(ResultCode.REDIS_ERROR.toString,
                    ResultCode.REDIS_ERROR.id)
        } finally {
            closeResource(jedis, broken)
        }
    }
}

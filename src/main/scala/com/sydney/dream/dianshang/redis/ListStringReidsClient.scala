package com.sydney.dream.dianshang.redis
import java.util


import scala.util.control.Breaks._
import com.sydney.dream.dianshang.model.{Result, ResultCode}
import redis.clients.jedis.ShardedJedis


/**
  * Redis List 有关的操作
  * @Autor 小生有画说
  */
//TODO 日记处理
//TODO 参数验证
class ListStringReidsClient extends AbsRedisClient with IListRedisClient[String]{
    /**
      * 存储值，可以传入多个值，并且设置超时时间
      * 从右侧添加多个元素
      *
      * @param key        key
      * @param expireTime 超时时间
      * @param values      具体的值
      * @return
      */
    override def rpush(key: String, expireTime: Int, values: String*): Result[Long] = {
//        Preconditions.checkArgument(value != null && value.length > 0)
        var jedis:ShardedJedis = null
        var broken = false
        try {
            jedis = shardedJedisPool.getResource
            var count = 0
            // List 中总的数据量
            var totolCountOfList = 0L
            breakable{
                for(value <- values) {
                    val res = jedis.rpush(key, value)
                    if (res == null || res == 0) {
                        count = count + 1
                        break
                    }
                    totolCountOfList = res
                }
            }
            if (count > 0) {
                Result.createError(ResultCode.REDIS_ERROR.toString,
                    ResultCode.REDIS_ERROR.id)
            } else {
                jedis.expire(key, expireTime)
                Result.createSuccess(totolCountOfList)
            }
        } catch {
            case ex:Exception =>
                broken = handleException(ex)
                Result.createError(ResultCode.REDIS_ERROR.toString,
                    ResultCode.REDIS_ERROR.id)
        } finally {
            closeResource(jedis, broken)
        }
    }

    /**
      * 从右侧添加一个元素
      *
      * @param key        key 需要设置的key
      * @param expireTime 超时时间
      * @param value      具体的值
      * @return
      */
override def rpush(key: String, expireTime: Int, value: String): Result[Long] = {
    var jedis:ShardedJedis = null
    var broken = false
    try {
        jedis = shardedJedisPool.getResource
        val res = jedis.rpush(key, value)
        if (res == null || res == 0) {
            Result.createError(ResultCode.REDIS_ERROR.toString,
                ResultCode.REDIS_ERROR.id)
        } else {
            jedis.expire(key, expireTime)
            Result.createSuccess(res)
        }
    } catch {
        case ex:Exception =>
            broken = handleException(ex)
            Result.createError(ResultCode.REDIS_ERROR.toString,
                ResultCode.REDIS_ERROR.id)
    } finally {
        closeResource(jedis, broken)
    }
}

    /**
      * 获取指定下标的值
      *
      * @param key   key
      * @param start 开始下标，从0 开始
      * @param end   结束下标，如果是取到最后一个值，可以直接设置-1
      * @param classType
      * @return
      */
    override def lrange(key: String, start: Long, end: Long,
                        classType: Class[String]): Result[util.List[String]] = {
        var jedis:ShardedJedis = null
        var broken = false
        try {
            jedis = shardedJedisPool.getResource
            val res:java.util.List[String] = jedis.lrange(key, start, end)
            Result.createSuccess(res)
        } catch {
            case ex:Exception =>
                broken = handleException(ex)
                Result.createError(ResultCode.REDIS_ERROR.toString,
                    ResultCode.REDIS_ERROR.id)
        } finally {
            closeResource(jedis, broken)
        }
    }

    /**
      * 从左侧插入n 个值到list 中，并且可以设置超时时间
      *
      * @param key        key
      * @param expireTime 超时时间
      * @param values      具体的值
      * @return
      */
    override def lpush(key: String, expireTime: Int,
                       values: String*): Result[Long] = {
        var jedis:ShardedJedis = null
        var broken = false
        try {
            jedis = shardedJedisPool.getResource
            var count = 0
            // List 中总的数据量
            var totolCountOfList = 0L
            breakable{
                for(value <- values) {
                    val res = jedis.lpush(key, value)
                    if (res == null || res == 0) {
                        count = count + 1
                        break
                    }
                    totolCountOfList = res
                }
            }
            if (count > 0) {
                Result.createError(ResultCode.REDIS_ERROR.toString,
                    ResultCode.REDIS_ERROR.id)
            } else {
                jedis.expire(key, expireTime)
                Result.createSuccess(totolCountOfList)
            }
        } catch {
            case ex:Exception =>
                broken = handleException(ex)
                Result.createError(ResultCode.REDIS_ERROR.toString,
                    ResultCode.REDIS_ERROR.id)
        } finally {
            closeResource(jedis, broken)
        }
    }

    /**
      * 从左侧插入一个值，
      *
      * @param key
      * @param expireTime
      * @return
      */
    override def lpush(key: String, expireTime: Int, value:String): Result[Long] = {
        var jedis:ShardedJedis = null
        var broken = false
        try {
            jedis = shardedJedisPool.getResource
            val res = jedis.lpush(key, value)
            if (res == null || res == 0) {
                Result.createError(ResultCode.REDIS_ERROR.toString,
                    ResultCode.REDIS_ERROR.id)
            } else {
                jedis.expire(key, expireTime)
                Result.createSuccess(res)
            }
        } catch {
            case ex:Exception =>
                broken = handleException(ex)
                Result.createError(ResultCode.REDIS_ERROR.toString,
                    ResultCode.REDIS_ERROR.id)
        } finally {
            closeResource(jedis, broken)
        }
    }

    /**
      * 从左边开始，弹出一个值，
      *
      * @param key
      * @param classType
      * @return
      */
    override def lpop(key: String, classType: Class[String]): Result[String] = {
        var jedis:ShardedJedis = null
        var broken = false
        try {
            jedis = shardedJedisPool.getResource
            val res = jedis.lpop(key)
            Result.createSuccess(res)
        } catch {
            case ex:Exception =>
                broken = handleException(ex)
                Result.createError(ResultCode.REDIS_ERROR.toString,
                    ResultCode.REDIS_ERROR.id)
        } finally {
            closeResource(jedis, broken)
        }
    }

    /**
      * 从右边开始，弹出一个值
      *
      * @param key
      * @param classType
      * @return
      */
    override def rpop(key: String, classType: Class[String]): Result[String] = {
        var jedis:ShardedJedis = null
        var broken = false
        try {
            jedis = shardedJedisPool.getResource
            val res = jedis.rpop(key)
            Result.createSuccess(res)
        } catch {
            case ex:Exception =>
                broken = handleException(ex)
                Result.createError(ResultCode.REDIS_ERROR.toString,
                    ResultCode.REDIS_ERROR.id)
        } finally {
            closeResource(jedis, broken)
        }
    }

    /**
      * 截取指定下标的值，并且把列表中非指定下标的值删除
      *
      * @param key
      * @param start
      * @param end
      * @return
      */
    override def ltrim(key: String, start: Int,
                       end: Int): Result[String] = {
        var jedis:ShardedJedis = null
        var broken = false
        try {
            jedis = shardedJedisPool.getResource
            val res = jedis.ltrim(key, start, end)
            Result.createSuccess(res)
        } catch {
            case ex:Exception =>
                broken = handleException(ex)
                Result.createError(ResultCode.REDIS_ERROR.toString,
                    ResultCode.REDIS_ERROR.id)
        } finally {
            closeResource(jedis, broken)
        }
    }
}

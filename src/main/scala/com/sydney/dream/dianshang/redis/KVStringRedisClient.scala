package com.sydney.dream.dianshang.redis
import com.sydney.dream.dianshang.model.{Result, ResultCode}
import redis.clients.jedis.ShardedJedis

/**
  * Redis KV 对操作，V 是字符串类型
  * @Autor 小生有画说
  */
//TODO 日记处理
//TODO 参数验证
class KVStringRedisClient extends AbsRedisClient with IKVRedisClient[String]{
    /**
      * 设置值，并且设置超时时间
      *
      * @param key        key
      * @param value      具体的值
      * @param expireTime 过期时间，单位秒
      * @return 成功返回true
      */
    override def put(key: String, value: String, expireTime: Int): Result[Boolean] = {
        var jedis:ShardedJedis = null
        var broken = false
        try {
            jedis = shardedJedisPool.getResource
            val code = jedis.setex(key, expireTime, value)
            var successFlag = false
            if ("OK".equals(code)) {
                successFlag = true
            }
            Result.createSuccess(successFlag)
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
      * 保存kv 对
      *
      * @param key   key
      * @param value 具体的值
      * @return 成功返回true
      */
    override def set(key: String, value: String): Result[Boolean] = {
        var jedis:ShardedJedis = null
        var broken = false
        try {
            jedis = shardedJedisPool.getResource
            val code = jedis.set(key, value)
            Result.createSuccess(new Result[Boolean]().value, code)
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
      * 取得key 对应的值
      *
      * @param key key
      * @return 对应的值
      */
    override def get[T >: Null](key: String, clazz: Class[T]): Result[_] = {
        var jedis:ShardedJedis = null
        var broken = false
        try {
            jedis = shardedJedisPool.getResource
            val value = jedis.get(key)
            Result.createSuccess(value)
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
      * 删除对应的k/V 对
      *
      * @param key key
      * @return 删除key
      */
    override def del(key: String): Result[Boolean] = {
        var jedis:ShardedJedis = null
        var broken = false
        try {
            jedis = shardedJedisPool.getResource
            val code = jedis.del(key)
            var isSuccess = false
            if (code == 1) {
                isSuccess = true
            }
            Result.createSuccess(isSuccess)
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
      * 对值进行自增, 并且设置过期时间
      *
      *
      * @param key        自增的key
      * @param expireTime 过期的时间
      * @return
      */
    // TODO 优化此代码， 代码里面做了两件事情
    override def incr(key: String, expireTime: Int): Result[Long] = {
        var jedis:ShardedJedis = null
        var broken = false
        try {
            jedis = shardedJedisPool.getResource
            val value = jedis.incr(key)
            if (value == null) {
                Result.createError(ResultCode.REDIS_ERROR.toString,
                    ResultCode.REDIS_ERROR.id)
            }  else {
                jedis.expire(key, expireTime)
                Result.createSuccess(value)
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


}

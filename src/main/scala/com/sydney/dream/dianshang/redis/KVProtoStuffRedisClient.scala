package com.sydney.dream.dianshang.redis
import com.google.common.base.Charsets
import com.sydney.dream.dianshang.model.{Result, ResultCode}
import com.sydney.dream.dianshang.utils.ProtoStuffUtils
import redis.clients.jedis.ShardedJedis

/**
  * Redis KV 对操作，V 是序列化后的内容
  * 知识点，Web 或者其他程序，有时候需要保存整个对象的状态和值
  * 这时候就要用到序列化和反序列化的内容
  *
  * @Autor 小生有画说
  */
//TODO 日记处理
//TODO 参数验证
class KVProtoStuffRedisClient[T] extends AbsRedisClient with IKVRedisClient[T]{

    /**
      *
      * 获取对应string 的byte[]
      * @param value
      */
    def getByteKey(value:String): Array[Byte] = {
        value.getBytes(Charsets.UTF_8)
    }


    /**
      * 设置值，并且设置超时时间
      *
      * @param key        key
      * @param value      具体的值
      * @param expireTime 过期时间，单位秒
      * @return 成功返回true
      */
    override def put(key: String, value: T, expireTime: Int): Result[Boolean] = {
        var jedis:ShardedJedis = null
        var broken = false
        try {
            jedis = shardedJedisPool.getResource
            val code = jedis.setex(getByteKey(key), expireTime,
                ProtoStuffUtils.serializer(value))
            Result.createSuccess(true, code)
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
    override def set(key: String, value: T): Result[Boolean] = {
        var jedis:ShardedJedis = null
        var broken = false
        try {
            jedis = shardedJedisPool.getResource
            val code = jedis.set(getByteKey(key),
                ProtoStuffUtils.serializer(value))
            Result.createSuccess(true, code)
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
    override def get[T >: Null](key: String, clazz:Class[T]): Result[T] = {
        var jedis:ShardedJedis = null
        var broken = false
        try {
            jedis = shardedJedisPool.getResource
            val data = jedis.get(getByteKey(key))
            val t = ProtoStuffUtils.deserializer(data, clazz)
            Result.createSuccess(t)
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
            val code = jedis.del(getByteKey(key))
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
      * @param key        自增的key
      * @param expireTime 过期的时间
      * @return
      */
    override def incr(key: String, expireTime: Int): Result[Long] = ???
}

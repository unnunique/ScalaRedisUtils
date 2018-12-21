package com.sydney.dream.dianshang.redis

import redis.clients.jedis.ShardedJedis
import redis.clients.jedis.exceptions.{JedisConnectionException, JedisDataException, JedisException}
import com.sydney.dream.dianshang.model.{Result, ResultCode}
import com.sydney.dream.dianshang.utils.JedisHelper

/**
  * Jedis 抽象类
  * @Autor 小生有画说
  */
abstract class AbsRedisClient extends IRedisClient{

    //TODO 日记处理
    //TODO 参数验证

    protected val shardedJedisPool = JedisHelper.getShareJedisInfo()

    /**
      * 设置key 过期的时间
      * @param key
      * @param expireTime
      * @return
      */
    override def expireKey(key: String, expireTime: Int) = {
        var jedis:ShardedJedis = null
        var broken:Boolean = false
        try {
            jedis = shardedJedisPool.getResource
            val rs:Long = jedis.expire(key, expireTime)
            Result.createSuccess(rs)
        } catch {
            case ex:Exception =>
                broken = handleException(ex)
                Result.createError(ResultCode.REDIS_ERROR.toString, ResultCode.REDIS_ERROR.id)
        }
    }

    /**
      * 处理失败异常，handle jedisException,
      * write log and return whether the connection is broken.
      * 看是否连接断开，或者数据只是可读的状态
      * @param throwable
      * @return
      */
    def handleException(throwable: Throwable): Boolean = {
        if (!throwable.isInstanceOf[JedisException]) {
            false
        } else if (throwable.isInstanceOf[JedisConnectionException]) {
            true
        } else if (throwable.isInstanceOf[JedisDataException]) {
            if (throwable.getMessage != null &&
                throwable.getMessage.indexOf("READONLY") != -1) {
                true
            } else {
                false
            }
        } else {
            true
        }
    }

    /**
      * 关闭未释放的资源
      * @param jedisClient shardedJedis 客户端
      * @param connectionBroke 客户端是否异常断开
      */
    def closeResource(jedisClient:ShardedJedis, connectionBroke:Boolean): Unit = {
        if (jedisClient != null) {
            if (connectionBroke) {
                shardedJedisPool.returnBrokenResource(jedisClient)
            } else {
                shardedJedisPool.returnResource(jedisClient)
            }
        }
    }
}

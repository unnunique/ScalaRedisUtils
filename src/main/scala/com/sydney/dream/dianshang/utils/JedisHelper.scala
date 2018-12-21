package com.sydney.dream.dianshang.utils

import java.util

import redis.clients.jedis._

/**
  * jedis config,
  * 关于shardedJedis 客户端的配置和获取
  * @Autor 小生有画说
  */
// TODO Redis 信息配置化, 可以添加一个配置文件控制器，
// TODO 配置化参见ProjectConf
object JedisHelper {
    //spring.shardredis.database=1
    //spring.shardredis.host=ABC
    //spring.shardredis.port=7777
    //spring.shardredis.password=~redis123
    //spring.shardredis.jedis.pool.max-active=20
    //spring.shardredis.jedis.pool.max-wait=2000
    //spring.shardredis.jedis.pool.max-idle=5
    //spring.shardredis.jedis.pool.min-idle=5
    //spring.shardredis.timeout=1000
    //spring.shardredis.pool.whenExhaustedBlocked=true
    //spring.shardredis.pool.testOnBorrow=true
    //spring.shardredis.pool.testOnReturn=true
    private val jedisConfig = new JedisPoolConfig
    jedisConfig.setMaxTotal(20);//最大活动的对象个数
    jedisConfig.setMaxIdle(5);//
    jedisConfig.setMaxWaitMillis(2 * 1000);//获取对象时最大等待时间
    jedisConfig.setTestOnBorrow(true)
    private val host = "your hout or ip"
    private val port = 7777
    private val jedisShardInfo = new JedisShardInfo(host, port)
    jedisShardInfo.setPassword("~redis123")
    private val jedisShardInfos = new util.ArrayList[JedisShardInfo]()

    jedisShardInfos.add(jedisShardInfo)
    private val shardedJedisPool:ShardedJedisPool = new ShardedJedisPool(jedisConfig, jedisShardInfos)

    def getShareJedisInfo(): ShardedJedisPool = {
        return this.shardedJedisPool
    }

    /**
      * 测试是否可以ping 通jedis
      * @param args
      */
    def main(args: Array[String]): Unit = {
        var shardedJedisClient: ShardedJedis = null
        var jedisClient:Jedis = null
        var conectionBroken = false
        try {
            jedisClient = new Jedis("yourhostorip", 7777)
            jedisClient.auth("~redis123")
            println(jedisClient.ping())
            val shardedJedisClient = shardedJedisPool.getResource
            if (shardedJedisClient != null) {
                shardedJedisClient.set("demotubu", "value")
                shardedJedisClient.expire("demotubu", 10)
                val value = shardedJedisClient.get("demotubu")
                println(value)

                val rs = shardedJedisClient.setex("demotubu1", 10, "value01")
                println(rs)
                val value2 = shardedJedisClient.get("demotubu1")
                println(value2)
                shardedJedisClient.setex("demotubu1", 10, "value0dada2")
                val value3 = shardedJedisClient.get("demotubu1")
                println(value3)

                Thread.sleep(10 * 1000)

                val value4 = shardedJedisClient.get("demotubu1")
                println(value4)
            }
        } catch {
            case ex:Exception =>
                conectionBroken = true
                ex.printStackTrace()
        } finally {
            if (shardedJedisClient != null) {
                if (conectionBroken) {
                    shardedJedisPool.returnBrokenResource(shardedJedisClient)
                } else {
                    shardedJedisPool.returnResource(shardedJedisClient)
                }
            }
            if (jedisClient != null) {
                jedisClient.close()
            }
        }
    }
}

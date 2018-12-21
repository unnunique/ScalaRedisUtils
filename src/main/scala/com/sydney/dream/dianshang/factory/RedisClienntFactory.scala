package com.sydney.dream.dianshang.factory

import com.sydney.dream.dianshang.redis._

/**
  * KV RedisClient 的工厂类
  *
  *
  * @Autor 小生有画说
  */
object RedisClienntFactory {


    // TODO 优化工厂实现类，用来适配byte 的情况，redis 支持byte 的存储

    /**
      * 获取IKVRedisClient
      * @return
      */
    def getKVStringReidsClient(): IKVRedisClient[String] = {
        val iKVRedisClient:IKVRedisClient[String] = new KVStringRedisClient
        iKVRedisClient
    }

    /**
      * 获取IListRedisClient
      * @return
      */
    def getListStringRedisClient():IListRedisClient[String] = {
        val iListRedisClient:IListRedisClient[String] = new ListStringReidsClient
        iListRedisClient
    }


    /**
      * 获取 SortSetStringRedisClient
      * @return
      */
    def getSortSetStringRedisClient():ISortSetRedisClient[String] = {
        val iSortSetRedisClient:ISortSetRedisClient[String] = new SortSetStringRedisClient
        iSortSetRedisClient
    }


    /**
      * 获取 HashMapStringRedisClient
      * @return
      */
    def getHashMapStringRedisClient:IHashMapRedisClient[String] = {
        val iHashMapRedisClient:IHashMapRedisClient[String] = new HashMapStringRedisClient
        iHashMapRedisClient
    }

}

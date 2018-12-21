package com.sydney.dream.dianshang.redis

import com.sydney.dream.dianshang.model.Result

/**
  * jedis config,
  * redis sorted set 封装
  *
  * @Autor 小生有画说
  */
trait ISortSetRedisClient[T] extends  IRedisClient{

    /**
      * 按照得分，把数据进行返回
      * @param key 具体的key
      * @param minScore 指定最小的score
      * @param maxScore 指定最大的score
      * @param classType 返回值的类型
      * @return 按照得分范围，返回到的值
      */
    def zrangeByScore(key:String,
                      minScore:Double,
                      maxScore:Double,
                      classType:Class[T]): Result[java.util.Set[T]]


    /**
      * 保存值，
      * @param key 值的key
      * @param score 值对应的得分，用来排序
      * @param value 具体的值
      * @return 当前， 注意返回0 表示保存失败，1，表示保存成功
      */
    def zadd(key:String, score:Double, value:T):Result[Long]

    /**
      * 删除set 中的某个值
      * @param key 具体的key
      * @param value 具体的值
      * @return 需要返回的内容
      */
    def zrem(key:String, value:T):Result[Long]
}

package com.sydney.dream.dianshang.redis

import com.sydney.dream.dianshang.model.Result


/**
  * Redis List 有关的操作
  * @Autor 小生有画说
  */
trait IListRedisClient[T] {
    /**
      * 存储值，可以传入多个值，并且设置超时时间
      * 从右侧添加多个元素
      * @param key key
      * @param expireTime 超时时间
      * @param values 具体的值
      * @return
      */
    def rpush(key:String, expireTime:Int, values:T*):Result[Long]

    /**
      * 从右侧添加一个元素
      * @param key key 需要设置的key
      * @param expireTime 超时时间
      * @param value 具体的值
      * @return
      */
    def rpush(key:String, expireTime:Int, value:T):Result[Long]

    /**
      * 获取指定下标的值
      * @param key key
      * @param start 开始下标，从0 开始
      * @param end 结束下标，如果是取到最后一个值，可以直接设置-1
      * @param classType
      * @return
      */
    def lrange(key:String, start:Long, end:Long,
               classType:Class[T]):Result[java.util.List[T]]

    /**
      * 从左侧插入n 个值到list 中，并且可以设置超时时间
      * @param key key
      * @param expireTime 超时时间
      * @param values 具体的值
      * @return
      */
    def lpush(key:String, expireTime:Int, values:T*):Result[Long]

    /**
      * 从左侧插入一个值，
      * @param key
      * @param expireTime
      * @param value
      * @return
      */
    def lpush(key:String, expireTime:Int, value:T):Result[Long]

    /**
      * 从左边开始，弹出一个值，
      * @param key
      * @param classType
      * @return
      */
    def lpop(key:String, classType: Class[T]):Result[T]

    /**
      * 从右边开始，弹出一个值
      * @param key
      * @param classType
      * @return
      */
    def rpop(key:String, classType:Class[T]):Result[T]

    /**
      * 截取指定下标的值，并且把列表中非指定下标的值删除
      * @param key
      * @param start
      * @param end
      * @return
      */
    def ltrim(key:String, start:Int, end:Int):Result[String]

}

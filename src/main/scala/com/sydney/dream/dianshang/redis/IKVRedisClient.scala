package com.sydney.dream.dianshang.redis

import com.sydney.dream.dianshang.model.Result

/**
  * 关于KV 对的操作抽象接口，在scala 中叫做trait， 类似于java interface
  * @Autor 小生有画说
  */
trait IKVRedisClient[T] extends IRedisClient{
    /**
      * 设置值，并且设置超时时间
      * @param key  key
      * @param value 具体的值
      * @param expireTime 过期时间，单位秒
      * @return 成功返回true
      */
    def put(key:String, value:T, expireTime:Int):Result[Boolean]

    /**
      * 保存kv 对
      * @param key key
      * @param value 具体的值
      * @return 成功返回true
      */
    def set(key:String, value:T):Result[Boolean]


    /**
      * 取得key 对应的值
      * @param key key
      * @return 对应的值
      */
    def get[T >: Null](key:String, clazz:Class[T]):Result[_]


    /**
      * 删除对应的k/V 对
      * @param key key
      * @return 删除key
      */
    def del(key:String):Result[Boolean]


    /**
      * 对值进行自增, 并且设置过期时间
      * @param key 自增的key
      * @param expireTime 过期的时间
      * @return
      */
    def incr(key:String, expireTime:Int):Result[Long]

}

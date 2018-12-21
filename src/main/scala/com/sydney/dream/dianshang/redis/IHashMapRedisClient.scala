package com.sydney.dream.dianshang.redis

import com.sydney.dream.dianshang.model.Result

/**
  * redis hash 的相关操作。
  *
  * @Autor 小生有画说
  */
trait IHashMapRedisClient[T] extends IRedisClient{

    /**
      * 向hash缓存中设置字符串内容 返回值：
      * 如果 field 是哈希表中的一个新建域，并且值设置成功，返回 1 。
      * 如果哈希表中域 field 已经存在且旧值已被新值覆盖，返回 0 。
      *
      * @param key hash  的名字
      * @param field 具体的字段
      * @param value 字段对应的值
      * @return
      */
    def hset(key:String, field:String, value:T):Result[Boolean]


    /**
      * HGET key field 返回哈希表 key 中给定域 field 的值
      * 时间复杂度： O(1) 返回值： 给定域的值。
      * 当给定域不存在或是给定 key 不存在时，返回 nil 。
      *
      * @param key
      * @param field
      * @param clazz
      * @return
      */
    def hget(key:String, field:String, clazz:Class[T]):Result[T]


    /**
      * HMSET key field value [field value ...]
      * 同时将多个 field-value (域-值)对设置到哈希表 key 中。
      * 此命令会覆盖哈希表中已存在的域。
      * 如果 key 不存在，一个空哈希表被创建并执行 HMSET 操作。
      * 可用版本>= 2.0.0 时间复杂度： O(N)， N 为 field-value,对的数量。
      * 返回值：如果命令执行成功，返回 OK 。
      *
      * @param key
      * @param values
      * @return
      */
    def hmset(key:String, values: java.util.Map[String, T]):Result[Boolean]

    /**
      * HMGET key field [field ...]
      * <p/>
      * 返回哈希表 key 中，一个或多个给定域的值。
      * <p/>
      * 如果给定的域不存在于哈希表，那么返回一个 nil 值。
      * <p/>
      * 因为不存在的 key 被当作一个空哈希表来处理，
      * 所以对一个不存在的 key 进行
      * HMGET 操作将返回一个只带有 nil 值的表。
      * <p/>
      * 时间复杂度： O(N)， N 为给定域的数量。
      * 返回值： 一个包含多个给定域的关联值的表，
      * 表值的排列顺序和给定域参数的请求顺序一样。
      */
    def hmget(key:String, clazz:Class[T], fields:String*):Result[java.util.List[T]]


    /**
      * 获取hash 中所有的键值对
      * @param key
      * @param clazz
      * @return
      */
    def hgetAll(key:String, clazz:Class[T]):Result[java.util.Map[String, T]]


    /**
      * 获取所有hash 中对应的field
      * @param key
      * @return
      */
    def hkeys(key:String):Result[java.util.Set[String]]

    /**
      * HDEL key field [field ...]
      * <p/>
      * 删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略。
      * <p/>
      * 时间复杂度: O(N)， N 为要删除的域的数量。
      * 返回值: 被成功移除的域的数量，不包括被忽略的域。
      */
    def hdel(key:String, fields:String*):Result[Long]

}

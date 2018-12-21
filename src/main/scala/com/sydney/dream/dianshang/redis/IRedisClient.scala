package com.sydney.dream.dianshang.redis

import com.sydney.dream.dianshang.model.Result

/**
  * Redis 抽象接口
  * @Autor 小生有画说
  */
trait IRedisClient {
    def expireKey(key:String, expireTime:Int):Result[Long]
}

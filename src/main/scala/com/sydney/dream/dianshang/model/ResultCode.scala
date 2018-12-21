package com.sydney.dream.dianshang.model


/**
  * 异常返回码以及说明
  * @Autor 小生有画说
  */
object ResultCode extends Enumeration{
    type ResultCode = Value
    val SUCESS = Value(100000, "成功")
    val DB_ERROR = Value(200001,"数据库异常")
    val REDIS_ERROR = Value(200002, "系统异常")
    val INTERFACE_ERROR = Value(200004, "访问接口异常")
    val SYSTEM_ERROR = Value(200003, "系统异常")
    val BIZ_CHECK_ERROR = Value(301001, "参数校验失败")
}

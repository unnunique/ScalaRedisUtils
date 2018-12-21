package com.sydney.dream.dianshang.model

/**
  * 返回结果的统一封装
  * @Autor 小生有画说
  * @tparam T 泛型定义
  */
class Result[T] {
    // _ 初始化为默认值
    var success:Boolean = _  // 成功与否，成功true, 失败，false
    var value:T = _  // 具体要返回的值
    var msg:String = _ // 返回状态码说明
    var rc:Int = _ //放回状态码

    def this(success:Boolean, value:T, msg:String, rc:Int) {
        this
        this.success = success
        this.value = value
        this.msg = msg
        this.rc = rc
    }

    override def toString = s"Result($success, $value, $msg, $rc)"
}
object Result {

    /**
      * success 的时候的返回值
      * @tparam T 返回值的类型
      * @return
      */
    def createSuccess[T]():Result[T] = {
        new Result[T](true, new Result[T]().value, ResultCode.SUCESS.toString,
            ResultCode.SUCESS.id)
    }


    /**
      * succces 的时候的返回值
      * @param value succces 的时候的返回值
      * @tparam T 值的类型
      * @return
      */
    def createSuccess[T](value:T):Result[T] = {
        new Result(true, value,
            ResultCode.SUCESS.toString,
            ResultCode.SUCESS.id)
    }

    /**
      * succces 的时候的返回值
      * @param value succces 的时候的返回值
      * @param msg 状态码说明
      * @tparam T 返回值的类型
      * @return
      */
    def createSuccess[T](value:T, msg:String):Result[T] = {
        new Result(true, value, msg, ResultCode.SUCESS.id)
    }


    /**
      * error 的时候的返回值
      * @param msg 状态码说明
      * @tparam T 返回值的类型
      * @return
      */
    def createError[T](msg:String, rc:Int):Result[T] = {
        new Result[T](false, new Result[T]().value, msg, rc)
    }

    /**
      * error 的时候的返回值
      * @param value 具体的返回值
      * @param msg 状态码说明
      * @tparam T 返回值的类型
      * @return
      */
    def createErrror[T](msg:String, rc:Int, value:T): Result[T] = {
        new Result[T](false, value, msg, rc)
    }

    /**
      * 系统异常
      * @tparam T
      * @return
      */
    def createSysError[T](): Result[T] = {
        new Result[T](false, new Result[T]().value,
            ResultCode.SYSTEM_ERROR.toString,
            ResultCode.SYSTEM_ERROR.id)
    }

    /**
      * 数据库异常
      * @tparam T
      * @return
      */
    def createDBError[T]() = {
        new Result[T](false, new Result[T]().value,
            ResultCode.DB_ERROR.toString,
            ResultCode.DB_ERROR.id)
    }


    def main(args: Array[String]): Unit = {
        println(new Result[Boolean]())
    }
}

package com.sydney.dream.dianshang.utils

import java.util.concurrent.ConcurrentHashMap

import io.protostuff.{LinkedBuffer, ProtostuffIOUtil, Schema, Tag}
import io.protostuff.runtime.RuntimeSchema
import org.objenesis.{Objenesis, ObjenesisStd}

/**
  * 序列化和反序列化工具类的封装
  * 注意由于序列化和反序列化过程中，构造scheme 比较耗时，
  * 所以把scheme 缓存在内存中，是一个比较好的优化，
  * 这样就不用每次都去构造scheme 了，只需要在内存中获取就好了。
  *
  * @Autor 小生有画说
  */
object ProtoStuffUtils {
    // scala 通配符
    private val cacheSchema:java.util.Map[Class[_], Schema[_]] = new ConcurrentHashMap[Class[_], Schema[_]]()

    // 用于在反序列化的情况下，构造一个实例
    private val objenesis:Objenesis = new ObjenesisStd(true)

    /**
      * 获取schema
      * @param clazz
      * @tparam T
      * @return
      */
    def getSchema[T](clazz:Class[T]):Schema[T] = {
        var schema:Schema[T] = null
        if(cacheSchema.get(clazz) != null) {
            schema = cacheSchema.get(clazz).asInstanceOf[Schema[T]]
        }
        if (schema == null) {
            schema = RuntimeSchema.getSchema(clazz)
            if (schema != null) {
                cacheSchema.put(clazz, schema)
            }
        }
        schema
    }

    /**
      * 序列化
      * @param obj 需要序列化的对象
      * @tparam T 泛型
      * @return 序列化之后的数组
      */
    def serializer[T](obj: T):Array[Byte] = {
        if (obj == null) {
            null
        } else {
            val clazz:Class[T] = obj.getClass.asInstanceOf[Class[T]]
            val buffer:LinkedBuffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE)
            try {
                val schema = getSchema(clazz)
                ProtostuffIOUtil.toByteArray(obj, schema, buffer)
            } catch {
                case ex:Exception =>
                    throw  new IllegalArgumentException(ex.getMessage, ex)
            } finally {
                if (buffer != null) {
                    buffer.clear()
                }
            }
        }
    }

    /**
      * 反序列化
      * 此处 注意null 和T 不适配的问题， 有两种方法，一种加泛型的界定，
      * 另一种把返回值封装成Option 类型
      *
      * Null 是所有继承了Object 类的子类，
      * T >: Null 表示的是scala 泛型的定义， 即T 必须是Null 的父类 或者 Null 本身，这是scala泛型下界的定义  Null 只有一个对象， null
      * T <: U 表示的是 T 必须是U 的子类或者U 本省， 这个是scala 上界的定义
      *
      * @param data 需要反序列化的实际数据
      * @param clazz 反序列化之后的类型
      * @tparam T
      * @return
      */
    def deserializer[T >: Null](data:Array[Byte], clazz:Class[T]):T = {
        try {
            if (data != null) {
                val obj:T = objenesis.newInstance(clazz)
                val schema:Schema[T] = getSchema(clazz)
                ProtostuffIOUtil.mergeFrom(data, obj, schema)
                obj
            } else {
                null
            }
        } catch {
            case ex:Exception =>
                ex.printStackTrace()
                null
        }
    }

//    /**
//      * 反序列化
//      * @param data 需要反序列化的实际数据
//      * @param clazz 反序列化之后的类型
//      * @tparam T
//      * @return
//      */
//    def deserializer[T](data:Array[Byte], clazz:Class[T]):Option[T] = {
//        try {
//            if (data != null) {
//                val obj:T = objenesis.newInstance(clazz)
//                val schema:Schema[T] = getSchema(clazz)
//                ProtostuffIOUtil.mergeFrom(data, obj, schema)
//                Option(obj)
//            } else {
//                None
//            }
//        } catch {
//            case ex:Exception =>
//                ex.printStackTrace()
//                None
//        }
//    }

}



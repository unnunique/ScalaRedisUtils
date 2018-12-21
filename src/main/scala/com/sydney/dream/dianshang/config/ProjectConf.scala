package com.sydney.dream.dianshang.config

import java.util.Properties

/**
  * 获取properties 中的属性
  *
  * @Autor 小生有画说
  */
object ProjectConf {
    // 配置文件对象
    val prop = new Properties
    try {
        val in = ProjectConf.getClass
            .getClassLoader
            .getResourceAsStream("project-conf.properties")
        prop.load(in)
    } catch {
        case e: Exception => e.printStackTrace()
    }

    /**
      * 获取执行key 的对应的值
      * @param key key，需要取的值得key
      * @return 返回key对应的值 key=value
      */
    def getString(key: String): String = {
        prop.getProperty(key)
    }

    /**
      * 获取指定键的值，并转化成Boolean
      * @param key 键值
      * @return value转化后的Boolean 对象
      */
    def getInterger(key: String): Int = {
        val value = getString(key)
        try {
            Integer.valueOf(value)
        } catch {
            case e: Exception =>
                e.printStackTrace()
                Integer.MIN_VALUE
        }
    }

    /**
      * 获取指定键的值，并转化成Boolean
      * @param key  键对象
      * @return Boolean对象
      */
    def getBoolean(key: String): Boolean = {
        val value = getString(key)
        try {
            java.lang.Boolean.valueOf(value)
        } catch {
            case e: Exception =>
                e.printStackTrace()
                false
        }
    }
}

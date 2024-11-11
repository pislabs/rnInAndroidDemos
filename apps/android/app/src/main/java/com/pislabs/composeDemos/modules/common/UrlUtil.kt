package com.pislabs.composeDemos.modules.common

import android.net.Uri
import java.net.URLEncoder

/**
 * UrlUtil
 * @author rayl
 * @e-mail liuyi86@zto.com
 * @date 2024/11/6 18:17
 */
object UrlUtil {
    /**
     * 判断是否http url
     */
    fun isHttpUrl(url: String?): Boolean {
        if (url.isNullOrEmpty()) return false
        if (url.startsWith("http://") || url.startsWith("https://")) return true
        return false
    }

    /**
     * 获取url origin部分
     */
    fun getOrigin(url: String?): String? {
        return if (url != null && url.contains("?")) {
            url.substring(0, url.indexOf("?"))
        } else url
    }

    /**
     * 获取指定查询参数
     */
    fun getQueryParameter(url: String?, key: String): String? {
        if (!isHttpUrl(url)) return null
        return Uri.parse(url).getQueryParameter(key)
    }

    /**
     * 新增查询参数
     */
    fun addQueryParameter(url: String, queryMap: Map<String, Any>?): String {
        if (queryMap.isNullOrEmpty()) return url
        if (!isHttpUrl(url)) return url
        val builder = Uri.parse(url).buildUpon()
        for (key in queryMap.keys) {
            queryMap[key]?.let {
                builder.appendQueryParameter(encodeUrl(key), encodeUrl(it.toString()))
            }
        }
        return builder.build().toString()
    }

    /**
     * 数据进行url编码
     */
    fun encodeUrl(data: String?): String {
        data ?: return ""
        return try {
            URLEncoder.encode(data, "UTF-8")
        } catch (e: Exception) {
            e.printStackTrace()
            data
        }
    }
}

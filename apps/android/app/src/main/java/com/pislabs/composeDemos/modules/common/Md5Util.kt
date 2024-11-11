package com.pislabs.composeDemos.modules.common

import android.text.TextUtils
import java.io.File
import java.io.FileInputStream
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Md5Util
 * @author rayl
 * @e-mail liuyi86@zto.com
 * @date 2024/11/7 15:11
 */
object MD5Util {
    /**
     * 计算字符串的MD5值
     */
    fun getMD5String(str: String): String? {
        try {
            // 生成一个MD5加密计算摘要
            val md = MessageDigest.getInstance("MD5")
            // 计算md5函数
            md.update(str.toByteArray())
            // digest()最后确定返回md5 hash值，返回值为8位字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            //一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方）
            return BigInteger(1, md.digest()).toString(16)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    /**
     * 比较md5是否相等，去除间隔符等，这里
     */
    fun isEqualsMD5(fromMd5: String?, toMd5: String?): Boolean {
        if (TextUtils.isEmpty(fromMd5)) {
            return false
        }

        return fromMd5.equals(toMd5, true)
    }

    /**
     * 获取文件md5值
     */
    fun getFileMD5(file: File): String? {
        if (!file.isFile) {
            return ""
        }
        var digest: MessageDigest? = null
        var stream: FileInputStream? = null
        val buffer = ByteArray(1024)
        var len: Int
        try {
            digest = MessageDigest.getInstance("MD5")
            stream = FileInputStream(file)
            while ((stream.read(buffer, 0, 1024).also { len = it }) != -1) {
                digest.update(buffer, 0, len)
            }
            stream.close()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return bytesToHexString(digest.digest())
    }

    /**
     * 二进制转字符串
     */
    private fun bytesToHexString(src: ByteArray?): String? {
        val stringBuilder = StringBuilder("")
        if (src == null || src.isEmpty()) {
            return null
        }
        for (i in src.indices) {
            val v = src[i].toInt() and 0xFF
            val hv = Integer.toHexString(v)
            if (hv.length < 2) {
                stringBuilder.append(0)
            }
            stringBuilder.append(hv)
        }
        return stringBuilder.toString()
    }
}

package com.pislabs.composeDemos.modules.fs

import android.text.TextUtils
import java.io.File
import java.io.FileInputStream
import java.security.MessageDigest

/**
 * FileUtil
 * @author rayl
 * @e-mail liuyi86@zto.com
 * @date 2024/11/7 11:08
 */
object FsManager {
    const val B: Long = 8
    const val KB: Long = 1024 * B
    const val MB: Long = 1024 * KB

    const val SIZETYPE_B: Int = 1
    const val SIZETYPE_KB: Int = 2
    const val SIZETYPE_MB: Int = 3
    const val SIZETYPE_GB: Int = 4

    /**
     * 检查文件是否存在
     */
    fun isExist(filePath: String?): Boolean {
        if (TextUtils.isEmpty(filePath)) {
            return false
        }

        try {
            val file = File(filePath!!)
            if (file.exists()) {
                return true
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 删除指定路径文件/文件夹
     */
    fun delete(filePath: String?) {
        if (TextUtils.isEmpty(filePath)) {
            return
        }

        val file = File(filePath!!)

        delete(file)
    }

    /**
     * 删除多个文件/文件夹
     */
    fun delete(files: List<File?>) {
        for (file in files) {
            delete(file)
        }
    }

    /**
     * 删除文件/文件夹
     */
    fun delete(file: File?) {
        if (file == null || !file.exists()) return

        if (file.isFile) {
            file.delete()
            return
        }

        if (file.isDirectory) {
            val childFiles = file.listFiles()
            if (childFiles == null || childFiles.isEmpty()) {
                file.delete()
                return
            }
            for (i in childFiles.indices) {
                delete(childFiles[i])
            }
            file.delete()
        }
    }
}

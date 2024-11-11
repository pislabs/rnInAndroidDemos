package com.pislabs.composeDemos.modules.turn.bundle

import android.app.Application
import android.text.TextUtils
import com.pislabs.composeDemos.modules.turn.TurnLog
import com.pislabs.composeDemos.modules.turn.common.TurnConstants.RN_CACHE_FOLDER
import com.pislabs.composeDemos.modules.turn.common.TurnErrorCode
import com.pislabs.composeDemos.modules.turn.common.TurnException
import com.pislabs.composeDemos.modules.fs.FsManager
import com.pislabs.composeDemos.modules.common.MD5Util
import com.pislabs.composeDemos.modules.common.UrlUtil
import com.pislabs.composeDemos.modules.net.NetManager
import com.pislabs.composeDemos.modules.turn.common.LaunchOptions
import okhttp3.ResponseBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

/**
 * TurnBundleManager
 * @author rayl
 * @e-mail liuyi86@zto.com
 * @date 2024/11/7 13:04
 */
object TurnBundleManager {
    private const val TAG = "TurnBundleManager"

    private lateinit var application: Application

    fun init(context: Application) {
        application = context
    }

    private val cacheDir : String
        get() = application.cacheDir.absolutePath + File.separator + RN_CACHE_FOLDER

    private val downloadCacheDir: String
        get() = cacheDir + File.separator + "package" + File.separator + "preload";

    private val downloadTmpDir: String
        get() = downloadCacheDir + "tmp"

    /**
     * 获取Bundle文件
     */
    fun getBundleFile(
        url: String,
        callback: ((filePath: String?, error: TurnException?) -> Unit)? = null
    ) {
        TurnLog.d("$TAG getBundleFile called url=$url")
        if (url.isEmpty()) {
            TurnLog.e("$TAG getBundleFile called but url is empty")
            callback?.invoke(null, TurnException(TurnErrorCode.INVALID_URL, "无效的URL"))
            return
        }

        if (!UrlUtil.isHttpUrl(url)) {
            TurnLog.e("$TAG getBundleFile called url=$url but is not valid url")
            callback?.invoke(url, null)
            return
        }

        val originUrl = UrlUtil.getOrigin(url)
        if (originUrl.isNullOrEmpty()) {
            TurnLog.e("$TAG getBundleFile called url=$url but originUrl is empty")
            callback?.invoke(null, TurnException(TurnErrorCode.INVALID_URL, "无效的URL"))
            return
        }

        val cacheFileExist = isBundleFileExists(originUrl)

        if (cacheFileExist) {
            callback?.invoke(originUrl, null)
            return
        }

        LaunchOptions.parseUrl(url)?.let {
            downLoadFile(originUrl, cacheDir, it.md5)
        }
    }

    /**
     * 判断文件是否已存在
     */
    private fun isBundleFileExists(url: String): Boolean {
        val fileName = MD5Util.getMD5String(url)

        TurnLog.d("$TAG 文件校验: url:$url MD5:$fileName path:$url")

        if (fileName == null) {
            return false
        }

        // 指定目录文件
        val file = File(cacheDir, fileName)

        // 缓存文件
        val cacheDir = File(application.filesDir, downloadCacheDir)

        val cacheFile = File(cacheDir, fileName)
        return file.exists() || cacheFile.exists()
    }

    /**
     * 文件下载
     *
     * @param url  下载链接
     * @param path 缓存目录
     * @param name 文件名称
     * @param md5  modify 20221118 增加文件md5校验
     * @return 文件信息
     */
    private fun downLoadFile(url: String, path: String, md5: String?): File? {
        val fileName = MD5Util.getMD5String(url) ?: return null

        TurnLog.d("$TAG 文件开始下载")

        // 下载文件临时缓存目录创建
        val tmpCacheDirectory = File(application.filesDir, downloadTmpDir)
        tmpCacheDirectory.mkdirs()

        // 清除临时文件
        val tmpFile = File(tmpCacheDirectory, "$fileName.tmp")
        if (tmpFile.exists()) {
            FsManager.delete(tmpFile)
            TurnLog.d("$TAG 临时文件缓存移除")
        }
        try {
            val response = NetManager.download(url)

            if (response == null || !response.isSuccessful || response.body == null) {
                TurnLog.e("$TAG 文件下载失败")
                return null
            }

            // 临时文件保存到本地
            val tmpDownLoadFile: File =
                saveFile(response.body, tmpCacheDirectory.absolutePath, tmpFile.name)
            // 校验临时文件状态
            if (!tmpDownLoadFile.exists()) {
                TurnLog.e("$TAG 临时文件下载失败")
                return null
            }
            TurnLog.d("$TAG 临时文件下载成功")

            // 文件移动到缓存目录
            val cacheFile = copyFile(tmpDownLoadFile.absolutePath, File(path, fileName).absolutePath)
            if (cacheFile == null) {
                TurnLog.e("$TAG 缓存文件失败")
                return null
            }
            TurnLog.d("$TAG 文件下载完成")

            // 移除临时文件
            FsManager.delete(tmpDownLoadFile)
            TurnLog.d("$TAG 移除下载临时文件")

            /**
             * 下载完成后,获取文件md5值和接口返回的md5进行比较
             */
            //md5校验 和服务端返回的md5比较 不一致则删除 本次下载失败
            if (!TextUtils.isEmpty(md5)) {
                val fileMd5 = MD5Util.getFileMD5(cacheFile)

                if (!MD5Util.isEqualsMD5(md5, fileMd5)) {
                    FsManager.delete(cacheFile)
                    TurnLog.e("$TAG MD5校验失败")
                }

                return null
            }

            return cacheFile
        } catch (e: IOException) {
            TurnLog.e("$TAG 文件下载异常", e)
            return null
        }
    }


    /**
     * 文件拷贝
     *
     * @param fromPath 当前目录
     * @param toPath   目标目录
     * @return 压缩完成的文件
     */
    private fun copyFile(fromPath: String, toPath: String): File? {
        TurnLog.d("$TAG 文件开始拷贝")
        val oldFile = File(fromPath)
        if (!oldFile.exists()) {
            TurnLog.d("$TAG 文件不存在")
            return null
        }
        val file = File(toPath)
        if (!file.exists()) {
            file.parentFile?.mkdirs()
            try {
                file.createNewFile()
            } catch (e: IOException) {
                TurnLog.e("$TAG 拷贝目标文件创建失败", e)
                return null
            }
        }
        var inputStream: FileInputStream? = null
        var out: FileOutputStream? = null
        try {
            inputStream = FileInputStream(fromPath)
            out = FileOutputStream(file)
            var len: Int
            val buffer = ByteArray(1024)
            while ((inputStream.read(buffer).also { len = it }) != -1) {
                out.write(buffer, 0, len)
                out.flush()
            }
            TurnLog.d("$TAG 文件拷贝成功")
            return file
        } catch (e: Throwable) {
            TurnLog.e("$TAG 文件拷贝失败", e)
            FsManager.delete(file)
            return null
        } finally {
            if (out != null) {
                try {
                    out.close()
                } catch (e: IOException) {
                    TurnLog.e("$TAG 文件拷贝失败，资源关闭", e)
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close()
                } catch (e: IOException) {
                    TurnLog.e("$TAG 文件拷贝失败，资源关闭", e)
                }
            }
        }
    }

    /**
     * 文件保存
     *
     * @param response 文件内容
     * @param path     保存目录
     * @param name     文件名称
     * @return 保存后的文件
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun saveFile(response: ResponseBody?, path: String, name: String): File {
        var stream: InputStream? = null
        val buf = ByteArray(2048)
        var fos: FileOutputStream? = null
        try {
            stream = response!!.byteStream()
            val dir = File(path)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            val file = File(dir, name)
            fos = FileOutputStream(file)
            var len: Int
            while ((stream.read(buf).also { len = it }) != -1) {
                fos.write(buf, 0, len)
            }
            fos.flush()
            return file
        } finally {
            response?.close()
            if (stream != null) {
                try {
                    stream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            if (fos != null) {
                try {
                    fos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
}

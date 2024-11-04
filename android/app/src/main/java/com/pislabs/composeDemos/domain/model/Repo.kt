package com.pislabs.composeDemos.domain.model

/**
 * Repo
 * @author rayl
 * @e-mail liuyi86@zto.com
 * @date 2024/10/22
 * Copyright © 2024 ZTO. All rights reserved
 */
data class Repo (
    val id: Int,    // 仓库id
    val name: String,   // 仓库名称
    val private: Boolean = false,   // 是否私有库
    var nodeId: String? = null, // 节点id
    var fullName: String? = null,   // 全名
    var description: String? = null,    // 描述
    var stargazersCount: Int = 0,   // star数量
    var forksCount: Int = 0,    // fork数量
    var url: String? = null,    // api url
    var gitUrl: String? = null, // git url
    var cloneUrl: String? = null,   // 克隆 url
    var size: Int? = null,  // 大小
    var homepage: String? = null,   // 首页
    var owner: User? = null,    // 所有者
    var language: String? = null    // 语言
)

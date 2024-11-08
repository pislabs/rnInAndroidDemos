package com.pislabs.composeDemos.domain.model

/**
 * User
 * @author rayl
 * @e-mail liuyi86@zto.com
 * @date 2024/10/22
 * Copyright © 2024 ZTO. All rights reserved
 */
data class User(
    val id: Int,    // 用户id
    val nodeId: String, // 节点id
    val login: String,  // 登录名
    val type: String,   // 用户类型
    var avatarUrl: String? = "",    // 头像url
    var url: String? = "",  // api url
    var htmlUrl: String? = ""   // html url
)

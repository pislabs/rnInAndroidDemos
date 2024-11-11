package com.pislabs.composeDemos.modules.turn.react

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.facebook.react.modules.core.PermissionAwareActivity
import com.facebook.react.modules.core.PermissionListener
import com.pislabs.composeDemos.modules.turn.TurnView

/**
 * TurnFragment
 * @author rayl
 * @e-mail liuyi86@zto.com
 * @date 2024/11/5 12:59
 */
class TurnFragment: Fragment(), PermissionAwareActivity {
    private var mPermissionListener: PermissionListener? = null

    private var mReactRootView: TurnView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        check(this.mReactRootView == null) { "Cannot loadApp while app is already running." }

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun checkPermission(permission: String, pid: Int, uid: Int): Int {
        return this.requireActivity().checkPermission(permission, pid, uid)
    }

    override fun checkSelfPermission(permission: String): Int {
        return this.requireActivity().checkSelfPermission(permission)
    }

    override fun requestPermissions(permissions: Array<out String>, requestCode: Int, listener: PermissionListener?) {
        this.mPermissionListener = listener
        this.requestPermissions(permissions, requestCode)
    }
}

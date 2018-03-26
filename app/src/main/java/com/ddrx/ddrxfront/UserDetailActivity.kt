package com.ddrx.ddrxfront

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MotionEvent
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import com.ddrx.ddrxfront.Model.UserDetailModel
import com.ddrx.ddrxfront.Utilities.UserInfoPreference
import kotlinx.android.synthetic.main.activity_user_detail.*
import com.ddrx.ddrxfront.Utilities.ToastUtil.prompt
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by dokym on 2018/3/24.
 */
class UserDetailActivity : AppCompatActivity() {

    lateinit var userInfoPreference: UserInfoPreference

    lateinit var mRecycleView: RecyclerView
    lateinit var mUserDetailAdapter: UserDetailAdapter
    var mDataList: MutableList<UserDetailModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        mRecycleView = recycle_user_detail
        mRecycleView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mRecycleView.setHasFixedSize(true)
        mUserDetailAdapter = UserDetailAdapter(mDataList)
        mUserDetailAdapter.mOnItemClickListener = object : UserDetailAdapter.Companion.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int?) {
                when (position) {
                    0 -> showNickNameDialog()
                    1 -> showSexDialog()
                    2 -> showBirthdayDialog()
                }
            }
        }
        mRecycleView.adapter = mUserDetailAdapter
        userInfoPreference = UserInfoPreference(this)
    }

    fun showNickNameDialog() {
        val editText = EditText(this)
        AlertDialog.Builder(this)
                .setTitle("修改昵称")
                .setView(editText)
                .setPositiveButton("确定", { dialog: DialogInterface?, which: Int ->
                    if (editText.text.isEmpty())
                        prompt(this@UserDetailActivity, "昵称不能为空！")
                    else {
                        prompt(this@UserDetailActivity, "修改成功")
                        userInfoPreference.userInfo.nickname = editText.text.trim().toString()
                    }
                })
                .setNegativeButton("取消", { dialog, which ->
                    dialog.dismiss()
                })
                .show()
    }

    fun showSexDialog() {
        var choice: Int
        if (userInfoPreference.userInfo.sex == "男")
            choice = 0
        else
            choice = 1
        AlertDialog.Builder(this)
                .setTitle("修改性别")
                .setSingleChoiceItems(arrayOf("男", "女"), choice, DialogInterface.OnClickListener({ dialog: DialogInterface?, which: Int ->
                    choice = which
                }))
                .setPositiveButton("确定", { dialog: DialogInterface?, which: Int ->
                    when (choice) {
                        0 -> userInfoPreference.userInfo.sex = "男"
                        1 -> userInfoPreference.userInfo.sex = "女"
                    }
                    prompt(this@UserDetailActivity, "修改成功")
                })
                .setNegativeButton("取消", { dialog, which ->
                    dialog.dismiss()
                })
                .show()
    }

    fun showBirthdayDialog() {

    }

    inner class SetDateDialog : DialogFragment(), DatePickerDialog.OnDateSetListener {
        override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
            var calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            userInfoPreference.userInfo.birthday = calendar.time
        }

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val calendar = Calendar.getInstance()
            var year = calendar.get(Calendar.YEAR)
            var month = calendar.get(Calendar.MONTH)
            var day = calendar.get(Calendar.DAY_OF_MONTH)
            return DatePickerDialog(activity, this, year, month, day)
        }
    }

    init {
        mDataList.add(UserDetailModel("昵称", "邹小凯"))
        mDataList.add(UserDetailModel("性别", "男"))
        mDataList.add(UserDetailModel("生日", "1997-05-14"))
        mDataList.add(UserDetailModel("所在城市", "南京"))
        mDataList.add(UserDetailModel("个性签名", "我就是我"))
    }
}
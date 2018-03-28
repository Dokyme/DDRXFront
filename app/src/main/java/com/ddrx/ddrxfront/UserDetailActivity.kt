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
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import com.ddrx.ddrxfront.Model.UserDetailModel
import com.ddrx.ddrxfront.Utilities.*
import kotlinx.android.synthetic.main.activity_user_detail.*
import com.ddrx.ddrxfront.Utilities.ToastUtil.prompt
import java.text.SimpleDateFormat
import java.util.*
import com.ddrx.ddrxfront.Utilities.URLHelper.*
import okhttp3.*
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

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
                    3 -> showCityDialog()
                }
            }
        }
        mRecycleView.adapter = mUserDetailAdapter
        userInfoPreference = UserInfoPreference(this)
        mDataList.add(UserDetailModel("昵称", userInfoPreference.userInfo.nickname))
        mDataList.add(UserDetailModel("性别", userInfoPreference.userInfo.sex))
        mDataList.add(UserDetailModel("生日", SimpleDateFormat("yyyy-MM-dd").format(userInfoPreference.userInfo.birthday)))
        mDataList.add(UserDetailModel("所在城市", userInfoPreference.userInfo.city))
        mDataList.add(UserDetailModel("个性签名", userInfoPreference.userInfo.brief))

        btn_detail_save.setOnClickListener(OnConfirmBtnClickedListener())
    }

    fun showNickNameDialog() {
        val editText = EditText(this)
        editText.setText(userInfoPreference.userInfo.nickname)
        AlertDialog.Builder(this)
                .setTitle("修改昵称")
                .setView(editText)
                .setPositiveButton("确定", { dialog: DialogInterface?, which: Int ->
                    if (editText.text.isEmpty())
                        prompt(this@UserDetailActivity, "昵称不能为空！")
                    else {
                        prompt(this@UserDetailActivity, "修改成功")
                        userInfoPreference.userInfo.nickname = editText.text.trim().toString()
                        mDataList[0].value = userInfoPreference.userInfo.nickname
                        mUserDetailAdapter.notifyItemChanged(0)
                    }
                })
                .setNegativeButton("取消", { dialog, which ->
                    dialog.dismiss()
                })
                .show()
    }

    fun showCityDialog() {
        val editText = EditText(this)
        editText.setText(userInfoPreference.userInfo.city)
        AlertDialog.Builder(this)
                .setTitle("修改城市")
                .setView(editText)
                .setPositiveButton("确定", { dialog: DialogInterface?, which: Int ->
                    if (editText.text.isEmpty())
                        prompt(this@UserDetailActivity, "城市不能为空！")
                    else {
                        prompt(this@UserDetailActivity, "修改成功")
                        userInfoPreference.userInfo.city = editText.text.trim().toString()
                        mDataList[3].value = userInfoPreference.userInfo.city
                        mUserDetailAdapter.notifyItemChanged(3)
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
                    mDataList[1].value = userInfoPreference.userInfo.sex
                    mUserDetailAdapter.notifyItemChanged(1)
                })
                .setNegativeButton("取消", { dialog, which ->
                    dialog.dismiss()
                })
                .show()
    }

    fun showBirthdayDialog() {
        DatePickerDialog(this, SetDateDialog(), 2000, 1, 1).show()
        mUserDetailAdapter.notifyDataSetChanged()
    }

    inner class SetDateDialog : DialogFragment(), DatePickerDialog.OnDateSetListener {
        override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            userInfoPreference.userInfo.birthday = calendar.time
            mDataList[2].value = SimpleDateFormat("yyyy-MM-dd").format(calendar.time)
            mUserDetailAdapter.notifyItemChanged(2)
        }

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            return DatePickerDialog(activity, this, year, month, day)
        }
    }

    inner class OnConfirmBtnClickedListener : View.OnClickListener {
        override fun onClick(v: View?) {
            val client = OKHttpClientWrapper.getInstance(this@UserDetailActivity)
            val formBody = FormBody.Builder()
                    .add(MAC, MacAddressUtil(this@UserDetailActivity).macAddress)
                    .add(USER_ID, "${userInfoPreference.userInfo.id}")
                    .add(NICK_NAME, userInfoPreference.userInfo.nickname)
                    .add(SEX, userInfoPreference.userInfo.sex)
                    .add(BIRTHDAY, SimpleDateFormat("yyyy-MM-dd").format(userInfoPreference.userInfo.birthday))
                    .add(CITY, userInfoPreference.userInfo.city)
                    .add(BRIEF, userInfoPreference.userInfo.brief)
                    .build()
            val request = Request.Builder()
                    .url(URLHelper("/user/social_alter").build())
                    .post(formBody)
                    .build()
            client.newCall(request)
                    .enqueue(object : Callback {
                        override fun onFailure(call: Call?, e: IOException?) {
                            prompt(this@UserDetailActivity, "网络环境错误，请重试")
                        }

                        override fun onResponse(call: Call?, response: Response?) {
                            val obj = JSONObject(response?.body().toString())
                            when (obj.get("code") as Int) {
                                600 -> ToastUtil.prompt(this@UserDetailActivity, "用户不存在！")
                                601 -> ToastUtil.prompt(this@UserDetailActivity, "密码错误！")
                                1000 -> ToastUtil.prompt(this@UserDetailActivity, "服务器错误。")
                                1003 -> ToastUtil.prompt(this@UserDetailActivity, "无效的Cookies。")
                                1004 -> ToastUtil.prompt(this@UserDetailActivity, "MAC地址错误。")
                                0 -> {
                                    prompt(this@UserDetailActivity, "修改成功")
                                    try {
                                        JSONToEntity.getUserDetailInfo(this@UserDetailActivity, obj)
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                        prompt(this@UserDetailActivity, "修改失败")
                                    }
                                }
                            }
                        }
                    })
        }
    }
}
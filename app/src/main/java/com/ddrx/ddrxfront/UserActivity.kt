package com.ddrx.ddrxfront

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.ddrx.ddrxfront.Model.TimeLineModel
import com.ddrx.ddrxfront.Utilities.ToastUtil.prompt
import com.yanzhenjie.album.Action
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.durban.Durban
import kotlinx.android.synthetic.main.activity_user.*

/**
 * Created by dokym on 2018/3/23.
 */
class UserActivity : AppCompatActivity() {
    lateinit var mRecyclerView: RecyclerView
    lateinit var mTimeLineAdapter: TimeLineAdapter
    var mDataList: MutableList<TimeLineModel> = ArrayList()

    companion object {
        val CROP_IMAGE = 1
        val PERMISSION_READ_STORAGE = 2
        val PERMISSION_WRITE_STORAGE = 3
    }

    init {
        mDataList.add(TimeLineModel("复习了：二叉树", "2018-3-24"))
        mDataList.add(TimeLineModel("复习了：KMP算法", "2018-3-25"))
        mDataList.add(TimeLineModel("复习了：快速排序算法", "2018-3-26"))
        mDataList.add(TimeLineModel("复习了：《楚辞-离骚》", "2018-3-27"))
        mDataList.add(TimeLineModel("复习了：TCP/IP-Cubic算法", "2018-3-27"))
        mDataList.add(TimeLineModel("复习了：TCP/IP-Reno算法", "2018-3-27"))
        mDataList.sortByDescending { e -> e.date }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        mRecyclerView = view_recycle
        mRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.setHasFixedSize(true)
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_READ_STORAGE)
            return
        }
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_WRITE_STORAGE)
            return
        }

        initEvent()
        initView()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_READ_STORAGE || requestCode == PERMISSION_WRITE_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                prompt(this, "获得读写本地文件权限")
            else
                prompt(this, "获得读写本地文件失败，请到系统设置中手动授予读写外部存储权限")
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CROP_IMAGE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val images = Durban.parseResult(data!!)
                    prompt(this, images[0])
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_user_detail_actionbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.btn_edit_detail -> {
                startActivity(Intent(this, UserDetailActivity::class.java))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun initEvent() {
        user_image.setOnClickListener({ view: View? ->
            kotlin.run {
                Album.album(this)
                        .singleChoice()
                        .onResult(object : Action<ArrayList<AlbumFile>> {
                            override fun onAction(requestCode: Int, result: ArrayList<AlbumFile>) {
                                var list = arrayListOf<String>(result[0].path)
                                Durban.with(this@UserActivity)
                                        .title("裁剪")
                                        .inputImagePaths(list)
                                        .outputDirectory("/sdcard/")
                                        .maxWidthHeight(256, 256)
                                        .compressFormat(Durban.COMPRESS_JPEG)
                                        .compressQuality(90)
                                        .gesture(Durban.GESTURE_SCALE)
                                        .requestCode(CROP_IMAGE)
                                        .start()
                            }
                        })
                        .start()
            }
        })
    }

    fun initView() {
        mTimeLineAdapter = TimeLineAdapter(mDataList)
        mRecyclerView.adapter = mTimeLineAdapter
    }
}
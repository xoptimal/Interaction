package com.xoptimal.interactiondemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.xoptimal.rcvhelper.RcvHelper
import com.xoptimal.rcvhelper.provider.BaseViewHolder
import com.xoptimal.rcvhelper.provider.ExItemViewBinder
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        fab_add.setOnClickListener {
            val mList: MutableList<Any> = mutableListOf()
            var x = 5
            while (x > 0) {
                mList.add(Date().time.toString())
                x--
            }
            mRcvHelper.addAll(mList)
        }
        fab_remove.setOnClickListener {
            mRcvHelper.remove(mRcvHelper.size - 1)
        }
        init()
    }

    lateinit var mRcvHelper: RcvHelper

    private fun init() {

        mRcvHelper = RcvHelper.Builder(findViewById(R.id.rcv)).setmSmartLoadMoreView(true).create()

        mRcvHelper.register(String::class.java, object : ExItemViewBinder<String>() {
            override fun getLayoutId(): Int {
                return R.layout.item
            }

            override fun onBindViewHolder(holder: BaseViewHolder, item: String) {
                holder.setText(R.id.tv_value, item)
            }
        })
        //initData()
        // var interaction = Interaction(this)
    }

    private fun initData() {
        var x = 20
        while (x > 0) {
            mRcvHelper.add(Date().time.toString())
            x--
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}

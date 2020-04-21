package com.gaoxianglong.uibestpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private val msgList = ArrayList<Msg>()
    private var adapter:MsgAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initMsg() // 初始数据
        recyclerView.layoutManager = LinearLayoutManager(this) // 设置RecyclerView的布局
        adapter = MsgAdapter(msgList) // 初始化适配器
        recyclerView.adapter = adapter // 设置适配器
        // 发送按钮点击事件
        button.setOnClickListener {
            val content = editText.text.toString() // 获取到editText中的内容
            // 当内容不为空的时候
            if (content.isNotEmpty()) {
                // 使用java中的Random类随机一个boolean值，将发送的消息随机变换
                val msg:Msg = if (Random().nextBoolean()) {
                    Msg(content,Msg.TYPE_SENT)
                }else{
                    Msg(content,Msg.TYPE_RECEIVED)
                }
                msgList.add(msg) // 将消息添加到消息列表中
                adapter?.notifyItemInserted(msgList.size-1) // 有新消息时
                recyclerView.scrollToPosition(msgList.size-1) // recyclerView定位到最后一条
                editText.setText("") // 清空
            }
        }
    }

    private fun initMsg() {
        val msg1 = Msg("发送消息,随机消息类型",Msg.TYPE_RECEIVED)
        msgList.add(msg1)
        val msg2 = Msg("这是发送的消息",Msg.TYPE_SENT)
        msgList.add(msg2)
        val  msg3 = Msg("这是收到的消息",Msg.TYPE_RECEIVED)
        msgList.add(msg3)
    }
}

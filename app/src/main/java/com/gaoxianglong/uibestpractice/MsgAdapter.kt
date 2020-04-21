package com.gaoxianglong.uibestpractice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MsgAdapter(val msgList: List<Msg>): RecyclerView.Adapter<MsgAdapter.MyViewHolder>() {
    /**
     * 内部类
     * 用于找到item布局中的控件
     */
    inner class MyViewHolder(view:View) :RecyclerView.ViewHolder(view){
        val textView: TextView = view.findViewById(R.id.textView)
    }

    /**
     * 将每一个消息的类型返回
     * 这样下面的viewType就能判断出当前item是哪一种类型的
     */
    override fun getItemViewType(position: Int): Int {
        val msg = msgList[position] // 获取到item对应的数据
        return msg.type // 将返回当前数据的消息类型返回给适配器
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MsgAdapter.MyViewHolder {
        val from = LayoutInflater.from(parent.context)
        val view:View
        // 当前这个函数的viewType参数就是我们上面返回给适配器的消息类型
        // 判断是哪一种类型
        view = if (viewType == Msg.TYPE_RECEIVED) {
            // 加载left的item布局
            from.inflate(R.layout.msg_left_item, parent, false)
        } else {
            // 加载right的item布局
            from.inflate(R.layout.msg_right_item, parent, false)
        }
        return MyViewHolder(view) // 将加载好的item布局传入内部类，然后将结果返回
    }

    override fun getItemCount()=msgList.size

    override fun onBindViewHolder(holder: MsgAdapter.MyViewHolder, position: Int) {
        // 将数据显示到对应item的控件上
        val msg = msgList[position]
        holder.textView.text = msg.content
    }
}
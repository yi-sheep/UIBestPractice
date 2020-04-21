package com.gaoxianglong.uibestpractice

class Msg(val content: String, val type: Int){
    /**
     * companion object
     * 这个的可以理解成编译器帮我们生成了一个Msg伴生类这个类中有两个常量
     * 这两个常量只能使用Msg这个伴生类来访问
     * 原本的这个类的实例是无法访问到的
     */
    companion object{
        const val TYPE_RECEIVED = 0 // 消息的类型为收到
        const val TYPE_SENT = 1 // 消息类型为发出
    }
}
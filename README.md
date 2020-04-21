# UIBestPractice

这个demo完成一个简单的聊天界面，用到RecyclerView、editText、Button等控件

[apk文件下载](https://ww.lanzous.com/ibp58qf)
连接失效了联系我QQ：1766816333

现在开始，首先我们要做成，有两种消息类型，一个是收到的消息显示在左边，另一个是发送的消息显示在右边。

怎么实现呢，使用recyclerView来显示每一条消息，两种类型的消息就需要两个item布局

左边的
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <TextView
        android:id="@+id/textView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="8dp"
        android:layout_marginLeft="8dp"
        android:layout_marginTop="8dp"
        android:layout_marginBottom="8dp"
        android:background="@drawable/message_left_original"
        android:text="TextView"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
</androidx.constraintlayout.widget.ConstraintLayout>
```
右边的
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <TextView
        android:id="@+id/textView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="8dp"
        android:layout_marginEnd="8dp"
        android:layout_marginRight="8dp"
        android:layout_marginBottom="8dp"
        android:background="@drawable/message_right_original"
        android:text="TextView"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
</androidx.constraintlayout.widget.ConstraintLayout>
```
我这里使用了Androidstudio制作.9的图片，具体制作方法百度上有很多。

接下来是数据类了，这里还有一个kotlin的语法
```kotlin
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
```
适配器
```kotlin
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
```
加载适配器和发送消息
```kotlin
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
```
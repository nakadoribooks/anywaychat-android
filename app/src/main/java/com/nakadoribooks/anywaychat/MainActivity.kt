package com.nakadoribooks.anywaychat

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Sampler
import android.support.annotation.RequiresApi
import android.util.Log
import android.widget.ListView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseReference




class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView;
    private lateinit var adapter:MessageListAdapter
    private var messageList:ArrayList<Message> = arrayListOf()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView) as ListView
        adapter = MessageListAdapter(this, messageList)
        listView.adapter = adapter

        val reference = FirebaseDatabase.getInstance().reference
        val testRef = reference.child("testMessage")
        testRef.setValue("Hello, World!")

        val chatId = "-Ksvf40l7KqOD_V1tTPk"

        val chatsRef = reference.child("chats")
        val messagesRef = reference.child("messages")
        chatsRef.child(chatId).child("messageList").addListenerForSingleValueEvent(object: ValueEventListener{

            override fun onDataChange(var1: DataSnapshot){
                val total:Long = var1.childrenCount
                var loaded:Long = 0
                val results:ArrayList<Message> = arrayListOf()
                for(child in var1.children){
                    Log.d("onDataChange child.key", child.key)
                    messagesRef.child(child.key).addListenerForSingleValueEvent(object: ValueEventListener{
                        override fun onDataChange(var1: DataSnapshot) {
                            results.add(Message(var1))
                            loaded++
                            if(total == loaded){
                                Log.d("onLoaded Message", "onLoaded Message:" + results.size)
                                results.sortBy { s -> s.createdAt }
                                this@MainActivity.messageList.clear()
                                for(i in 0..results.count()-1){
                                    this@MainActivity.messageList.add(results[i])
                                }

                                this@MainActivity.adapter.notifyDataSetChanged()
                            }
                        }
                        override fun onCancelled(var1: DatabaseError){

                        }
                    })
                }
            }

            override fun onCancelled(var1: DatabaseError){
                Log.e("onCancelled", var1.toString())
            }
        })

    }

}

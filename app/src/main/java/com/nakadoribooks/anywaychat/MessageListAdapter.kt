package com.nakadoribooks.anywaychat

import android.content.Context
import android.widget.ArrayAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView


/**
 * Created by kawase on 2017/09/02.
 */
class MessageListAdapter(context: Context, messageList:ArrayList<Message>) : BaseAdapter(){

    private var context = context
    private var messageList = messageList

    init {
        print("inited MessageListAdapter")
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view:View
        if (convertView == null){
            view = inflater.inflate(R.layout.message_list_item, null);
        }else{
            view = convertView
        }

        val message = getItem(position) as Message
        view.findViewById<TextView>(R.id.message).setText(message.message)
        view.findViewById<TextView>(R.id.username).setText(message.userName)
        view.findViewById<TextView>(R.id.datelabel).setText(message.agoText)

        return view
    }

    override fun getItem(p0: Int): Any {
        return messageList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return messageList.size
    }


    private val inflater = LayoutInflater.from(context)


}
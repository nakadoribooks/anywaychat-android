package com.nakadoribooks.anywaychat

import android.text.format.DateFormat
import com.google.firebase.database.DataSnapshot
import java.util.*

/**
 * Created by kawase on 2017/09/02.
 */
class Message(snapshot:DataSnapshot) {

    var snapshot = snapshot

    val key: String = snapshot.key

    var message:String = ""
    get() {
        val v = snapshot.child("message").value
        if(v !is String){
            return ""
        }

        return v
    }

    var userId:String = ""
        get() {
            val v = snapshot.child("userId").value
            if(v !is String){
                return ""
            }

            return v
        }

    var userName:String = ""
        get() {
            val v = snapshot.child("userName").value
            if(v !is String){
                return ""
            }

            return v
        }

    var platform:String = ""
        get() {
            val v = snapshot.child("platform").value
            if(v !is String){
                return ""
            }

            return v
        }

    var createdAt:Long = 0
        get() {
            val v = snapshot.child("createdAt").value
            if(v !is Long){
                return 0
            }

            return v
        }

    var agoText:String = ""
    get() {
        val timestamp = createdAt
        val cal = Calendar.getInstance(Locale.JAPAN)

        val now = cal.timeInMillis / 1000

        cal.setTimeInMillis(timestamp)
        val created = cal.timeInMillis / 1000

        val dTime = now - created

        if(dTime < 60){
            return "${dTime}秒前"
        }else if(dTime < 60*60){
            return "${dTime/60}分前"
        }else if(dTime < 60*60*24){
            return "${dTime / 60 / 60}時間前"
        }else{
            return "${dTime / 60 / 60 / 24}日前"
        }

    }
}
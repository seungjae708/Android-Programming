package com.example.week13

import android.Manifest
import android.content.BroadcastReceiver
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.CallLog
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var callLogAdapter: CallLogAdapter
    private lateinit var textViewBroadcast: TextView
    private lateinit var broadcastReceiver: BroadcastReceiver
    private lateinit var telephonyManager: TelephonyManager
    private lateinit var phoneStateListener: PhoneStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewBroadcast = findViewById(R.id.textViewBroadcast)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        callLogAdapter = CallLogAdapter(mutableListOf())
        recyclerView.adapter = callLogAdapter

        telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CALL_LOG), PERMISSIONS_REQUEST_READ_CALL_LOG)
        } else {
            readCallLogs()
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_PHONE_STATE), PERMISSIONS_REQUEST_READ_PHONE_STATE)
        } else {
            setupPhoneStateListener()
        }

        MyBroadcastReceiver()
    }

    private fun setupPhoneStateListener() {
        phoneStateListener = object : PhoneStateListener() {
            override fun onCallStateChanged(state: Int, phoneNumber: String?) {
                super.onCallStateChanged(state, phoneNumber)
                if (state == TelephonyManager.CALL_STATE_IDLE) {
                    readCallLogs()
                }
            }
        }
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE)
    }

    private fun readCallLogs() {
        val callLogItems = mutableListOf<CallLogItem>()
        val contentResolver: ContentResolver = contentResolver
        val uri: Uri = CallLog.Calls.CONTENT_URI
        val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)

        cursor?.use {
            while (it.moveToNext()) {
                val number = it.getString(it.getColumnIndexOrThrow(CallLog.Calls.NUMBER))
                val callType = it.getString(it.getColumnIndexOrThrow(CallLog.Calls.TYPE))
                val callTypeStr = when (Integer.parseInt(callType)) {
                    CallLog.Calls.OUTGOING_TYPE -> "OUTGOING"
                    CallLog.Calls.INCOMING_TYPE -> "INCOMING"
                    CallLog.Calls.MISSED_TYPE -> "MISSED"
                    else -> ""
                }
                callLogItems.add(CallLogItem(number, callTypeStr))
            }
        }
        callLogAdapter.updateCallLogs(callLogItems)
    }

    private fun MyBroadcastReceiver() {
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if ("ACTION_MY_BROADCAST" == intent.action) {
                    textViewBroadcast.text = "ACTION_MY_BROADCAST"
                }
            }
        }
        val filter = IntentFilter("ACTION_MY_BROADCAST")
        ContextCompat.registerReceiver(this, broadcastReceiver, filter, ContextCompat.RECEIVER_EXPORTED)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_READ_CALL_LOG) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                readCallLogs()
            }
        } else if (requestCode == PERMISSIONS_REQUEST_READ_PHONE_STATE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupPhoneStateListener()
            }
        }
    }

    companion object {
        private const val PERMISSIONS_REQUEST_READ_CALL_LOG = 100
        private const val PERMISSIONS_REQUEST_READ_PHONE_STATE = 101
    }
}



package com.boyko.savestate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import androidx.core.content.PermissionChecker
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.boyko.savestate.model.ContactDb
import com.boyko.savestate.model.ContactEntity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread


class MainActivity : AppCompatActivity() {

    private lateinit var mDb: ContactDb

    private val recyclerViewContact: RecyclerView
        get() {
            return findViewById<RecyclerView>(R.id.recyclerViewContact)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initcontacts()

        ///////////////
        mDb = ContactDb.getInstance(applicationContext)

        val contact = ContactEntity(
            id = null,
            username = "Andrey",
            phone = 89095403710
        )

        doAsync {
            // Put the student in database
            mDb.contasctDao().insert(contacts = arrayListOf(contact))

            uiThread {
                toast("One record inserted.")
            }
        }


        ////////////
        recyclerViewContact.layoutManager = LinearLayoutManager(this)

        checkpermission()
    }
    private fun checkpermission() {
        if ( PermissionChecker.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS) == PermissionChecker.PERMISSION_GRANTED)
            initcontacts()
        else
            println("mytag checkSelfPermission false")
        //requestContactPermission(this) реализую запрос позже, в данный момент вручную на эмуляторе устанавливаю разрешение
    }

    private fun initcontacts() {
        //TODO("Not yet implemented")
        var arrayContacts = arrayListOf<ContactModel>()
        var cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)
        cursor?.let {
            while (it.moveToNext()){
                val username = it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val numberphone = it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                val newModel = ContactModel()
                newModel.username = username
                newModel.phone = numberphone.replace(Regex("[\\s,-]"),"")
                arrayContacts.add(newModel)
            }
        }
        cursor?.close()
        val myAdapter = Adapter(arrayContacts, object : Adapter.Callback {
            override fun onItemClicked(item: ContactModel) {
                //TODO Сюда придёт элемент, по которому кликнули. Можно дальше с ним работать
            }
        })
        recyclerViewContact.adapter = myAdapter
    }
}
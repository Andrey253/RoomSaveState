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
    private var toWriteArrayContact = arrayListOf<ContactEntity>()
    private var toReadArrayContact: List<ContactEntity>  = arrayListOf<ContactEntity>()

    private val recyclerViewContact: RecyclerView
        get() {
            return findViewById<RecyclerView>(R.id.recyclerViewContact)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkpermission()

        mDb = ContactDb.getInstance(applicationContext)

        doAsync {
            // Put the student in database

            mDb.contasctDao().clear()
            mDb.contasctDao().insert(toWriteArrayContact)

            toReadArrayContact = mDb.contasctDao().getAll()

            setrecycleview(toReadArrayContact)
            uiThread {
                toast("Из базы данных извлечено ${toReadArrayContact.size} записей")
            }
        }

        recyclerViewContact.layoutManager = LinearLayoutManager(this)


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
        //var arrayContacts = arrayListOf<ContactModel>()

        var cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)
        cursor?.let {
            while (it.moveToNext()){
                val username = it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val numberphone = it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                //val newModel = ContactModel()
                val newM = ContactEntity(null, username, numberphone.replace(Regex("[\\s,-]"),""))
                //newModel.username = username
                //newModel.phone = numberphone.replace(Regex("[\\s,-]"),"")
                //arrayContacts.add(newModel)
                toWriteArrayContact.add(newM)
            }
        }
        cursor?.close()
    }

    private fun setrecycleview(array: List<ContactEntity>) {
        val myAdapter = Adapter(array, object : Adapter.Callback {
            override fun onItemClicked(item: ContactEntity) {
                //TODO Сюда придёт элемент, по которому кликнули. Можно дальше с ним работать
            }
        })
        recyclerViewContact.adapter = myAdapter
    }
}
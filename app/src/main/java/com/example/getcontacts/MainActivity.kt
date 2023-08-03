package com.example.getcontacts

import android.content.ContentResolver
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.getcontacts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private val data = mutableListOf<RvData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).also { binding = it }.root)

        binding?.run {


//            repeat(20) {
//                data.add(
//                    RvData(
//                        name = "Name $it",
//                        phone = "Phone $it",
//                    )
//                )
//            }

            rvContacts.adapter = MyRecyclerViewAdapter(
                data = data,
                onNameClick = {
                    Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()

                },
                onPhoneNumberClick = {
                    Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
                },
            )
            rvContacts.layoutManager = LinearLayoutManager(this@MainActivity)


        }

        val permissions = arrayOf(android.Manifest.permission.READ_CONTACTS)
        if (permissions.all {
                ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
            }
        ) {
            getContacts()
        } else {
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_CODE && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
            getContacts()
        } else {
            Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show()
        }
    }

    fun getContacts() {
        val contentResolver: ContentResolver = getContentResolver()
        val uri: Uri? = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val cursor: Cursor? = uri?.let { contentResolver.query(it, null, null, null, null) }

        if (cursor?.count!! > 0) {
            while (cursor.moveToNext() == true) {
                data.add(
                    RvData(
                        name = (cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY)))
                    )
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        private const val PERMISSION_CODE = 123
    }

}
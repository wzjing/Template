package com.infinitytech.template.items

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.UiThread
import com.infinitytech.template.BaseActivity
import com.infinitytech.template.R
import com.infinitytech.template.utils.w
import kotlinx.android.synthetic.main.activity_io.*
import kotlinx.coroutines.experimental.async
import java.io.*
import java.nio.ByteBuffer

class IOActivity : BaseActivity() {

    private val filename = "file.txt"
    private val file = lazy {
        val _file = File(filesDir, filename)
        if (!_file.exists()) _file.createNewFile()
        _file
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_io)

        withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) {
            if (it) {
                presentTitle.text = filename
                readFile {
                    presentContent.text = it
                }
            }
        }

        saveBtn.setOnClickListener {
            withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                if (it) {
                    writeFile {
                        Toast.makeText(this, "Write Finished", Toast.LENGTH_SHORT).show()
                        readFile{
                            presentContent.text = it
                        }
                    }
                }
            }
        }
    }

    @UiThread
    private fun readFile(cb: ((text: String) -> Unit)? = null ){
        async {
            val fis = FileInputStream(file.value)
            val reader = InputStreamReader(fis, "utf-8")
            val text = reader.readText()
            reader.close()
            fis.close()
            runOnUiThread {
                cb?.invoke(text)
            }
        }
    }

    @UiThread
    private fun writeFile(cb: (()->Unit)? = null) {
        val text = sourceContent.text.toString()
        async {
            val fos = FileOutputStream(file.value)
            val writer = OutputStreamWriter(fos, "utf-8")
            writer.write(text)
            writer.flush()
            writer.close()
            fos.close()
            runOnUiThread {
                cb?.invoke()
            }
        }
    }
}

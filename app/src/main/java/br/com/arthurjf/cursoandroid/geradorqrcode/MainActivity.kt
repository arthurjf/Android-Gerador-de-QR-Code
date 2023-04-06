package br.com.arthurjf.cursoandroid.geradorqrcode

import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter

class MainActivity : AppCompatActivity() {
    private var edtQRCodeContent: EditText? = null
    private var btnGenerateQRCode: Button? = null
    private var imgQRCode: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponents()

        btnGenerateQRCode!!.setOnClickListener {
            val content = edtQRCodeContent!!.text.toString()
            if (TextUtils.isEmpty(content)) {
                edtQRCodeContent!!.error = getString(R.string.qr_code_content_error_empty_content)
                edtQRCodeContent!!.requestFocus()
            } else {
                generateQRCode(content)
            }
        }
    }

    private fun generateQRCode(content: String) {
        val qrCode = QRCodeWriter()

        try {
            val bitMatrix = qrCode.encode(content, BarcodeFormat.QR_CODE, 196, 196)

            val width = bitMatrix.width
            val height = bitMatrix.height

            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }

            imgQRCode!!.setImageBitmap(bitmap)
        } catch (e: WriterException) {

        }
    }

    private fun initComponents() {
        edtQRCodeContent = findViewById(R.id.edtQRCodeContent)
        btnGenerateQRCode = findViewById(R.id.btnGenerateQRCode)
        imgQRCode = findViewById(R.id.imgQRCode)
    }
}
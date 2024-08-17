package com.niladri.lloydsdemo.utils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import com.niladri.lloydsdemo.LloydsApplication
import com.niladri.lloydsdemo.R
import javax.inject.Inject


class Utils @Inject constructor(){

    fun toast(context: Context, text: String, duration: Int? = null) {
        val lloydsApplication = LloydsApplication()
        if (lloydsApplication.getToastEnabled(context))
            Toast.makeText(context, text, duration ?: Toast.LENGTH_SHORT).show()
    }

    fun showAlertDialog(
        message: String?,
        ctx: Context
    ) {
        val builder = AlertDialog.Builder(ctx)
        builder.setMessage(message)
            .setCancelable(false)
            .setPositiveButton(ctx.resources.getString(R.string.okay)) { dialog: DialogInterface, _: Int -> dialog.dismiss() }
        builder.create()
        builder.show()
    }
}
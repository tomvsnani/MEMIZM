package com.analogit.memeizm

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.provider.FontsContractCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.analogit.memeizm.Adapters.TextColorRecyclerAdapter
import com.analogit.memeizm.Adapters.TextFontFamilyAdapter
import com.analogit.memeizm.Models.TextPropertiesModelClass
import com.analogit.memeizm.databinding.CustomTextTabRowLayoutBinding
import com.analogit.memeizm.databinding.TextColorAlertDialogRecyclerLlayoutBinding


class CustomTextTabRecyclerAdapter :
    ListAdapter<CustomTextModelClass, CustomTextTabRecyclerAdapter.CustomTextViewHolder>(
        CustomTextModelClass.diff
    ) {

    lateinit var context: Context
    lateinit var textChangeCallBack: (TextPropertiesModelClass) -> Unit


    fun EditText.addCallBack(
        callback: (TextPropertiesModelClass) -> Unit,
        holder: CustomTextViewHolder
    ) {

        callback(
            TextPropertiesModelClass(
                holder.adapterPosition.toString(),
                this.text.toString(),
                this.currentTextColor.toString(),
                this.typeface
            )
        )
    }


    fun setTextChangedCallback(textChangeCallback: (TextPropertiesModelClass) -> Unit) {
        this.textChangeCallBack = textChangeCallback
    }

    inner class CustomTextViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var binding = CustomTextTabRowLayoutBinding.bind(view)


        init {


            binding.apply {


                editText.viewTreeObserver.addOnGlobalLayoutListener {
                    Log.d("edittextchanges", "inglobal")
                }


                editText.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
//                        TextPropertiesModelClass(id = currentList[adapterPosition].id,)
//                        textChangeCallBack()

//                        for (i in listCallback)
//                            i(binding.editText)






                        Log.d("edittextchanges", "intextchangelistener")
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        editText.addCallBack(textChangeCallBack, this@CustomTextViewHolder)
                    }

                    override fun afterTextChanged(s: Editable?) {

                    }
                })

                boldTextImageView.setOnClickListener {
                    if (!editText.typeface.isBold && editText.typeface.isItalic)
                        setTypefaceAndTint(it, R.color.black, Typeface.BOLD_ITALIC)
                    else if

                                 (!editText.typeface.isBold) {
                        setTypefaceAndTint(it, R.color.black, Typeface.BOLD)
                    } else if (
                        editText.typeface.isBold && editText.typeface.isItalic
                    ) {
                        setTypefaceAndTint(
                            it,
                            R.color.lightBlack,
                            Typeface.ITALIC
                        )
                    } else {
                        setTypefaceAndTint(
                            it,
                            R.color.lightBlack,
                            Typeface.NORMAL
                        )

                    }
                    editText.addCallBack(textChangeCallBack, this@CustomTextViewHolder)

                }

                italicTextImageView.setOnClickListener {
                    if (editText.typeface.isBold && !editText.typeface.isItalic)
                        setTypefaceAndTint(it, R.color.black, Typeface.BOLD_ITALIC)
                    else if (!editText.typeface.isItalic) {
                        setTypefaceAndTint(it, R.color.black, Typeface.ITALIC)
                    } else if (
                        editText.typeface.isBold && editText.typeface.isItalic
                    ) {
                        setTypefaceAndTint(
                            it,
                            R.color.lightBlack,
                            Typeface.BOLD
                        )
                    } else {
                        setTypefaceAndTint(
                            it,
                            R.color.lightBlack,
                            Typeface.NORMAL
                        )

                    }
                    editText.addCallBack(textChangeCallBack, this@CustomTextViewHolder)

                }

                textColorImageView.setOnClickListener {

                    createAlertDialogForTextColor(this, this@CustomTextViewHolder)
                }



                fontChangeImageView.setOnClickListener {


                    createFontFamilySelectorDialog(this, this@CustomTextViewHolder)

                }

            }
        }

        private fun createFontFamilySelectorDialog(
            customTextTabRowLayoutBinding: CustomTextTabRowLayoutBinding,
            customTextViewHolder: CustomTextViewHolder
        ) {
            var alert = AlertDialog.Builder(context).create()
            var alertDialogBinding = TextColorAlertDialogRecyclerLlayoutBinding.bind(
                LayoutInflater.from(context)
                    .inflate(R.layout.text_color_alert_dialog_recycler_llayout, null, false)
            )
            alert.setView(alertDialogBinding.root)
            alertDialogBinding.textcolorRecycler.apply {

                var fontList = mutableListOf("Actor", "Aladin", "Amita", "Arapey")
                layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

                adapter = TextFontFamilyAdapter {


                    requestFontsFromGoogle(fontList[it], customTextTabRowLayoutBinding.editText,this@CustomTextViewHolder)

                    alert.dismiss()
                }


                    .apply {
                        submitList(
                            fontList
                        )
                    }
            }
            alert.show()
        }

        private fun CustomTextTabRowLayoutBinding.setTypefaceAndTint(
            view: View,
            color: Int,
            typeface: Int
        ) {
            (view as? ImageView)?.drawable?.let {
                DrawableCompat.setTint(
                    it,
                    ResourcesCompat.getColor(context.resources, color, null)
                )
            }
            editText.setTypeface(
                Typeface.create(editText.typeface, typeface),
                typeface
            );
        }
    }

    private fun requestFontsFromGoogle(

        fontName: String, editText: EditText, customTextViewHolder: CustomTextViewHolder
    ) {

        val request = androidx.core.provider.FontRequest(
            context.getString(R.string.fonts_provider),
            context.getString(R.string.fonts_package),
            "name=$fontName",
            R.array.com_google_android_gms_fonts_certs
        )
        FontsContractCompat.requestFont(
            context, request, object : FontsContractCompat.FontRequestCallback() {
                override fun onTypefaceRetrieved(typeface: Typeface?) {
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    editText.typeface = typeface

                    with(this@CustomTextTabRecyclerAdapter) {
                        customTextViewHolder.binding.editText.addCallBack(
                            textChangeCallBack,
                            customTextViewHolder
                        )
                    }
                    super.onTypefaceRetrieved(typeface)
                }

                override fun onTypefaceRequestFailed(reason: Int) {
                    Toast.makeText(context, "fail $reason", Toast.LENGTH_SHORT).show()
                    super.onTypefaceRequestFailed(reason)
                }
            }, editText.handler
        )
    }

    private fun createAlertDialogForTextColor(
        customTextTabRowLayoutBinding: CustomTextTabRowLayoutBinding,
        customTextViewHolder: CustomTextViewHolder
    ) {
        var alert = AlertDialog.Builder(context).create()
        var alertDialogBinding = TextColorAlertDialogRecyclerLlayoutBinding.bind(
            LayoutInflater.from(context)
                .inflate(R.layout.text_color_alert_dialog_recycler_llayout, null, false)
        )
        alert.setView(alertDialogBinding.root)
        alertDialogBinding.textcolorRecycler.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            var colorsarray = context.resources.getStringArray(R.array.colorlist).toMutableList()
            adapter = TextColorRecyclerAdapter {
                customTextTabRowLayoutBinding.editText.setTextColor(Color.parseColor(colorsarray[it]))
                with(this@CustomTextTabRecyclerAdapter) {
                    customTextTabRowLayoutBinding.editText.addCallBack(
                        textChangeCallBack,
                        customTextViewHolder
                    )
                }
                alert.dismiss()
            }.apply {
                submitList(
                    colorsarray
                )
            }
        }
        alert.show()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomTextViewHolder {
        context = parent.context
        return CustomTextViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.custom_text_tab_row_layout, parent, false)
        )

    }


    override fun submitList(list: MutableList<CustomTextModelClass>?) {

        super.submitList(list)
    }


    override fun onBindViewHolder(holder: CustomTextViewHolder, position: Int) {


        var model = currentList[position]



        if (model.isActive)
            holder.binding.isViewActiveImageView.setImageDrawable(
                ResourcesCompat.getDrawable(
                    context.resources, R.drawable.ic_baseline_visibility_24, null
                )
            )
        else
            holder.binding.isViewActiveImageView.setImageDrawable(
                ResourcesCompat.getDrawable(
                    context.resources, R.drawable.ic_baseline_visibility_off_24, null
                )
            )


    }

    override fun getItemCount(): Int {

        return currentList.size

    }
}

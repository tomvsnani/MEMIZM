package com.analogit.memeizm.Fragments

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.analogit.memeizm.Adapters.ImageFragmentTreandingImageAdapter
import com.analogit.memeizm.MainActivity
import com.analogit.memeizm.R
import com.analogit.memeizm.databinding.FragmentImageBinding
import java.io.File
import java.util.*

class ImageFragment : Fragment(R.layout.fragment_image) {

lateinit var binding:FragmentImageBinding
lateinit var imagesadapter:ImageFragmentTreandingImageAdapter
    var gallaryImageIntentLauncher: ActivityResultLauncher<String>? = null
    var cameraImageLauncher:ActivityResultLauncher<Uri>? =null
    lateinit var fileToStoreCameraImage: File

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding= FragmentImageBinding.bind(view)

        imagesadapter= ImageFragmentTreandingImageAdapter()

        binding.trendingImagesRecycerView.layoutManager=GridLayoutManager(context,3,RecyclerView.VERTICAL,false)

        binding.trendingImagesRecycerView.adapter=imagesadapter

        binding.gallaryImageWrapper.setOnClickListener {
            gallaryImageIntentLauncher?.launch("image/*")
        }

        binding.cameraImageViewWrapper.setOnClickListener {
            cameraImageLauncher?.launch(context?.let { it1 ->
                fileToStoreCameraImage=  File.createTempFile("imagetobeadded", Date().time.toString(),it1.getExternalFilesDir(Environment.DIRECTORY_PICTURES))
                FileProvider.getUriForFile(
                    it1,it1.packageName+".fileprovider",
                  fileToStoreCameraImage)
            })
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)

        gallaryImageIntentLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
            it?.let {


                (activity as MainActivity).imageChangeCallBack(it.toString())
            }



        }
        cameraImageLauncher=  registerForActivityResult(ActivityResultContracts.TakePicture()){
            if(it ){
                (activity as MainActivity).imageChangeCallBack(fileToStoreCameraImage.toURI().toString())
            }
        }



        super.onCreate(savedInstanceState)
    }

    override fun onResume() {

        if (this::binding.isInitialized) {
            val wMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                binding.root.width,
                View.MeasureSpec.EXACTLY
            )
            val hMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                0,
                View.MeasureSpec.UNSPECIFIED
            )
            binding.trendingImagesRecycerView.measure(wMeasureSpec,hMeasureSpec)
            binding.trendingImagesRecycerView.layoutParams.height= binding.trendingImagesRecycerView.measuredHeight
           binding.trendingImagesRecycerView.requestLayout()

        }

        super.onResume()

    }

//    override fun onStart() {
//
//        super.onStart()
//    }

}
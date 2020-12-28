package com.yinglan.comparepicview

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val picView: ComparePicView = view.findViewById(R.id.picView)

        val mBitmap1 =
            (resources.getDrawable(R.mipmap.p1) as BitmapDrawable).bitmap
        val mBitmap2 =
            (resources.getDrawable(R.mipmap.p2) as BitmapDrawable).bitmap
        picView.setCompareBitmap(mBitmap1, mBitmap2)
    }
}
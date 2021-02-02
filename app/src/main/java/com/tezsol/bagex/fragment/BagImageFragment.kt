package com.tezsol.bagex.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tezsol.bagex.BagScanActivity
import com.tezsol.bagex.R
import kotlinx.android.synthetic.main.fragment_bag_image.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [BagImageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BagImageFragment : BaseFragment() {
    private lateinit var onNextClickListener: BagImageFragment.OnNextClickListener

    // TODO: Rename and change types of parameters
    private var mParam1: Int? = null
    private var mParam2: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getInt(ARG_PARAM1)
            mParam2 = arguments!!.getInt(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bag_image, container, false)
        view.bag_name.text = "Bag $mParam1"

        if (mParam1 == mParam2)
            view.lableText.text = "Submit"
        else
            view.lableText.text = "Next"

        view.nextBtn.setOnClickListener {
            if (isValied()) {
                val bagInfo = BagScanActivity.BagTagInfo()
                bagInfo.sealNo = view.sealNumber.text.toString()
                bagInfo.tagNo = view.tagNumber.text.toString()
                mParam1?.let { it1 -> onNextClickListener.onNextClick(it1, bagInfo) }
            }
        }
        return view
    }

    private fun isValied(): Boolean {
        if (view?.tagNumber?.text.toString().isEmpty()) {
            view?.tagNumber?.error = "Please enter Tag Number"
            return false;
        }
        if (view?.sealNumber?.text.toString().isEmpty()) {
            view?.sealNumber?.error = "Please enter Seal Number"
            return false
        }
        return true
    }


    fun setOnNextClickListener(onNextClickListener: OnNextClickListener) {
        this.onNextClickListener = onNextClickListener
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        fun newInstance(param1: Int?, param2: Int?): BagImageFragment {
            val fragment = BagImageFragment()
            val args = Bundle()
            param1?.let { args.putInt(ARG_PARAM1, it) }
            param2?.let { args.putInt(ARG_PARAM2, it) }
            fragment.arguments = args
            return fragment
        }
    }

    interface OnNextClickListener {
        fun onNextClick(pos: Int, bagTagInfo: BagScanActivity.BagTagInfo)
    }

}
package com.aplication.mapp.onboarding

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.aplication.mapp.R
import com.aplication.mapp.onboarding.OnBoardingData

class OnBoardingViewPagerAdapter(private var context: Context, private var onBoardingDataList: List<OnBoardingData>): PagerAdapter() {
    override fun getCount(): Int {
        return onBoardingDataList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_intro_viewpager, null)
        val imageView: ImageView
        val title: TextView
        val text: TextView

        imageView = view.findViewById(R.id.intro_img)
        title = view.findViewById(R.id.intro_title)
        text = view.findViewById(R.id.intro_description)

        imageView.setImageResource(onBoardingDataList[position].image)
        title.text = onBoardingDataList[position].title
        text.text = onBoardingDataList[position].text

        container.addView(view)
        return view
    }
}
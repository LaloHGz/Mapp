package com.aplication.mapp.menu.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.aplication.mapp.R
import com.aplication.mapp.menu.profile.OptionProfile
import kotlinx.android.synthetic.main.row_listview_profile.view.*

class ProfileAdapter(private  val mContext: Context, private val listOptions: List<OptionProfile>): ArrayAdapter<OptionProfile>(mContext, 0, listOptions) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(mContext).inflate(R.layout.row_listview_profile, parent, false)

        val options =  listOptions[position]

        layout.image.setImageResource(options.image)
        layout.title.text = options.title

        val version:TextView = layout.findViewById(R.id.version)
        val arrow:ImageView = layout.findViewById(R.id.arrow)
        if (position == 2) {
            arrow.visibility = View.GONE
            version.visibility = View.VISIBLE
        }else if (position == 3) {
            arrow.visibility = View.GONE
            version.visibility = View.GONE
        }

        return layout
    }


}
package com.example.drken.blocks

import android.app.Activity
import android.support.design.widget.CollapsingToolbarLayout
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.drken.blocks.data.BlockContent
import com.example.drken.blocks.network.models.Block

/**
 * A fragment representing a single Block detail screen.
 * This fragment is either contained in a [BlockListActivity]
 * in two-pane mode (on tablets) or a [BlockDetailActivity]
 * on handsets.
 */
class BlockDetailFragment : Fragment() {

    private var mItem: Block? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments!!.containsKey(ARG_ITEM_ID)) {
            mItem = BlockContent.ITEM_MAP[arguments!!.getString(ARG_ITEM_ID)]
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.block_detail, container, false)

        if (mItem != null) {
            (rootView.findViewById<View>(R.id.block_detail) as TextView).text = BlockContent.getOverviewDetails(mItem!!.id)
        }

        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        val ARG_ITEM_ID = "item_id"
    }
}

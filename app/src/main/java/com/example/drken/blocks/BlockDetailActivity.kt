package com.example.drken.blocks

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.View
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.ActionBar
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView

import com.example.drken.blocks.data.BlockContent
import com.example.drken.blocks.network.models.Block

import com.example.drken.blocks.BlockDetailFragment.Companion.ARG_ITEM_ID

/**
 * An activity representing a single Block detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [BlockListActivity].
 */
class BlockDetailActivity : AppCompatActivity() {

    private val mItem: Block? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_block_detail)
        val toolbar = findViewById<View>(R.id.detail_toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val rawBlockID = intent.getStringExtra(ARG_ITEM_ID)
        val rawData: String
        if (!TextUtils.isEmpty(rawBlockID)) {
            rawData = BlockContent.getRawDetails(rawBlockID)
        } else {
            rawData = getString(R.string.error_no_raw_data)
        }

        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener {
            val bottomSheet = findViewById<View>(R.id.bottom_sheet) as LinearLayout
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
            val textView = findViewById<View>(R.id.bottom_sheet_text) as TextView
            textView.text = rawData
            //bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        // Show the Up button in the action bar.
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            val arguments = Bundle()
            arguments.putString(ARG_ITEM_ID,
                    intent.getStringExtra(ARG_ITEM_ID))
            val fragment = BlockDetailFragment()
            fragment.arguments = arguments
            supportFragmentManager.beginTransaction()
                    .add(R.id.block_detail_container, fragment)
                    .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(Intent(this, BlockListActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

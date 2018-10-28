package com.example.drken.blocks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView

import com.example.drken.blocks.data.BlockContent
import com.example.drken.blocks.network.BlockRequest
import com.example.drken.blocks.network.EOSUtilities
import com.example.drken.blocks.network.models.Block
import com.example.drken.blocks.network.models.EOSInfo

import java.util.ArrayList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * An activity representing a list of Blocks. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [BlockDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class BlockListActivity : AppCompatActivity() {
    private val mBlocks = ArrayList<Block>()
    private var mProgressBar: ProgressBar? = null
    private var mDownloadStatus: TextView? = null
    private var mAdapter: SimpleItemRecyclerViewAdapter? = null


    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var mTwoPane: Boolean = false

    private var mRecyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_block_list)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        toolbar.title = title

        mRecyclerView = findViewById<View>(R.id.block_list) as RecyclerView

        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { if (clearBlockData()) getBlockInfo() }

        mProgressBar = findViewById<View>(R.id.progress_blocks) as ProgressBar
        mDownloadStatus = findViewById<View>(R.id.textViewNoData) as TextView
        mDownloadStatus!!.visibility = View.INVISIBLE

        if (findViewById<View>(R.id.block_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true
        }
        setupRecyclerView()
        getBlockInfo()

    }

    private fun setupRecyclerView() {
        mAdapter = SimpleItemRecyclerViewAdapter(this, mBlocks, mTwoPane)
        mRecyclerView!!.adapter = mAdapter
        mRecyclerView!!.itemAnimator = DefaultItemAnimator()
        mRecyclerView!!.layoutManager = LinearLayoutManager(this)
    }

    fun clearBlockData(): Boolean {
        if (mBlocks != null && mAdapter != null) {
            val size = mBlocks.size
            mBlocks.clear()
            mAdapter!!.notifyItemRangeRemoved(0, size)
            return true
        } else {
            return false
        }
    }

    class SimpleItemRecyclerViewAdapter internal constructor(private val mParentActivity: BlockListActivity,
                                                             private val mValues: List<Block>,
                                                             private val mTwoPane: Boolean) : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {
        private val mOnClickListener = View.OnClickListener { view ->
            val item = view.tag as Block
            if (mTwoPane) {
                val arguments = Bundle()
                arguments.putString(BlockDetailFragment.ARG_ITEM_ID, item.id)
                val fragment = BlockDetailFragment()
                fragment.arguments = arguments
                mParentActivity.supportFragmentManager.beginTransaction()
                        .replace(R.id.block_detail_container, fragment)
                        .commit()
            } else {
                val context = view.context
                val intent = Intent(context, BlockDetailActivity::class.java)
                intent.putExtra(BlockDetailFragment.ARG_ITEM_ID, item.id)

                context.startActivity(intent)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.block_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            if (mValues[position] != null && holder.mIdView != null) {
                holder.mIdView.text = mValues[position].id
            }

            holder.itemView.tag = mValues[position]
            holder.itemView.setOnClickListener(mOnClickListener)
        }

        override fun getItemCount(): Int {
            return mValues.size
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val mIdView: TextView?

            init {
                mIdView = view.findViewById<View>(R.id.id_text) as TextView
            }
        }
    }


    private fun getBlockInfo() {
        mProgressBar!!.visibility = View.VISIBLE
        mDownloadStatus!!.visibility = View.INVISIBLE
        val call = EOSUtilities.eosInterface.info
        call.enqueue(object : Callback<EOSInfo> {
            override fun onResponse(call: Call<EOSInfo>, response: Response<EOSInfo>) {
                if (response.isSuccessful) {
                    try {
                        val eosInfo = response.body()
                        val headBlockID = Integer.valueOf(eosInfo!!.headBlockNum!!)
                        getBlocks(headBlockID)

                    } catch (e: Exception) {
                        Log.e(TAG, "onResponse: Data was unreadable")
                        mProgressBar!!.visibility = View.INVISIBLE
                        mDownloadStatus!!.visibility = View.VISIBLE
                    }

                } else {
                    mProgressBar!!.visibility = View.INVISIBLE
                    mDownloadStatus!!.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<EOSInfo>, t: Throwable) {
                Log.d("Error", t.message)
                mProgressBar!!.visibility = View.INVISIBLE
                mDownloadStatus!!.visibility = View.VISIBLE
            }
        })
    }

    private fun getBlocks(blockID: Int) {
        val blockCall = EOSUtilities.eosInterface.getBlock(BlockRequest(blockID.toString()))
        blockCall.enqueue(object : Callback<Block> {
            override fun onResponse(call: Call<Block>, response: Response<Block>) {
                if (response.isSuccessful) {
                    val block = response.body()
                    if (block != null) {
                        if (!TextUtils.isEmpty(block.id)) {
                            mBlocks.add(block)
                            BlockContent.addItem(block)
                        }
                    }
                }
                cycleThroughBlocksAndUpdateUI()
            }

            override fun onFailure(call: Call<Block>, t: Throwable) {
                Log.e(TAG, "onFailure: " + t.localizedMessage)
                cycleThroughBlocksAndUpdateUI()
            }

            private fun cycleThroughBlocksAndUpdateUI() {
                var currentBlockID = blockID
                if (mBlocks.size < 20 && blockID > 0) {
                    --currentBlockID
                    getBlocks(currentBlockID)
                } else {
                    if (mBlocks.size > 0) {
                        mAdapter!!.notifyDataSetChanged()
                        mProgressBar!!.visibility = View.INVISIBLE
                    }
                }
            }
        })
    }

    companion object {
        private val TAG = BlockListActivity::class.java!!.getCanonicalName()
    }
}



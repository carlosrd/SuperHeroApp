package com.carlosrd.superhero.ui.recyclerview

import android.graphics.Canvas
import android.graphics.Rect
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.view.View
import android.view.View.MeasureSpec.EXACTLY
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class StickyHeaderItemDecorator<T>(
    parent: RecyclerView,
    private val adapter: AsyncListDifferDelegationAdapter<T>,
    private val onHeaderClickListener : ((position: Int) -> Unit)? = null,
) : RecyclerView.ItemDecoration() {

    interface StickyHeader

    private var currentHeader: Pair<Int, RecyclerView.ViewHolder>? = null

    init {
        parent.adapter?.registerAdapterDataObserver(
            object: RecyclerView.AdapterDataObserver() {
                override fun onChanged() {
                    // clear saved header as it can be outdated noew
                    currentHeader = null
                }
            }
        )
        parent.doOnEachNextLayout {
            currentHeader = null
        }

        parent.addOnItemTouchListener(
            object: RecyclerView.SimpleOnItemTouchListener() {
                override fun onInterceptTouchEvent(recyclerView: RecyclerView, motionEvent: MotionEvent): Boolean =

                    if (motionEvent.action == ACTION_DOWN){
                        if (motionEvent.y <= currentHeader?.second?.itemView?.bottom ?: 0)
                            currentHeader?.let { header ->
                                val manager = recyclerView.layoutManager as LinearLayoutManager
                                val pos = manager.findFirstVisibleItemPosition()
                                val containsHeaderAbove = adapter.items.subList(0,pos).any { it is StickyHeader }
                                if ((adapter.items[pos] is StickyHeader).not() && containsHeaderAbove){
                                    onHeaderClickListener?.invoke(header.first)
                                    return true
                                }
                            }
                        false
                    } else false

            }
        )
    }

    private fun isHeader(itemPosition: Int): Boolean = adapter.items[itemPosition] is StickyHeader

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val topChild = parent.findChildViewUnder(parent.paddingLeft.toFloat(), parent.paddingTop.toFloat()) ?: return
        val topChildPosition = parent.getChildAdapterPosition(topChild)
        if (topChildPosition == NO_POSITION) return

        val headerView = getHeaderViewForItem(topChildPosition, parent) ?: return

        val contactPoint = headerView.bottom + parent.paddingTop
        val childInContact = getChildInContact(parent, contactPoint) ?: return

        val childPosition = parent.getChildAdapterPosition(childInContact)
        if (childPosition == NO_POSITION) return

        if (isHeader(childPosition)){
            moveHeader(c, headerView, childInContact, parent.paddingTop, parent.paddingLeft)
        }

        drawHeader(c, headerView, parent.paddingTop, parent.paddingLeft)

    }

    private fun getHeaderViewForItem(itemPosition: Int, parent: RecyclerView): View? {
        if (parent.adapter == null){
            return null
        }
        val headerPosition = getHeaderPositionForItem(itemPosition)
        if (headerPosition == NO_POSITION) return null
        val headerType = parent.adapter?.getItemViewType(headerPosition) ?: return null
        // if match resue viewholder
        if (currentHeader?.first == headerPosition && currentHeader?.second?.itemViewType == headerType){
            return currentHeader?.second?.itemView
        }

        val headerHolder = parent.adapter?.createViewHolder(parent, headerType)
        if (headerHolder != null){
            parent.adapter?.onBindViewHolder(headerHolder, headerPosition)
            fixLayoutSize(parent, headerHolder.itemView)
            //save for next draw
            currentHeader = headerPosition to headerHolder
        }

        return headerHolder?.itemView

    }

    private fun drawHeader(c: Canvas, header: View, paddingTop: Int,paddingStart: Int){
        c.save()
        c.translate(paddingStart.toFloat(), paddingTop.toFloat())
        header.draw(c)
        c.restore()
    }

    private fun moveHeader(c: Canvas, currentHeader: View, nextHeader: View, paddingTop: Int,
        paddingStart: Int){
        c.save()
        c.clipRect(0, paddingTop, c.width, paddingTop + currentHeader.height)
        c.translate(paddingStart.toFloat(), (nextHeader.top - currentHeader.height).toFloat())
        currentHeader.draw(c)
        c.restore()
    }

    private fun getChildInContact(parent: RecyclerView, contactPoint: Int): View? {

        var childInContact: View? = null
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val mBounds = Rect()
            parent.getDecoratedBoundsWithMargins(child, mBounds)
            if (mBounds.bottom > contactPoint) {
                if (mBounds.top <= contactPoint) {
                    childInContact = child
                    break
                }
            }
        }
        return childInContact
    }

    private fun fixLayoutSize(parent: ViewGroup, view: View){

        // Specs for parent (RecyclerView)
        val widthSpec = View.MeasureSpec.makeMeasureSpec(parent.width, EXACTLY)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(parent.height, EXACTLY)

        val childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec, parent.paddingLeft + parent.paddingRight, view.layoutParams.width)
        val childHeightSpec = ViewGroup.getChildMeasureSpec(widthSpec, parent.paddingTop + parent.paddingBottom, view.layoutParams.height)

        view.measure(childWidthSpec, childHeightSpec)
        view.layout(0,0, view.measuredWidth, view.measuredHeight)

    }

    private fun getHeaderPositionForItem(itemPosition: Int): Int {
        var headerPosition = NO_POSITION
        var currentPosition = itemPosition
        do {
            if (isHeader(currentPosition)) {
                headerPosition = currentPosition
                break
            }
            currentPosition -= 1
        } while (currentPosition >= 0)
        return headerPosition
    }

    private inline fun View.doOnEachNextLayout(crossinline action: (view: View) -> Unit) {
        addOnLayoutChangeListener { view, _,_,_,_,_,_,_,_, -> action(view)}
    }
}
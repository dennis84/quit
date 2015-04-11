package quit.app.history

import android.widget.AbsListView
import android.widget.AbsListView.OnScrollListener

abstract class EndlessScrollListener extends OnScrollListener {

    var visibleThreshold = 5
    var currentPage = 0
    var previousTotalItemCount = 0
    var startingPageIndex = 0;
    var loading = true

    override def onScroll(
      view: AbsListView,
      firstVisibleItem: Int,
      visibleItemCount: Int,
      totalItemCount: Int
    ) {
      if(totalItemCount < previousTotalItemCount) {
        currentPage = startingPageIndex
        previousTotalItemCount = totalItemCount
        if(totalItemCount == 0) loading = true
      }

      if(loading && (totalItemCount > previousTotalItemCount)) {
        loading = false
        previousTotalItemCount = totalItemCount
        currentPage = currentPage + 1
      }

      if(!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
        onLoadMore(currentPage + 1, totalItemCount)
        loading = true
      }
    }

    override def onScrollStateChanged(view: AbsListView, scrollState: Int) {}

    def onLoadMore(page: Int, totalItemsCount: Int)
}

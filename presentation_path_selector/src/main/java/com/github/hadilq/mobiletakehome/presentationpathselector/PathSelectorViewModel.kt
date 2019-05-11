package com.github.hadilq.mobiletakehome.presentationpathselector

import com.github.hadilq.mobiletakehome.domain.usecase.PathSelectorPage
import com.github.hadilq.mobiletakehome.presentationcommon.BaseViewModel
import javax.inject.Inject

class PathSelectorViewModel @Inject constructor(
    private val pathSelector: PathSelectorPage
) : BaseViewModel() {

}
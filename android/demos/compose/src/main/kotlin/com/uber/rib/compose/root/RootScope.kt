/*
 * Copyright (C) 2021. Uber Technologies
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.uber.rib.compose.root

import android.view.ViewGroup
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.uber.rib.compose.root.main.MainScope
import com.uber.rib.compose.util.AnalyticsClient
import com.uber.rib.compose.util.AnalyticsClientImpl
import com.uber.rib.compose.util.ExperimentClient
import com.uber.rib.compose.util.ExperimentClientImpl
import com.uber.rib.compose.util.LoggerClient
import com.uber.rib.compose.util.LoggerClientImpl
import com.uber.rib.core.EmptyPresenter
import com.uber.rib.core.RibActivity
import motif.Expose

@motif.Scope
interface RootScope {
  fun router(): RootRouter

  fun mainScope(parentViewGroup: ViewGroup): MainScope

  @motif.Objects
  abstract class Objects {
    abstract fun router(): RootRouter

    abstract fun interactor(): RootInteractor

    abstract fun presenter(): EmptyPresenter

    fun view(parentViewGroup: ViewGroup, activity: RibActivity): RootView {
      return RootView(parentViewGroup.context).apply {
        setViewTreeLifecycleOwner(activity)
        setViewTreeSavedStateRegistryOwner(activity)
      }
    }

    @Expose
    fun analyticsClient(activity: RibActivity): AnalyticsClient {
      return AnalyticsClientImpl(activity.application)
    }

    @Expose
    fun experimentClient(activity: RibActivity): ExperimentClient {
      return ExperimentClientImpl(activity.application)
    }

    @Expose
    fun loggerClient(activity: RibActivity): LoggerClient {
      return LoggerClientImpl(activity.application)
    }
  }
}

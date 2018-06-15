package com.samizerouta.definitelytypedforks.util

import io.reactivex.Scheduler

interface SchedulerProvider {
  val io: Scheduler
  val ui: Scheduler
}
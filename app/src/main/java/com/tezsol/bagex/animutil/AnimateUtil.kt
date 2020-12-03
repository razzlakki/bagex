package com.tezsol.bagex.animutil

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View


class AnimateUtil {


    private object HOLDER {
        val INSTANCE = AnimateUtil()
    }

    companion object {
        val instance: AnimateUtil by lazy { HOLDER.INSTANCE }
    }

    fun animateFromLeft(view: View, animFrom: AnimFrom) {
        val value = if (animFrom == AnimFrom.TOP || animFrom == AnimFrom.LEFT) -1000f else 1000f
        ObjectAnimator.ofFloat(view, getTransactionType(animFrom), value, 1f).apply {
            duration = 600
            start()
        }
    }

    fun animateFromLeft(view: View, animFrom: AnimFrom, durationDelay: Long) {
        val value = if (animFrom == AnimFrom.TOP || animFrom == AnimFrom.LEFT) -1000f else 1000f
        ObjectAnimator.ofFloat(view, getTransactionType(animFrom), value, 1f).apply {
            duration = durationDelay
            start()
        }
    }

    private fun getTransactionType(animFrom: AnimFrom): String? {
        return when (animFrom) {
            AnimFrom.TOP -> "translationY"
            AnimFrom.LEFT -> "translationX"
            AnimFrom.RIGHT -> "translationX"
            AnimFrom.BOTTOM -> "translationY"
        }
    }

    fun zoomAnimation(view: View) {
        val set = AnimatorSet()
        set.play(ObjectAnimator.ofFloat(view, View.SCALE_X, 1f, 0.2f))
            .with(ObjectAnimator.ofFloat(view, View.SCALE_Y, 1f, 0.2f));
        set.apply { duration = 400 }
        set.start()
    }


    enum class AnimFrom {
        TOP, LEFT, RIGHT, BOTTOM,
    }

}
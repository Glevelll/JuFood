package com.project.jufood.data.local.utils

import com.project.jufood.R

enum class RecipeType(val imageResourceId: Int) {
    Завтраки(R.drawable.breakfast),
    Десерты(R.drawable.deserts),
    Салаты(R.drawable.salads),
    Супы(R.drawable.soups),
    Горячее(R.drawable.mains)
}

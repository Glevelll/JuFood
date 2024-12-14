package com.project.jufood.presentation.main.screens.main

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.jufood.R
import com.project.jufood.presentation.addplan.AddPlanActivity
import com.project.jufood.data.local.RecipesDatabase

@Composable
fun MainScreen(context: Context) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                val intent = Intent(context, AddPlanActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier
                .padding(16.dp)
                .size(250.dp)
                .background(Color(android.graphics.Color.parseColor("#FFF0E1"))),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFBFD05F)
            )
        ) {
            Text(
                text = stringResource(id = R.string.create_plan),
                fontSize = 30.sp
            )
        }
    }
}
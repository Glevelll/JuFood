package com.project.jufood.presentation.addplan.screens.main

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.jufood.R
import com.project.jufood.presentation.addplan.AddPlanActivity
import com.project.jufood.presentation.addplan.AddPlanViewModel
import com.project.jufood.presentation.addplan.screens.main.components.PlanCard

@Composable
fun AddPlanScreen(activity: AddPlanActivity, viewModel: AddPlanViewModel) {
    var text1 by remember { mutableStateOf(TextFieldValue("")) }

    val filteredRecipes by viewModel.filteredRecipesByProducts.collectAsState()
    val favoriteRecipes by viewModel.favoriteRecipes.collectAsState()
    val createdRecipes by viewModel.createdRecipes.collectAsState()
    val selectedRecipes by viewModel.selectedRecipes.collectAsState()
    val buttonStates by viewModel.buttonStates.collectAsState()

    val allRecipes = favoriteRecipes + createdRecipes + filteredRecipes

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(end = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .clickable {
                            activity.finish()
                        }
                )
            }
            Text(
                text = stringResource(id = R.string.create_plan),
                fontFamily = FontFamily.Default,
                fontSize = 22.sp,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = R.string.enter_date),
            fontFamily = FontFamily.Default,
            fontSize = 22.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 16.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(R.color.background))
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextField(
                    value = text1,
                    onValueChange = {
                        if (it.text.matches(Regex("^\\d{0,2}(\\.\\d{0,2}(\\.\\d{0,4})?)?$"))) {
                            text1 = it
                        }
                    },
                    modifier = Modifier
                        .width(200.dp)
                        .height(55.dp)
                        .border(
                            1.dp,
                            Color(R.color.border),
                            shape = RoundedCornerShape(10.dp)
                        ),
                    shape = RoundedCornerShape(10.dp),
                    textStyle = TextStyle(
                        textAlign = TextAlign.Start,
                        fontSize = 18.sp
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(R.color.background),
                        unfocusedContainerColor = Color(R.color.background),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    placeholder = { Text(text = stringResource(id = R.string.date_format)) }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.add_recipes),
                    fontFamily = FontFamily.Default,
                    fontSize = 22.sp,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(allRecipes) { index, recipe ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    PlanCard(item = recipe)
                    Button(
                        onClick = { viewModel.toggleButtonState(index) },
                        modifier = Modifier
                            .width(180.dp)
                            .height(48.dp),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(text = if (buttonStates.getOrNull(index) == true)
                            stringResource(id = R.string.delete)
                        else stringResource(id = R.string.add))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(80.dp))

        Button(
            onClick = {
                if (text1.text.isEmpty()) {
                    Toast.makeText(activity, R.string.enter_date, Toast.LENGTH_SHORT).show()
                    return@Button
                }
                if (text1.text.length != 5) {
                    Toast.makeText(activity, R.string.enter_correct_date, Toast.LENGTH_SHORT).show()
                    return@Button
                }
                val selectedDate = text1.text

                selectedRecipes.forEach { recipe ->
                    viewModel.addPlan(selectedDate, recipe)
                }

                activity.finish()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(start = 5.dp, end = 5.dp, bottom = 5.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFBFD05F)
            )
        ) {
            Text(text = stringResource(id = R.string.create))
        }
    }
}
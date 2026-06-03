package com.example.medicitas

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun BookAppointmentBottomSheet(
    onDismiss: () -> Unit,
    onMakeAppointment: () -> Unit
) {
    val strings = LocalStrings.current
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = Color.White,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 32.dp)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Close")
                }
                Text(
                    text = strings.bookAppointment,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(48.dp)) // To center the title
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Appointment Type Selector
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .clip(CircleShape)
                    .background(Color(0xFFF3F3F3))
                    .padding(4.dp)
            ) {
                var selectedType by remember { mutableStateOf(strings.inPerson) }
                AppointmentTypeButton(
                    text = strings.inPerson,
                    isSelected = selectedType == strings.inPerson,
                    onClick = { selectedType = strings.inPerson },
                    modifier = Modifier.weight(1f)
                )
                AppointmentTypeButton(
                    text = strings.videoVisit,
                    isSelected = selectedType == strings.videoVisit,
                    onClick = { selectedType = strings.videoVisit },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Calendar Section
            CalendarSection()

            Spacer(modifier = Modifier.height(24.dp))

            // Time Slots
            TimeSlotsSection()

            Spacer(modifier = Modifier.height(24.dp))

            // Action Button
            Button(
                onClick = onMakeAppointment,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007BFF))
            ) {
                Text(text = strings.makeAppointment, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun AppointmentTypeButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(40.dp)
            .clip(CircleShape)
            .background(if (isSelected) Color.White else Color.Transparent)
            .then(if (isSelected) Modifier.border(1.dp, Color(0xFFE0E0E0), CircleShape) else Modifier)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) Color.Black else Color.Gray
        )
    }
}

@Composable
fun CalendarSection() {
    val strings = LocalStrings.current
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFF3F3F3))
                .padding(vertical = 12.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Prev Month", modifier = Modifier.size(20.dp), tint = Color.Gray)
            Text(
                text = if (strings == EnglishStrings) "December 2024" else "Diciembre 2024", 
                fontWeight = FontWeight.Bold, 
                fontSize = 14.sp
            )
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next Month", modifier = Modifier.size(20.dp), tint = Color.Gray)
        }

        Spacer(modifier = Modifier.height(16.dp))

        val days = if (strings == EnglishStrings) {
            listOf("Mon" to "10", "Tue" to "11", "Wed" to "12", "Thu" to "13", "Fri" to "14", "Sat" to "15", "Sun" to "16")
        } else {
            listOf("Lun" to "10", "Mar" to "11", "Mie" to "12", "Jue" to "13", "Vie" to "14", "Sab" to "15", "Dom" to "16")
        }
        var selectedDay by remember { mutableStateOf(days[2].first + " " + days[2].second) }

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(days) { (day, num) ->
                val current = "$day $num"
                val isSelected = current == selectedDay
                Column(
                    modifier = Modifier
                        .width(60.dp)
                        .height(80.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (isSelected) Color.White else Color(0xFFF3F3F3))
                        .border(1.dp, if (isSelected) Color(0xFF007BFF) else Color.Transparent, RoundedCornerShape(16.dp))
                        .clickable { selectedDay = current },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = day, fontSize = 12.sp, color = if (isSelected) Color(0xFF007BFF) else Color.Gray)
                    Text(text = num, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = if (isSelected) Color(0xFF007BFF) else Color.Black)
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TimeSlotsSection() {
    val strings = LocalStrings.current
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        TimeCategory(title = strings.morning, slots = listOf("08:00 am", "09:00 am", "10:00 am", "11:00 am"))
        Spacer(modifier = Modifier.height(16.dp))
        TimeCategory(title = strings.afternoon, slots = listOf("01:00 pm", "02:00 pm", "03:00 pm", "04:00 pm"), selectedSlot = "02:00 pm")
        Spacer(modifier = Modifier.height(16.dp))
        TimeCategory(title = strings.evening, slots = listOf("06:00 pm", "07:00 pm", "08:00 pm"), disabledSlots = listOf("08:00 pm"))
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TimeCategory(
    title: String,
    slots: List<String>,
    selectedSlot: String? = null,
    disabledSlots: List<String> = emptyList()
) {
    Column {
        Text(text = title, color = Color.Gray, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(12.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            slots.forEach { slot ->
                val isSelected = slot == selectedSlot
                val isDisabled = disabledSlots.contains(slot)
                
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (isDisabled) Color(0xFFF3F3F3) else if (isSelected) Color.White else Color.White)
                        .border(
                            width = 1.dp,
                            color = if (isDisabled) Color.Transparent else if (isSelected) Color(0xFF007BFF) else Color(0xFFE0E0E0),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .clickable(enabled = !isDisabled) { }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = slot,
                        fontSize = 13.sp,
                        color = if (isDisabled) Color.LightGray else if (isSelected) Color(0xFF007BFF) else Color.Black,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}

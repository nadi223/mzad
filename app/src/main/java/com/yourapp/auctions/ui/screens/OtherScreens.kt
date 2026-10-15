package com.yourapp.auctions.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourapp.auctions.ui.components.ActionButton
import com.yourapp.auctions.ui.components.AuctionBottomBar
import com.yourapp.auctions.ui.components.AuctionHeader
import com.yourapp.auctions.ui.components.AuctionListCard
import com.yourapp.auctions.ui.components.AuctionTab
import com.yourapp.auctions.ui.components.ContactButton
import com.yourapp.auctions.ui.components.SupportFab
import com.yourapp.auctions.ui.model.AuctionItemUi
import com.yourapp.auctions.ui.theme.AuctionTheme

// ================================================================ Wallet Screen

@Composable
fun WalletScreen(
    balance: Double,
    lockedCollateral: Double,
    onTab: (AuctionTab) -> Unit,
    onAddAuction: () -> Unit,
    onSupport: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = AuctionTheme.colors
    var showDepositDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        containerColor = colors.background,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            AuctionHeader(
                brandName = "محفظة مزاد",
                brandTagline = "MZAD WALLET",
                languageLabel = "English",
                onContactClick = onSupport,
            )
        },
        bottomBar = {
            AuctionBottomBar(
                selected = AuctionTab.Wallet,
                onSelect = onTab,
                onAddClick = onAddAuction,
            )
        },
        floatingActionButton = { SupportFab(onClick = onSupport) },
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = AuctionTheme.shapes.bigCard,
                    colors = CardDefaults.cardColors(containerColor = colors.surfaceGreen),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        Text(
                            text = "الرصيد المتاح للمزايدة",
                            color = Color.White.copy(alpha = 0.8f),
                            style = AuctionTheme.type.body,
                        )
                        Text(
                            text = "$${"%,.2f".format(balance)}",
                            color = colors.gold,
                            style = AuctionTheme.type.price.copy(fontSize = 36.sp),
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Icon(Icons.Filled.Lock, contentDescription = null, tint = Color.White.copy(alpha = 0.8f), modifier = Modifier.size(16.dp))
                                Text("تأمين المزايدات المحجوز", color = Color.White.copy(alpha = 0.8f), style = AuctionTheme.type.caption)
                            }
                            Text("$${"%,.2f".format(lockedCollateral)}", color = Color.White, style = AuctionTheme.type.chip)
                        }

                        Spacer(Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            Button(
                                onClick = { showDepositDialog = true },
                                colors = ButtonDefaults.buttonColors(containerColor = colors.gold),
                                shape = AuctionTheme.shapes.button,
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                                    .testTag("wallet_deposit_button"),
                            ) {
                                Icon(Icons.Filled.Add, contentDescription = null, tint = colors.priceGreen)
                                Spacer(Modifier.width(6.dp))
                                Text("شحن المحفظة", color = colors.priceGreen, style = AuctionTheme.type.chip)
                            }
                        }
                    }
                }
            }

            item {
                Text(
                    text = "سجل العمليات الأخير",
                    color = colors.textPrimary,
                    style = AuctionTheme.type.sectionTitle,
                    modifier = Modifier.padding(vertical = 4.dp),
                )
            }

            items(
                listOf(
                    Triple("شحن رصيد عبر تحويل بنكي", "+$5,000.00", "منذ ساعتين · مكتمل"),
                    Triple("حجز تأمين مزاد شيفروليه كروز", "-$500.00", "اليوم 14:20 · جاري"),
                    Triple("استرداد تأمين مزاد مزرعة المشرفة", "+$1,200.00", "أمس · مكتمل"),
                    Triple("مزايدة مؤكدة - مرسيدس E300", "-$1,000.00", "12 أكتوبر · جاري"),
                ),
            ) { (title, amount, date) ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = AuctionTheme.shapes.card,
                    colors = CardDefaults.cardColors(containerColor = colors.card),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .background(
                                        if (amount.startsWith("+")) colors.chipBackground else Color(0xFFFFEBEE),
                                        CircleShape,
                                    ),
                                contentAlignment = Alignment.Center,
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.AccountBalanceWallet,
                                    contentDescription = null,
                                    tint = if (amount.startsWith("+")) colors.priceGreen else colors.live,
                                )
                            }
                            Column {
                                Text(title, style = AuctionTheme.type.cardTitle, color = colors.textPrimary)
                                Text(date, style = AuctionTheme.type.caption, color = colors.textMuted)
                            }
                        }
                        Text(
                            amount,
                            style = AuctionTheme.type.chip.copy(fontSize = 16.sp),
                            color = if (amount.startsWith("+")) colors.priceGreen else colors.live,
                        )
                    }
                }
            }
        }
    }

    if (showDepositDialog) {
        AlertDialog(
            onDismissRequest = { showDepositDialog = false },
            title = { Text("شحن المحفظة", style = AuctionTheme.type.sectionTitle) },
            text = {
                Text(
                    "يمكنك شحن رصيد المحفظة عبر الحوالات البنكية المعتمدة، سيريتل كاش، أو مكاتب مزاد المعتمدة للمعاينة وتأمين الدخول في المزاد.",
                    style = AuctionTheme.type.body,
                )
            },
            confirmButton = {
                TextButton(onClick = { showDepositDialog = false }) {
                    Text("تم", color = colors.priceGreen, style = AuctionTheme.type.chip)
                }
            },
        )
    }
}

// ================================================================ My Auctions Screen

@Composable
fun MyAuctionsScreen(
    allAuctions: List<AuctionItemUi>,
    onItemClick: (AuctionItemUi) -> Unit,
    onTab: (AuctionTab) -> Unit,
    onAddAuction: () -> Unit,
    onSupport: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = AuctionTheme.colors
    var selectedIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("مزايداتي الجارية", "المزادات الرابحة", "إعلاناتي المعروضة")

    Scaffold(
        modifier = modifier,
        containerColor = colors.background,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            AuctionHeader(
                brandName = "مزاداتي",
                brandTagline = "MY AUCTIONS",
                languageLabel = "English",
                onContactClick = onSupport,
            )
        },
        bottomBar = {
            AuctionBottomBar(
                selected = AuctionTab.MyAuctions,
                onSelect = onTab,
                onAddClick = onAddAuction,
            )
        },
        floatingActionButton = { SupportFab(onClick = onSupport) },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            TabRow(
                selectedTabIndex = selectedIndex,
                containerColor = colors.card,
                contentColor = colors.priceGreen,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedIndex]),
                        color = colors.gold,
                        height = 3.dp,
                    )
                },
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedIndex == index,
                        onClick = { selectedIndex = index },
                        text = {
                            Text(
                                title,
                                style = AuctionTheme.type.chip,
                                color = if (selectedIndex == index) colors.priceGreen else colors.textMuted,
                            )
                        },
                    )
                }
            }

            val displayedItems = when (selectedIndex) {
                0 -> allAuctions.filter { it.isLive }
                1 -> allAuctions.filter { it.isEnded || it.isFeatured }
                else -> allAuctions.filter { it.isDirectSale }
            }

            if (displayedItems.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Icon(Icons.Filled.Gavel, contentDescription = null, tint = colors.textMuted, modifier = Modifier.size(56.dp))
                        Text("لا توجد مزادات في هذه القائمة حالياً", style = AuctionTheme.type.body, color = colors.textMuted)
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(displayedItems, key = { "my-${it.id}" }) { item ->
                        AuctionListCard(
                            item = item,
                            onClick = { onItemClick(item) },
                            onFavoriteClick = {},
                        )
                    }
                }
            }
        }
    }
}

// ================================================================ Account Screen

@Composable
fun AccountScreen(
    onTab: (AuctionTab) -> Unit,
    onAddAuction: () -> Unit,
    onLanguageToggle: () -> Unit,
    onSupport: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = AuctionTheme.colors
    var notificationsEnabled by remember { mutableStateOf(true) }
    var soundEnabled by remember { mutableStateOf(true) }

    Scaffold(
        modifier = modifier,
        containerColor = colors.background,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            AuctionHeader(
                brandName = "حسابي الشخصي",
                brandTagline = "MZAD PROFILE",
                languageLabel = "English",
                onContactClick = onSupport,
            )
        },
        bottomBar = {
            AuctionBottomBar(
                selected = AuctionTab.Account,
                onSelect = onTab,
                onAddClick = onAddAuction,
            )
        },
        floatingActionButton = { SupportFab(onClick = onSupport) },
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = AuctionTheme.shapes.bigCard,
                    colors = CardDefaults.cardColors(containerColor = colors.card),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .size(68.dp)
                                .clip(CircleShape)
                                .background(colors.surfaceGreen),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(Icons.Filled.Person, contentDescription = null, tint = colors.gold, modifier = Modifier.size(42.dp))
                        }
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text("نادر المحسن", style = AuctionTheme.type.sectionTitle, color = colors.textPrimary)
                                Icon(Icons.Filled.Verified, contentDescription = null, tint = colors.priceGreen, modifier = Modifier.size(20.dp))
                            }
                            Text("+963 944 123 456", style = AuctionTheme.type.body, color = colors.textMuted)
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Box(Modifier.size(8.dp).background(colors.priceGreen, CircleShape))
                                Text("عضوية ذهبية موثقة", style = AuctionTheme.type.caption, color = colors.priceGreen)
                            }
                        }
                    }
                }
            }

            item {
                Text("الإعدادات والتفضيلات", style = AuctionTheme.type.sectionTitle, color = colors.textPrimary)
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = AuctionTheme.shapes.card,
                    colors = CardDefaults.cardColors(containerColor = colors.card),
                ) {
                    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                        SettingToggleRow(
                            icon = Icons.Filled.Notifications,
                            title = "تنبيهات المزايدة الحية",
                            checked = notificationsEnabled,
                            onCheckedChange = { notificationsEnabled = it },
                        )
                        SettingToggleRow(
                            icon = Icons.Filled.ConfirmationNumber,
                            title = "تنبيهات انتهاء الوقت",
                            checked = soundEnabled,
                            onCheckedChange = { soundEnabled = it },
                        )
                        SettingActionRow(
                            icon = Icons.Filled.Language,
                            title = "لغة التطبيق (العربية / English)",
                            subtitle = "اضغط للتبديل السريع",
                            onClick = onLanguageToggle,
                        )
                        SettingActionRow(
                            icon = Icons.Filled.Security,
                            title = "سياسة الخصوصية وشروط المزاد",
                            subtitle = "الضمانات والتأمينات القانونية",
                            onClick = {},
                        )
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = AuctionTheme.shapes.card,
                    colors = CardDefaults.cardColors(containerColor = colors.surfaceGreen),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(Icons.Filled.SupportAgent, contentDescription = null, tint = colors.gold, modifier = Modifier.size(28.dp))
                            Text("مركز خدمة عملاء مزاد", style = AuctionTheme.type.cardTitle.copy(fontSize = 18.sp), color = Color.White)
                        }
                        Text("فريقنا متواجد 24/7 للإجابة على استفساراتكم ومساعدتكم في إجراءات المعاينة والمزايدة.", style = AuctionTheme.type.body, color = Color.White.copy(alpha = 0.8f))
                        Spacer(Modifier.height(4.dp))
                        ContactButton(
                            text = "محادثة الدعم الفني",
                            onClick = onSupport,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingToggleRow(
    icon: ImageVector,
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    val colors = AuctionTheme.colors
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Icon(icon, contentDescription = null, tint = colors.priceGreen, modifier = Modifier.size(24.dp))
            Text(title, style = AuctionTheme.type.body, color = colors.textPrimary)
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = colors.priceGreen,
            ),
        )
    }
}

@Composable
private fun SettingActionRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
) {
    val colors = AuctionTheme.colors
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Icon(icon, contentDescription = null, tint = colors.priceGreen, modifier = Modifier.size(24.dp))
            Column {
                Text(title, style = AuctionTheme.type.body, color = colors.textPrimary)
                Text(subtitle, style = AuctionTheme.type.caption, color = colors.textMuted)
            }
        }
    }
}

// ================================================================ Add Auction Bottom Sheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAuctionBottomSheet(
    onDismiss: () -> Unit,
    onSubmit: (AuctionItemUi) -> Unit,
) {
    val colors = AuctionTheme.colors
    var title by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("المركبات") }
    var location by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var isDirectSale by remember { mutableStateOf(false) }
    var description by remember { mutableStateOf("") }

    val categories = listOf("المركبات", "العقارات", "المعدات الثقيلة", "المجوهرات والذهب", "الإلكترونيات")

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = colors.card,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp)
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("إضافة عنصر جديد للمزاد", style = AuctionTheme.type.sectionTitle, color = colors.textPrimary)
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Filled.Close, contentDescription = "إغلاق", tint = colors.textMuted)
                }
            }

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("عنوان الإعلان / العنصر") },
                placeholder = { Text("مثال: تويوتا لاندكروزر 2023") },
                modifier = Modifier.fillMaxWidth().testTag("add_item_title_input"),
                singleLine = true,
            )

            Text("اختر الفئة:", style = AuctionTheme.type.chip, color = colors.textPrimary)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                categories.take(3).forEach { cat ->
                    val selected = cat == category
                    Box(
                        modifier = Modifier
                            .clip(AuctionTheme.shapes.chip)
                            .background(if (selected) colors.priceGreen else colors.chipBackground)
                            .clickable { category = cat }
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                    ) {
                        Text(cat, color = if (selected) Color.White else colors.priceGreen, style = AuctionTheme.type.chip)
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("نوع البيع: بيع مباشر بسعر محدد", style = AuctionTheme.type.body, color = colors.textPrimary)
                Switch(
                    checked = isDirectSale,
                    onCheckedChange = { isDirectSale = it },
                    colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = colors.priceGreen),
                )
            }

            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                label = { Text(if (isDirectSale) "السعر المطلوب ($)" else "المزايدة الافتتاحية ($)") },
                modifier = Modifier.fillMaxWidth().testTag("add_item_price_input"),
                singleLine = true,
            )

            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("المحافظة / المدينة") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("الوصف والمواصفات الفنية") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 3,
            )

            ActionButton(
                text = "نشر العنصر في المنصة",
                onClick = {
                    if (title.isNotBlank()) {
                        val newItem = AuctionItemUi(
                            id = (System.currentTimeMillis() % 100000).toString(),
                            title = title,
                            subtitle = description.ifBlank { "عنصر جديد متاح للمعاينة الفورية" },
                            categoryName = category,
                            location = location.ifBlank { "دمشق" },
                            priceText = if (price.isNotBlank()) "$${price.trim()}" else "$1,000",
                            isDirectSale = isDirectSale,
                            isLive = !isDirectSale,
                            bidsCount = 0,
                            timeLeft = if (!isDirectSale) "7ي 00س" else null,
                            imageUrls = listOf(
                                "https://images.unsplash.com/photo-1552519507-da3b142c6e3d?w=800&auto=format&fit=crop&q=80",
                            ),
                        )
                        onSubmit(newItem)
                    }
                },
                container = colors.priceGreen,
                content = Color.White,
                modifier = Modifier.fillMaxWidth().testTag("add_item_submit_button"),
            )
        }
    }
}

// ================================================================ Bid Dialog

@Composable
fun BidDialog(
    item: AuctionItemUi,
    onDismiss: () -> Unit,
    onConfirmBid: (String) -> Unit,
) {
    val colors = AuctionTheme.colors
    var increment by remember { mutableIntStateOf(100) }
    var currentNumericPrice = remember(item.priceText) {
        item.priceText?.replace(Regex("[^0-9]"), "")?.toIntOrNull() ?: 1000
    }
    val newBid = currentNumericPrice + increment

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Icons.Filled.Gavel, contentDescription = null, tint = colors.gold)
                Text("المزايدة على: ${item.title}", style = AuctionTheme.type.cardTitle)
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    "المزايدة الحالية: ${item.priceText}",
                    style = AuctionTheme.type.body,
                    color = colors.textMuted,
                )
                Text(
                    "مزايدتك الجديدة المقترحة:",
                    style = AuctionTheme.type.chip,
                    color = colors.textPrimary,
                )
                Text(
                    "$$newBid",
                    style = AuctionTheme.type.price.copy(color = colors.priceGreen, fontSize = 32.sp),
                )
                Text("اختر قيمة الرفع السريع:", style = AuctionTheme.type.caption, color = colors.textMuted)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(50, 100, 250, 500).forEach { inc ->
                        val selected = increment == inc
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (selected) colors.priceGreen else colors.chipBackground)
                                .clickable { increment = inc }
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                        ) {
                            Text("+$inc", color = if (selected) Color.White else colors.priceGreen, style = AuctionTheme.type.chip)
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirmBid("$$newBid") },
                colors = ButtonDefaults.buttonColors(containerColor = colors.gold),
                modifier = Modifier.testTag("confirm_bid_action_button"),
            ) {
                Text("تأكيد المزايدة الآن", color = colors.priceGreen, style = AuctionTheme.type.chip)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("إلغاء", color = colors.textMuted)
            }
        },
    )
}

// ================================================================ Contact Dialog

@Composable
fun ContactSellerDialog(
    itemTitle: String,
    onDismiss: () -> Unit,
) {
    val colors = AuctionTheme.colors
    var messageSent by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Icons.Filled.Call, contentDescription = null, tint = colors.whatsapp)
                Text("التواصل المباشر مع البائع", style = AuctionTheme.type.sectionTitle)
            }
        },
        text = {
            if (messageSent) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = colors.priceGreen)
                    Text("تم إرسال طلب التواصل بنجاح! سيتواصل معك ممثل المزاد خلال 15 دقيقة.", style = AuctionTheme.type.body)
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("أنت تتواصل بخصوص: $itemTitle", style = AuctionTheme.type.body, color = colors.textPrimary)
                    Text(
                        "لضمان أمان عمليتك، تتم جميع الاتصالات والمعاينات عبر وسطاء معتمدين من منصة مزاد.",
                        style = AuctionTheme.type.caption,
                        color = colors.textMuted,
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colors.chipBackground, RoundedCornerShape(12.dp))
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Icon(Icons.Filled.Call, contentDescription = null, tint = colors.whatsapp)
                        Text("+963 11 987654 (واتساب معتمد)", style = AuctionTheme.type.chip, color = colors.priceGreen)
                    }
                }
            }
        },
        confirmButton = {
            if (!messageSent) {
                Button(
                    onClick = { messageSent = true },
                    colors = ButtonDefaults.buttonColors(containerColor = colors.whatsapp),
                ) {
                    Text("إرسال رسالة واتساب", color = Color.White, style = AuctionTheme.type.chip)
                }
            } else {
                TextButton(onClick = onDismiss) {
                    Text("إغلاق", color = colors.priceGreen)
                }
            }
        },
        dismissButton = {
            if (!messageSent) {
                TextButton(onClick = onDismiss) {
                    Text("إلغاء", color = colors.textMuted)
                }
            }
        },
    )
}

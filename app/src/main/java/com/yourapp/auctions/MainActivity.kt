package com.yourapp.auctions

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.yourapp.auctions.data.AuctionsData
import com.yourapp.auctions.ui.components.AuctionTab
import com.yourapp.auctions.ui.model.AuctionDetailUi
import com.yourapp.auctions.ui.model.AuctionItemUi
import com.yourapp.auctions.ui.model.AuctionsHomeUiState
import com.yourapp.auctions.ui.screens.AccountScreen
import com.yourapp.auctions.ui.screens.AddAuctionBottomSheet
import com.yourapp.auctions.ui.screens.AuctionDetailActions
import com.yourapp.auctions.ui.screens.AuctionDetailScreen
import com.yourapp.auctions.ui.screens.AuctionsHomeActions
import com.yourapp.auctions.ui.screens.AuctionsHomeScreen
import com.yourapp.auctions.ui.screens.BidDialog
import com.yourapp.auctions.ui.screens.ContactSellerDialog
import com.yourapp.auctions.ui.screens.MyAuctionsScreen
import com.yourapp.auctions.ui.screens.WalletScreen
import com.yourapp.auctions.ui.theme.AuctionStrings
import com.yourapp.auctions.ui.theme.AuctionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MzadApp()
        }
    }
}

enum class AppScreen {
    Home,
    Detail,
}

@Composable
fun MzadApp() {
    var isArabic by remember { mutableStateOf(true) }
    val layoutDirection = if (isArabic) LayoutDirection.Rtl else LayoutDirection.Ltr

    val arabicStrings = remember { AuctionStrings() }
    val englishStrings = remember {
        AuctionStrings(
            searchHint = "Search auctions...",
            addItemTitle = "List New Item for Auction",
            addItemSubtitle = "Submit your item easily via the app",
            viewAll = "View All",
            allAuctions = "All Auctions",
            noBids = "No bids yet",
            currentBid = "Current Bid",
            bidsUnit = "bids",
            live = "LIVE",
            directSale = "Direct Sale",
            featured = "Featured",
            ended = "Ended",
            contactSeller = "Contact Seller",
            bidNow = "Bid Now",
            navHome = "Home",
            navWallet = "Wallet",
            navMyAuctions = "My Auctions",
            navAccount = "Account",
            navAdd = "Add Item",
        )
    }

    val currentStrings = if (isArabic) arabicStrings else englishStrings

    var selectedTab by remember { mutableStateOf(AuctionTab.Home) }
    var currentScreen by remember { mutableStateOf(AppScreen.Home) }
    var selectedAuctionDetail by remember { mutableStateOf<AuctionDetailUi?>(null) }

    var allAuctions by remember { mutableStateOf(AuctionsData.initialAuctions) }
    var sections by remember { mutableStateOf(AuctionsData.initialSections) }
    var walletBalance by remember { mutableDoubleStateOf(14500.0) }
    var lockedCollateral by remember { mutableDoubleStateOf(1500.0) }

    // Dialog & sheet states
    var showAddAuctionSheet by remember { mutableStateOf(false) }
    var activeBidItem by remember { mutableStateOf<AuctionItemUi?>(null) }
    var contactItemTitle by remember { mutableStateOf<String?>(null) }
    var showNotificationsDialog by remember { mutableStateOf(false) }
    var showSearchOverlay by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategoryFilter by remember { mutableStateOf<String?>(null) }

    AuctionTheme(
        strings = currentStrings,
        layoutDirection = layoutDirection,
    ) {
        val filteredAuctions = remember(allAuctions, searchQuery, selectedCategoryFilter) {
            allAuctions.filter { item ->
                val matchesQuery = searchQuery.isBlank() ||
                    item.title.contains(searchQuery, ignoreCase = true) ||
                    (item.categoryName?.contains(searchQuery, ignoreCase = true) == true) ||
                    (item.location?.contains(searchQuery, ignoreCase = true) == true)
                val matchesCategory = selectedCategoryFilter == null || item.categoryName == selectedCategoryFilter
                matchesQuery && matchesCategory
            }
        }

        val homeUiState = AuctionsHomeUiState(
            brandName = if (isArabic) "مزاد" else "MZAD",
            brandTagline = if (isArabic) "مزادات وبيوع مباشرة" else "LIVE AUCTIONS & DIRECT SALE",
            languageLabel = if (isArabic) "English" else "العربية",
            banners = AuctionsData.sampleBanners,
            categories = AuctionsData.sampleCategories,
            sections = sections,
            allAuctions = filteredAuctions,
            selectedTab = selectedTab,
        )

        BackHandler(enabled = currentScreen == AppScreen.Detail || selectedCategoryFilter != null || searchQuery.isNotEmpty()) {
            if (currentScreen == AppScreen.Detail) {
                currentScreen = AppScreen.Home
            } else if (selectedCategoryFilter != null) {
                selectedCategoryFilter = null
            } else if (searchQuery.isNotEmpty()) {
                searchQuery = ""
            }
        }

        Surface(modifier = Modifier.fillMaxSize(), color = AuctionTheme.colors.background) {
            when {
                currentScreen == AppScreen.Detail && selectedAuctionDetail != null -> {
                    AuctionDetailScreen(
                        detail = selectedAuctionDetail!!,
                        actions = AuctionDetailActions(
                            onBack = { currentScreen = AppScreen.Home },
                            onToggleVisibility = {},
                            onShare = {},
                            onMore = {},
                            onPrimaryAction = {
                                val item = allAuctions.find { it.id == selectedAuctionDetail!!.id }
                                if (item != null) {
                                    if (item.isDirectSale) {
                                        contactItemTitle = item.title
                                    } else {
                                        activeBidItem = item
                                    }
                                }
                            },
                        ),
                    )
                }

                selectedTab == AuctionTab.Home -> {
                    Column(Modifier.fillMaxSize()) {
                        if (selectedCategoryFilter != null) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(AuctionTheme.colors.headerDark)
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    "تصفية حسب: $selectedCategoryFilter",
                                    color = Color.White,
                                    style = AuctionTheme.type.chip,
                                )
                                IconButton(onClick = { selectedCategoryFilter = null }) {
                                    Icon(Icons.Filled.Close, contentDescription = "إلغاء التصفية", tint = Color.White)
                                }
                            }
                        }

                        AuctionsHomeScreen(
                            state = homeUiState,
                            actions = AuctionsHomeActions(
                                onLanguage = { isArabic = !isArabic },
                                onSearch = { showSearchOverlay = true },
                                onNotifications = { showNotificationsDialog = true },
                                onContactUs = { contactItemTitle = "منصة مزاد العامة" },
                                onSupport = { contactItemTitle = "الدعم الفني المباشر" },
                                onAddItem = { showAddAuctionSheet = true },
                                onAddAuction = { showAddAuctionSheet = true },
                                onTab = { selectedTab = it },
                                onCategory = { cat ->
                                    selectedCategoryFilter = if (selectedCategoryFilter == cat.title) null else cat.title
                                },
                                onItem = { item ->
                                    selectedAuctionDetail = AuctionsData.createDetailFor(item)
                                    currentScreen = AppScreen.Detail
                                },
                                onFavorite = { item ->
                                    allAuctions = allAuctions.map {
                                        if (it.id == item.id) it.copy(isFavorite = !it.isFavorite) else it
                                    }
                                },
                                onContactSeller = { item ->
                                    contactItemTitle = item.title
                                },
                            ),
                        )
                    }
                }

                selectedTab == AuctionTab.Wallet -> {
                    WalletScreen(
                        balance = walletBalance,
                        lockedCollateral = lockedCollateral,
                        onTab = { selectedTab = it },
                        onAddAuction = { showAddAuctionSheet = true },
                        onSupport = { contactItemTitle = "دعم المحفظة والمالية" },
                    )
                }

                selectedTab == AuctionTab.MyAuctions -> {
                    MyAuctionsScreen(
                        allAuctions = allAuctions,
                        onItemClick = { item ->
                            selectedAuctionDetail = AuctionsData.createDetailFor(item)
                            currentScreen = AppScreen.Detail
                        },
                        onTab = { selectedTab = it },
                        onAddAuction = { showAddAuctionSheet = true },
                        onSupport = { contactItemTitle = "دعم المزادات الشخصية" },
                    )
                }

                selectedTab == AuctionTab.Account -> {
                    AccountScreen(
                        onTab = { selectedTab = it },
                        onAddAuction = { showAddAuctionSheet = true },
                        onLanguageToggle = { isArabic = !isArabic },
                        onSupport = { contactItemTitle = "خدمة العملاء العامة" },
                    )
                }
            }
        }

        // Add Auction Bottom Sheet
        if (showAddAuctionSheet) {
            AddAuctionBottomSheet(
                onDismiss = { showAddAuctionSheet = false },
                onSubmit = { newItem ->
                    allAuctions = listOf(newItem) + allAuctions
                    showAddAuctionSheet = false
                },
            )
        }

        // Bid Dialog
        if (activeBidItem != null) {
            BidDialog(
                item = activeBidItem!!,
                onDismiss = { activeBidItem = null },
                onConfirmBid = { newPrice ->
                    val targetId = activeBidItem!!.id
                    allAuctions = allAuctions.map {
                        if (it.id == targetId) {
                            it.copy(
                                priceText = newPrice,
                                bidsCount = it.bidsCount + 1,
                            )
                        } else it
                    }
                    sections = sections.map { sec ->
                        sec.copy(
                            items = sec.items.map {
                                if (it.id == targetId) {
                                    it.copy(priceText = newPrice, bidsCount = it.bidsCount + 1)
                                } else it
                            },
                        )
                    }
                    if (selectedAuctionDetail?.id == targetId) {
                        selectedAuctionDetail = selectedAuctionDetail!!.copy(priceText = newPrice)
                    }
                    lockedCollateral += 100.0
                    activeBidItem = null
                },
            )
        }

        // Contact Seller Dialog
        if (contactItemTitle != null) {
            ContactSellerDialog(
                itemTitle = contactItemTitle!!,
                onDismiss = { contactItemTitle = null },
            )
        }

        // Notifications Dialog
        if (showNotificationsDialog) {
            AlertDialog(
                onDismissRequest = { showNotificationsDialog = false },
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(Icons.Filled.Notifications, contentDescription = null, tint = AuctionTheme.colors.gold)
                        Text("التنبيهات والإشعارات", style = AuctionTheme.type.sectionTitle)
                    }
                },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text("• تم قبول إعلانك الأخير في فئة المركبات.", style = AuctionTheme.type.body)
                        Text("• بدأ مزاد شيفروليه كروز 2009 رسميًا.", style = AuctionTheme.type.body)
                        Text("• لديك رصيد متاح في المحفظة للمزايدة.", style = AuctionTheme.type.body)
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showNotificationsDialog = false }) {
                        Text("حسناً", color = AuctionTheme.colors.priceGreen)
                    }
                },
            )
        }

        // Search Overlay Dialog
        if (showSearchOverlay) {
            AlertDialog(
                onDismissRequest = { showSearchOverlay = false },
                title = { Text("بحث في المزادات", style = AuctionTheme.type.sectionTitle) },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("اكتب اسم السيارة، العقار، أو الفئة...") },
                            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = AuctionTheme.colors.priceGreen,
                            ),
                        )
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showSearchOverlay = false }) {
                        Text("بحث", color = AuctionTheme.colors.priceGreen, style = AuctionTheme.type.chip)
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        searchQuery = ""
                        showSearchOverlay = false
                    }) {
                        Text("مسح", color = AuctionTheme.colors.textMuted)
                    }
                },
            )
        }
    }
}

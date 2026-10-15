package com.yourapp.auctions.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yourapp.auctions.ui.components.AddItemBanner
import com.yourapp.auctions.ui.components.AuctionBottomBar
import com.yourapp.auctions.ui.components.AuctionCompactCard
import com.yourapp.auctions.ui.components.AuctionHeader
import com.yourapp.auctions.ui.components.AuctionListCard
import com.yourapp.auctions.ui.components.AuctionTab
import com.yourapp.auctions.ui.components.CategoryMosaic
import com.yourapp.auctions.ui.components.DirectSaleCard
import com.yourapp.auctions.ui.components.HeroBannerPager
import com.yourapp.auctions.ui.components.SectionHeader
import com.yourapp.auctions.ui.components.SupportFab
import com.yourapp.auctions.ui.model.AuctionCategoryUi
import com.yourapp.auctions.ui.model.AuctionItemUi
import com.yourapp.auctions.ui.model.AuctionSectionUi
import com.yourapp.auctions.ui.model.AuctionsHomeUiState
import com.yourapp.auctions.ui.model.HeroBannerUi
import com.yourapp.auctions.ui.model.SectionStyle
import com.yourapp.auctions.ui.theme.AuctionTheme

data class AuctionsHomeActions(
    val onMenu: () -> Unit = {},
    val onNotifications: () -> Unit = {},
    val onContactUs: () -> Unit = {},
    val onLanguage: () -> Unit = {},
    val onSearch: () -> Unit = {},
    val onBanner: (HeroBannerUi) -> Unit = {},
    val onAddItem: () -> Unit = {},
    val onCategory: (AuctionCategoryUi) -> Unit = {},
    val onViewAll: (AuctionSectionUi) -> Unit = {},
    val onItem: (AuctionItemUi) -> Unit = {},
    val onFavorite: (AuctionItemUi) -> Unit = {},
    val onContactSeller: (AuctionItemUi) -> Unit = {},
    val onSupport: () -> Unit = {},
    val onTab: (AuctionTab) -> Unit = {},
    val onAddAuction: () -> Unit = {},
)

/**
 * Stateless. Everything shown here comes from [state], so hiding a category / section /
 * banner for marketing reasons = removing it from the lists in the ViewModel.
 */
@Composable
fun AuctionsHomeScreen(
    state: AuctionsHomeUiState,
    actions: AuctionsHomeActions,
    modifier: Modifier = Modifier,
) {
    val colors = AuctionTheme.colors
    val strings = AuctionTheme.strings

    Scaffold(
        modifier = modifier,
        containerColor = colors.background,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            AuctionHeader(
                brandName = state.brandName,
                brandTagline = state.brandTagline,
                languageLabel = state.languageLabel,
                onMenuClick = actions.onMenu,
                onNotificationsClick = actions.onNotifications,
                onContactClick = actions.onContactUs,
                onLanguageClick = actions.onLanguage,
                onSearchClick = actions.onSearch,
            )
        },
        bottomBar = {
            AuctionBottomBar(
                selected = state.selectedTab,
                onSelect = actions.onTab,
                onAddClick = actions.onAddAuction,
            )
        },
        floatingActionButton = { SupportFab(onClick = actions.onSupport) },
        floatingActionButtonPosition = FabPosition.End,
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + 16.dp,
                bottom = padding.calculateBottomPadding() + 24.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            if (state.banners.isNotEmpty()) {
                item(key = "hero") { HeroBannerPager(state.banners, actions.onBanner) }
            }

            item(key = "add-item") {
                AddItemBanner(onClick = actions.onAddItem, modifier = Modifier.padding(horizontal = 16.dp))
            }

            if (state.categories.isNotEmpty()) {
                item(key = "categories") {
                    CategoryMosaic(
                        categories = state.categories,
                        onClick = actions.onCategory,
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )
                }
            }

            items(state.sections, key = { "section-${it.id}" }) { section ->
                Column {
                    SectionHeader(
                        title = section.title,
                        count = section.count,
                        onViewAll = { actions.onViewAll(section) },
                    )
                    Spacer(Modifier.height(12.dp))
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        items(section.items, key = { it.id }) { item ->
                            when (section.style) {
                                SectionStyle.Auction -> AuctionCompactCard(
                                    item = item,
                                    onClick = { actions.onItem(item) },
                                )
                                SectionStyle.DirectSale -> DirectSaleCard(
                                    item = item,
                                    onClick = { actions.onItem(item) },
                                    onContact = { actions.onContactSeller(item) },
                                )
                            }
                        }
                    }
                }
            }

            if (state.allAuctions.isNotEmpty()) {
                item(key = "all-title") {
                    Text(
                        text = strings.allAuctions,
                        color = colors.textPrimary,
                        style = AuctionTheme.type.sectionTitle,
                        modifier = Modifier.padding(horizontal = 20.dp),
                    )
                }
                items(state.allAuctions, key = { "auction-${it.id}" }) { item ->
                    AuctionListCard(
                        item = item,
                        onClick = { actions.onItem(item) },
                        onFavoriteClick = { actions.onFavorite(item) },
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )
                }
            }
        }
    }
}

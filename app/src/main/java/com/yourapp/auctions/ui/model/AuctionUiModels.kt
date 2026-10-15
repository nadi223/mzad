package com.yourapp.auctions.ui.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import com.yourapp.auctions.ui.components.AuctionTab

/*
 * Pure UI models. Map your existing domain/API models into these in the ViewModel.
 * Prices and time-left are already-formatted strings, so currency and date rules
 * stay in your own app logic and the UI never assumes a currency.
 */

enum class CategorySpan(val units: Int) { Small(1), Wide(2) }

@Immutable
data class HeroBannerUi(
    val id: String,
    val title: String,
    val subtitle: String? = null,
    val imageUrl: String? = null,
)

@Immutable
data class AuctionCategoryUi(
    val id: String,
    val title: String,
    val subtitle: String? = null,
    val imageUrl: String? = null,
    val count: Int = 0,
    val span: CategorySpan = CategorySpan.Small,
)

@Immutable
data class AuctionItemUi(
    val id: String,
    val title: String,
    val subtitle: String? = null,
    val categoryName: String? = null,
    val imageUrls: List<String> = emptyList(),
    val isLive: Boolean = false,
    val isEnded: Boolean = false,
    val isFeatured: Boolean = false,
    val isDirectSale: Boolean = false,
    val isFavorite: Boolean = false,
    val bidsCount: Int = 0,
    val timeLeft: String? = null,
    val priceText: String? = null,   // current bid, or asking price for direct sale
    val location: String? = null,
) {
    val coverUrl: String? get() = imageUrls.firstOrNull()
}

enum class SectionStyle { Auction, DirectSale }

@Immutable
data class AuctionSectionUi(
    val id: String,
    val title: String,
    val count: Int,
    val style: SectionStyle = SectionStyle.Auction,
    val items: List<AuctionItemUi>,
)

@Immutable
data class AuctionsHomeUiState(
    val brandName: String,
    val brandTagline: String,
    val languageLabel: String = "English",
    val banners: List<HeroBannerUi> = emptyList(),
    val categories: List<AuctionCategoryUi> = emptyList(),
    val sections: List<AuctionSectionUi> = emptyList(),
    val allAuctions: List<AuctionItemUi> = emptyList(),
    val selectedTab: AuctionTab = AuctionTab.Home,
)

enum class SpecTone { Green, Blue, Gold }

@Immutable
data class SpecChipUi(
    val text: String,
    val tone: SpecTone = SpecTone.Green,
    val icon: ImageVector? = null,
)

enum class DetailAction { Contact, Bid }

@Immutable
data class AuctionDetailUi(
    val id: String,
    val referenceLabel: String,
    val title: String,
    val location: String? = null,
    val imageUrls: List<String> = emptyList(),
    val isEnded: Boolean = false,
    val isFeatured: Boolean = false,
    val specChips: List<SpecChipUi> = emptyList(),
    val description: String? = null,
    val priceLabel: String,
    val priceText: String,
    val action: DetailAction = DetailAction.Contact,
)

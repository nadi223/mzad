package com.yourapp.auctions.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Public
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import com.yourapp.auctions.ui.model.AuctionCategoryUi
import com.yourapp.auctions.ui.model.AuctionDetailUi
import com.yourapp.auctions.ui.model.AuctionItemUi
import com.yourapp.auctions.ui.model.AuctionSectionUi
import com.yourapp.auctions.ui.model.AuctionsHomeUiState
import com.yourapp.auctions.ui.model.CategorySpan
import com.yourapp.auctions.ui.model.DetailAction
import com.yourapp.auctions.ui.model.HeroBannerUi
import com.yourapp.auctions.ui.model.SectionStyle
import com.yourapp.auctions.ui.model.SpecChipUi
import com.yourapp.auctions.ui.model.SpecTone
import com.yourapp.auctions.ui.theme.AuctionTheme

// Sample data only for @Preview – image URLs are empty so the placeholder is shown.
// Prices are free-form strings: format them with your own currency rules.

private val sampleAuction = AuctionItemUi(
    id = "1",
    title = "شيفروليه كروز 2009",
    subtitle = "162 كم · لاذقية",
    categoryName = "مركبات",
    imageUrls = List(7) { "" },
    isLive = true,
    bidsCount = 1,
    timeLeft = "6ي 21س",
    priceText = "100",
)

private val sampleDirectSale = AuctionItemUi(
    id = "2",
    title = "كيا سونيت 2025",
    location = "إدلب",
    isDirectSale = true,
    priceText = "23000",
)

private val sampleFeatured = AuctionItemUi(
    id = "3",
    title = "مزرعة للبيع",
    categoryName = "العقارات",
    imageUrls = List(12) { "" },
    isFeatured = true,
    isDirectSale = true,
    priceText = "80000",
)

private val sampleNoBids = AuctionItemUi(id = "4", title = "ولاعة قديمة", isLive = true)

private val sampleState = AuctionsHomeUiState(
    brandName = "اسم تطبيقك",
    brandTagline = "YOUR APP",
    banners = listOf(
        HeroBannerUi("b1", "عقارات", "فرص مميزة"),
        HeroBannerUi("b2", "سيارات فاخرة", "مزاد إلكتروني"),
    ),
    categories = listOf(
        AuctionCategoryUi("c1", "المركبات", "سيارات، دراجات، شاحنات", count = 9, span = CategorySpan.Wide),
        AuctionCategoryUi("c2", "المعدات الثقيلة", count = 6),
        AuctionCategoryUi("c3", "الأعمال التجارية", count = 3),
        AuctionCategoryUi("c4", "الزراعة والمواشي", count = 1),
        AuctionCategoryUi("c5", "الإلكترونيات", count = 16),
        AuctionCategoryUi("c6", "العقارات", "شقق، منازل، أراضي", count = 33, span = CategorySpan.Wide),
        AuctionCategoryUi("c7", "المجوهرات والذهب", count = 7),
    ),
    sections = listOf(
        AuctionSectionUi("s1", "المركبات", 9, items = listOf(sampleAuction, sampleNoBids)),
        AuctionSectionUi("s2", "سيارات – بيع مباشر", 11, SectionStyle.DirectSale, listOf(sampleDirectSale, sampleDirectSale.copy(id = "5"))),
    ),
    allAuctions = listOf(sampleAuction, sampleFeatured),
)

@Preview(showBackground = true, widthDp = 360, heightDp = 780)
@Composable
private fun HomePreview() {
    AuctionTheme(layoutDirection = LayoutDirection.Rtl) {
        AuctionsHomeScreen(state = sampleState, actions = AuctionsHomeActions())
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 780)
@Composable
private fun DetailPreview() {
    AuctionTheme(layoutDirection = LayoutDirection.Rtl) {
        AuctionDetailScreen(
            detail = AuctionDetailUi(
                id = "3",
                referenceLabel = "ID #123456",
                title = "مزرعة للبيع",
                location = "المشرفة، حمص",
                imageUrls = List(12) { "" },
                isEnded = true,
                isFeatured = true,
                specChips = listOf(
                    SpecChipUi("العقارات", SpecTone.Green, Icons.Filled.Category),
                    SpecChipUi("1000 م²", SpecTone.Blue, Icons.Filled.GridView),
                    SpecChipUi("طابو 2400", SpecTone.Gold, Icons.Filled.Public),
                ),
                description = "وصف الإعلان يظهر هنا داخل إطار بحواف مدورة.",
                priceLabel = "بيع مباشر",
                priceText = "80000",
                action = DetailAction.Contact,
            ),
            actions = AuctionDetailActions(),
        )
    }
}

package com.yourapp.auctions.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Public
import com.yourapp.auctions.ui.model.AuctionCategoryUi
import com.yourapp.auctions.ui.model.AuctionDetailUi
import com.yourapp.auctions.ui.model.AuctionItemUi
import com.yourapp.auctions.ui.model.AuctionSectionUi
import com.yourapp.auctions.ui.model.CategorySpan
import com.yourapp.auctions.ui.model.DetailAction
import com.yourapp.auctions.ui.model.HeroBannerUi
import com.yourapp.auctions.ui.model.SectionStyle
import com.yourapp.auctions.ui.model.SpecChipUi
import com.yourapp.auctions.ui.model.SpecTone

object AuctionsData {

    val sampleBanners = listOf(
        HeroBannerUi(
            id = "b1",
            title = "مزادات السيارات الفاخرة",
            subtitle = "فرص حصرية بأسعار افتتاحية مميزة",
            imageUrl = "https://images.unsplash.com/photo-1617814076367-b759c7d7e738?w=800&auto=format&fit=crop&q=80",
        ),
        HeroBannerUi(
            id = "b2",
            title = "عقارات وأراضٍ استثمارية",
            subtitle = "طابو نظامي ومواقع استراتيجية",
            imageUrl = "https://images.unsplash.com/photo-1600585154340-be6161a56a0c?w=800&auto=format&fit=crop&q=80",
        ),
        HeroBannerUi(
            id = "b3",
            title = "ساعات ومجوهرات نادرة",
            subtitle = "مزادات معتمدة مع شهادات أصالة",
            imageUrl = "https://images.unsplash.com/photo-1522335789203-aabd1fc54bc9?w=800&auto=format&fit=crop&q=80",
        ),
    )

    val sampleCategories = listOf(
        AuctionCategoryUi(
            id = "c1",
            title = "المركبات",
            subtitle = "سيارات، دراجات، شاحنات",
            imageUrl = "https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=600&auto=format&fit=crop&q=80",
            count = 14,
            span = CategorySpan.Wide,
        ),
        AuctionCategoryUi(
            id = "c2",
            title = "المعدات الثقيلة",
            imageUrl = "https://images.unsplash.com/photo-1579273166629-91f7a0c7c729?w=600&auto=format&fit=crop&q=80",
            count = 6,
            span = CategorySpan.Small,
        ),
        AuctionCategoryUi(
            id = "c3",
            title = "الأعمال التجارية",
            imageUrl = "https://images.unsplash.com/photo-1486406146926-c627a92ad1ab?w=600&auto=format&fit=crop&q=80",
            count = 4,
            span = CategorySpan.Small,
        ),
        AuctionCategoryUi(
            id = "c4",
            title = "الزراعة والمواشي",
            imageUrl = "https://images.unsplash.com/photo-1500595046743-cd271d694d30?w=600&auto=format&fit=crop&q=80",
            count = 3,
            span = CategorySpan.Small,
        ),
        AuctionCategoryUi(
            id = "c5",
            title = "الإلكترونيات",
            imageUrl = "https://images.unsplash.com/photo-1550009158-9ebf69173e03?w=600&auto=format&fit=crop&q=80",
            count = 18,
            span = CategorySpan.Small,
        ),
        AuctionCategoryUi(
            id = "c6",
            title = "العقارات",
            subtitle = "فلل، مزارع، أراضٍ تجارية",
            imageUrl = "https://images.unsplash.com/photo-1512917774080-9991f1c4c750?w=600&auto=format&fit=crop&q=80",
            count = 38,
            span = CategorySpan.Wide,
        ),
        AuctionCategoryUi(
            id = "c7",
            title = "المجوهرات والذهب",
            imageUrl = "https://images.unsplash.com/photo-1515562141207-7a88fb7ce338?w=600&auto=format&fit=crop&q=80",
            count = 9,
            span = CategorySpan.Small,
        ),
    )

    val itemCruze = AuctionItemUi(
        id = "1",
        title = "شيفروليه كروز 2009 فابريكا",
        subtitle = "162 ألف كم · لاذقية · خالية من الحوادث",
        categoryName = "المركبات",
        imageUrls = listOf(
            "https://images.unsplash.com/photo-1552519507-da3b142c6e3d?w=800&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1617814076367-b759c7d7e738?w=800&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=800&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1492144534655-ae79c964c9d7?w=800&auto=format&fit=crop&q=80",
        ),
        isLive = true,
        bidsCount = 7,
        timeLeft = "6ي 21س",
        priceText = "$4,850",
        location = "اللاذقية، الكورنيش الجنوبي",
        isFavorite = false,
    )

    val itemMercedes = AuctionItemUi(
        id = "2",
        title = "مرسيدس بنز E300 موديل 2022",
        subtitle = "35 ألف كم · دمشق · صيانة وكالة",
        categoryName = "المركبات",
        imageUrls = listOf(
            "https://images.unsplash.com/photo-1618843479313-40f8afb4b4d8?w=800&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1617814076367-b759c7d7e738?w=800&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=800&auto=format&fit=crop&q=80",
        ),
        isLive = true,
        isFeatured = true,
        bidsCount = 19,
        timeLeft = "2ي 04س",
        priceText = "$42,000",
        location = "دمشق، أبورمانة",
        isFavorite = true,
    )

    val itemSonet = AuctionItemUi(
        id = "3",
        title = "كيا سونيت 2025 زيرو",
        subtitle = "جديدة بالكامل · ضمان الوكيل",
        categoryName = "المركبات",
        imageUrls = listOf(
            "https://images.unsplash.com/photo-1549399542-7e3f8b79c341?w=800&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1552519507-da3b142c6e3d?w=800&auto=format&fit=crop&q=80",
        ),
        isDirectSale = true,
        priceText = "$23,000",
        location = "إدلب",
    )

    val itemFarm = AuctionItemUi(
        id = "4",
        title = "مزرعة استثمارية نموذجية مع فيلا",
        subtitle = "مساحة 1000 م² · مسبح وبئر ماء",
        categoryName = "العقارات",
        imageUrls = listOf(
            "https://images.unsplash.com/photo-1580587771525-78b9dba3b914?w=800&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1512917774080-9991f1c4c750?w=800&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1600585154340-be6161a56a0c?w=800&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1600596542815-ffad4c1539a9?w=800&auto=format&fit=crop&q=80",
        ),
        isFeatured = true,
        isDirectSale = true,
        priceText = "$80,000",
        location = "المشرفة، حمص",
        isFavorite = true,
    )

    val itemRolex = AuctionItemUi(
        id = "5",
        title = "ساعة رولكس صبمارينر أصلية",
        subtitle = "ميناء أسود وإطار سيراميك · مع العلبة والشهادة",
        categoryName = "المجوهرات والذهب",
        imageUrls = listOf(
            "https://images.unsplash.com/photo-1522335789203-aabd1fc54bc9?w=800&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1515562141207-7a88fb7ce338?w=800&auto=format&fit=crop&q=80",
        ),
        isLive = true,
        isFeatured = true,
        bidsCount = 14,
        timeLeft = "1ي 11س",
        priceText = "$11,200",
        location = "حلب، الشهباء",
    )

    val itemBulldozer = AuctionItemUi(
        id = "6",
        title = "حفار كاتربيلر CAT 320D",
        subtitle = "ساعات عمل 4200 · حالة فنية ممتازة",
        categoryName = "المعدات الثقيلة",
        imageUrls = listOf(
            "https://images.unsplash.com/photo-1579273166629-91f7a0c7c729?w=800&auto=format&fit=crop&q=80",
        ),
        isDirectSale = true,
        priceText = "$65,000",
        location = "طرطوس، المنطقة الصناعية",
    )

    val initialAuctions = listOf(itemCruze, itemMercedes, itemFarm, itemRolex, itemSonet, itemBulldozer)

    val initialSections = listOf(
        AuctionSectionUi(
            id = "s1",
            title = "مزادات حية جارية",
            count = 3,
            style = SectionStyle.Auction,
            items = listOf(itemCruze, itemMercedes, itemRolex),
        ),
        AuctionSectionUi(
            id = "s2",
            title = "عروض البيع المباشر",
            count = 3,
            style = SectionStyle.DirectSale,
            items = listOf(itemSonet, itemFarm, itemBulldozer),
        ),
    )

    fun createDetailFor(item: AuctionItemUi): AuctionDetailUi {
        val specChips = when (item.categoryName) {
            "العقارات" -> listOf(
                SpecChipUi("العقارات", SpecTone.Green, Icons.Filled.Category),
                SpecChipUi("1000 م²", SpecTone.Blue, Icons.Filled.GridView),
                SpecChipUi("طابو 2400 سهم", SpecTone.Gold, Icons.Filled.Public),
            )
            "المركبات" -> listOf(
                SpecChipUi("المركبات", SpecTone.Green, Icons.Filled.Category),
                SpecChipUi(item.subtitle?.substringBefore("·")?.trim() ?: "أوتوماتيك", SpecTone.Blue),
                SpecChipUi("فحص فني شامل", SpecTone.Gold),
            )
            "المجوهرات والذهب" -> listOf(
                SpecChipUi("مجوهرات وساعات", SpecTone.Green, Icons.Filled.Category),
                SpecChipUi("ذهب أبيض وسيراميك", SpecTone.Blue),
                SpecChipUi("شهادة أصالة دولية", SpecTone.Gold),
            )
            else -> listOf(
                SpecChipUi(item.categoryName ?: "عام", SpecTone.Green, Icons.Filled.Category),
                SpecChipUi("حالة ممتازة", SpecTone.Blue),
                SpecChipUi("فحص دوري", SpecTone.Gold),
            )
        }

        return AuctionDetailUi(
            id = item.id,
            referenceLabel = "ID #${item.id.padStart(6, '0')}",
            title = item.title,
            location = item.location ?: "سوريا - متاح المعاينة",
            imageUrls = item.imageUrls.ifEmpty {
                listOf(
                    "https://images.unsplash.com/photo-1552519507-da3b142c6e3d?w=800&auto=format&fit=crop&q=80",
                    "https://images.unsplash.com/photo-1617814076367-b759c7d7e738?w=800&auto=format&fit=crop&q=80",
                )
            },
            isEnded = item.isEnded,
            isFeatured = item.isFeatured,
            specChips = specChips,
            description = item.subtitle + ".\n\nالمعروض متاح للمعاينة المباشرة مع إمكانية الفحص الكامل قبل إتمام البيع. جميع الأوراق الثبوتية والملكيات متوفرة وتخضع لضمانات منصة مزاد.",
            priceLabel = if (item.isDirectSale) "سعر البيع المباشر" else "المزايدة الحالية",
            priceText = item.priceText ?: "$0",
            action = if (item.isDirectSale) DetailAction.Contact else DetailAction.Bid,
        )
    }
}

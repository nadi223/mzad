package com.yourapp.auctions.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourapp.auctions.ui.components.ActionButton
import com.yourapp.auctions.ui.components.AuctionImage
import com.yourapp.auctions.ui.components.ContactButton
import com.yourapp.auctions.ui.components.SpecChip
import com.yourapp.auctions.ui.components.StatusTag
import com.yourapp.auctions.ui.model.AuctionDetailUi
import com.yourapp.auctions.ui.model.DetailAction
import com.yourapp.auctions.ui.theme.AuctionTheme

data class AuctionDetailActions(
    val onBack: () -> Unit = {},
    val onToggleVisibility: () -> Unit = {},
    val onShare: () -> Unit = {},
    val onMore: () -> Unit = {},
    val onPrimaryAction: () -> Unit = {},
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AuctionDetailScreen(
    detail: AuctionDetailUi,
    actions: AuctionDetailActions,
    modifier: Modifier = Modifier,
) {
    val colors = AuctionTheme.colors
    val strings = AuctionTheme.strings

    Scaffold(
        modifier = modifier,
        containerColor = colors.background,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = { DetailActionBar(detail, actions.onPrimaryAction) },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = padding.calculateBottomPadding())
                .verticalScroll(rememberScrollState()),
        ) {
            DetailGallery(detail.imageUrls, detail.referenceLabel, actions)

            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                if (detail.location != null) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Icon(Icons.Filled.LocationOn, contentDescription = null, tint = colors.priceGreen, modifier = Modifier.size(20.dp))
                        Text(detail.location, color = colors.priceGreen, style = AuctionTheme.type.body.copy(fontSize = 16.sp))
                    }
                }

                if (detail.isEnded || detail.isFeatured) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        if (detail.isEnded) StatusTag(strings.ended, Color(0xFF7A7A7A), Color.White)
                        if (detail.isFeatured) StatusTag(strings.featured, colors.gold, colors.priceGreen, icon = Icons.Filled.Star)
                    }
                }

                Text(
                    text = detail.title,
                    color = colors.textPrimary,
                    style = AuctionTheme.type.listTitle.copy(fontSize = 28.sp, lineHeight = 38.sp),
                )

                if (detail.specChips.isNotEmpty()) {
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        detail.specChips.forEach { SpecChip(it) }
                    }
                }

                if (detail.description != null) {
                    val shape = RoundedCornerShape(18.dp)
                    Text(
                        text = detail.description,
                        color = colors.textPrimary,
                        style = AuctionTheme.type.body,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colors.card, shape)
                            .border(1.5.dp, colors.textMuted.copy(alpha = 0.5f), shape)
                            .padding(18.dp),
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------- gallery

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DetailGallery(
    imageUrls: List<String>,
    referenceLabel: String,
    actions: AuctionDetailActions,
) {
    val pageCount = maxOf(1, imageUrls.size)
    val pagerState = rememberPagerState(pageCount = { pageCount })

    Box(Modifier.fillMaxWidth().height(350.dp)) {
        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
            AuctionImage(imageUrls.getOrNull(page), Modifier.fillMaxSize())
        }
        // top scrim keeps the action icons readable on any photo
        Box(
            Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(Brush.verticalGradient(listOf(Color(0x99000000), Color.Transparent))),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            GalleryIcon(Icons.AutoMirrored.Filled.ArrowBack, actions.onBack)
            Text(
                text = referenceLabel,
                color = Color.White,
                style = AuctionTheme.type.chip.copy(fontSize = 16.sp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f).padding(horizontal = 8.dp),
            )
            GalleryIcon(Icons.Filled.VisibilityOff, actions.onToggleVisibility)
            GalleryIcon(Icons.Filled.Share, actions.onShare)
            GalleryIcon(Icons.Filled.MoreVert, actions.onMore)
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 18.dp)
                .background(Color(0x99000000), CircleShape)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Icon(Icons.Filled.PhotoLibrary, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
            Text("${pagerState.currentPage + 1} / $pageCount", color = Color.White, style = AuctionTheme.type.chip.copy(fontSize = 16.sp))
        }
    }
}

@Composable
private fun GalleryIcon(icon: ImageVector, onClick: () -> Unit) {
    Box(
        Modifier.size(44.dp).clip(CircleShape).clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(26.dp))
    }
}

// ---------------------------------------------------------------- bottom action bar

@Composable
private fun DetailActionBar(detail: AuctionDetailUi, onAction: () -> Unit) {
    val colors = AuctionTheme.colors
    val strings = AuctionTheme.strings
    Surface(color = colors.card, shadowElevation = 16.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(Modifier.weight(1f)) {
                Text(detail.priceLabel, color = colors.textMuted, style = AuctionTheme.type.body.copy(fontSize = 14.sp))
                Text(detail.priceText, color = colors.priceGreen, style = AuctionTheme.type.price)
            }
            when (detail.action) {
                DetailAction.Contact -> ContactButton(
                    text = strings.contactSeller,
                    onClick = onAction,
                    modifier = Modifier.width(190.dp),
                )
                DetailAction.Bid -> ActionButton(
                    text = strings.bidNow,
                    onClick = onAction,
                    container = colors.gold,
                    content = colors.priceGreen,
                    modifier = Modifier.width(190.dp),
                )
            }
        }
    }
}

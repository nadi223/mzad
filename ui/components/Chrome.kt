package com.yourapp.auctions.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardDoubleArrowUp
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourapp.auctions.ui.theme.AuctionTheme

// ================================================================ header

@Composable
fun DefaultAuctionLogo() {
    Icon(
        imageVector = Icons.Filled.KeyboardDoubleArrowUp,
        contentDescription = null,
        tint = Color.White,
        modifier = Modifier.size(32.dp),
    )
}

/**
 * Dark green header: logo + brand on the start side, language / contact / notifications on the end side,
 * optional translucent search field underneath.
 *
 * Pass YOUR OWN brand name and logo – do not reuse another company's identity.
 */
@Composable
fun AuctionHeader(
    brandName: String,
    brandTagline: String,
    languageLabel: String,
    modifier: Modifier = Modifier,
    logo: @Composable () -> Unit = { DefaultAuctionLogo() },
    contactIcon: ImageVector = Icons.Filled.Call, // swap for your WhatsApp drawable via a painter-based variant if you prefer
    onMenuClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {},
    onContactClick: () -> Unit = {},
    onLanguageClick: () -> Unit = {},
    onSearchClick: (() -> Unit)? = null,
) {
    val colors = AuctionTheme.colors
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(colors.headerDark)
            .statusBarsPadding(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                Modifier.size(40.dp).clip(CircleShape).clickable(onClick = onMenuClick),
                contentAlignment = Alignment.Center,
            ) { logo() }
            Spacer(Modifier.width(10.dp))
            Column {
                Text(
                    text = brandName,
                    color = Color.White,
                    style = AuctionTheme.type.cardTitle.copy(fontSize = 17.sp, fontWeight = FontWeight.Bold),
                )
                Text(
                    text = brandTagline,
                    color = Color.White.copy(alpha = 0.8f),
                    style = AuctionTheme.type.nav.copy(fontSize = 10.sp, letterSpacing = 0.8.sp),
                )
            }
            Spacer(Modifier.weight(1f))
            Text(
                text = languageLabel,
                color = Color.White,
                style = AuctionTheme.type.chip.copy(fontSize = 17.sp),
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .clickable(onClick = onLanguageClick)
                    .padding(horizontal = 10.dp, vertical = 8.dp),
            )
            HeaderIcon(contactIcon, onContactClick)
            HeaderIcon(Icons.Filled.Notifications, onNotificationsClick)
        }
        if (onSearchClick != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .height(52.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White.copy(alpha = 0.12f))
                    .clickable(onClick = onSearchClick)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Icon(Icons.Filled.Search, contentDescription = null, tint = Color.White.copy(alpha = 0.8f))
                Text(
                    text = AuctionTheme.strings.searchHint,
                    color = Color.White.copy(alpha = 0.6f),
                    style = AuctionTheme.type.body,
                )
            }
        }
    }
}

@Composable
private fun HeaderIcon(icon: ImageVector, onClick: () -> Unit) {
    Box(
        Modifier.size(44.dp).clip(CircleShape).clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(26.dp))
    }
}

// ================================================================ bottom bar

enum class AuctionTab { Home, Wallet, MyAuctions, Account }

/**
 * Rounded dark-green bar with a raised gold "add auction" button in the middle.
 * Logical order (start → end): Home, Wallet, [add], My auctions, Account – this renders
 * exactly like the reference when the layout direction is RTL.
 */
@Composable
fun AuctionBottomBar(
    selected: AuctionTab,
    onSelect: (AuctionTab) -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = AuctionTheme.colors
    val strings = AuctionTheme.strings
    val navInset = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    Box(modifier.fillMaxWidth().height(92.dp + navInset)) {
        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(76.dp + navInset),
            shape = AuctionTheme.shapes.barTop,
            color = colors.surfaceGreen,
            shadowElevation = 12.dp,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, end = 10.dp, bottom = navInset),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                NavItem(Icons.Filled.Home, strings.navHome, selected == AuctionTab.Home) { onSelect(AuctionTab.Home) }
                NavItem(Icons.Filled.AccountBalanceWallet, strings.navWallet, selected == AuctionTab.Wallet) { onSelect(AuctionTab.Wallet) }
                Spacer(Modifier.width(84.dp))
                NavItem(Icons.Filled.Gavel, strings.navMyAuctions, selected == AuctionTab.MyAuctions) { onSelect(AuctionTab.MyAuctions) }
                NavItem(Icons.Filled.Person, strings.navAccount, selected == AuctionTab.Account) { onSelect(AuctionTab.Account) }
            }
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 10.dp + navInset),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .shadow(6.dp, CircleShape)
                    .background(colors.gold, CircleShape)
                    .border(4.dp, Color.White, CircleShape)
                    .clip(CircleShape)
                    .clickable(onClick = onAddClick),
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Filled.Add, contentDescription = strings.navAdd, tint = Color.White, modifier = Modifier.size(36.dp))
            }
            Text(
                text = strings.navAdd,
                color = colors.gold,
                style = AuctionTheme.type.nav.copy(fontSize = 13.sp),
                modifier = Modifier.padding(top = 2.dp),
            )
        }
    }
}

@Composable
private fun NavItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val colors = AuctionTheme.colors
    val tint = if (selected) colors.gold else colors.navInactive
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(18.dp))
            .background(if (selected) Color.White.copy(alpha = 0.12f) else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(26.dp))
        Text(label, color = tint, style = AuctionTheme.type.nav)
    }
}

// ================================================================ support FAB

@Composable
fun SupportFab(onClick: () -> Unit, modifier: Modifier = Modifier) {
    val shape = RoundedCornerShape(20.dp)
    Box(
        modifier = modifier
            .size(56.dp)
            .shadow(6.dp, shape)
            .clip(shape)
            .background(AuctionTheme.colors.surfaceGreen)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(Icons.Filled.SupportAgent, contentDescription = null, tint = Color.White, modifier = Modifier.size(30.dp))
    }
}

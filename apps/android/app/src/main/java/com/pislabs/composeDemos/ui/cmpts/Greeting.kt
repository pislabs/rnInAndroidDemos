package com.pislabs.composeDemos.ui.cmpts

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Abc
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ImageAspectRatio
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.pislabs.composeDemos.R
import com.pislabs.composeDemos.ui.react.ReactFrameActivity
import com.pislabs.composeDemos.ui.react.ReactMainActivity
import com.pislabs.composeDemos.ui.react.TurnDemoActivity
import com.pislabs.composeDemos.ui.theme.ComposeDemosTheme
import kotlinx.coroutines.launch

data class Item (
    val name: String,
    val icon: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val drawerItems = listOf(Icons.Default.Close, Icons.Default.Clear, Icons.Default.Call)
    val selectedDrawerItem = remember { mutableStateOf(drawerItems[0]) }

    var selectedItemIndex by remember { mutableIntStateOf(0) }
    val items = listOf(
        Item("Home", R.drawable.img_avatar),
        Item("Activity", R.drawable.img_avatar),
        Item("Fragment", R.drawable.img_avatar),
        Item("Turn", R.drawable.img_avatar),
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))
                drawerItems.forEach { item ->
                    NavigationDrawerItem(
                        icon = { Icon(item, contentDescription = null) },
                        label = { Text(item.name) },
                        selected = item == selectedDrawerItem.value,
                        onClick = {
                            scope.launch { drawerState.close() }
                            selectedDrawerItem.value = item
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }
    ) {
        Scaffold (
            modifier = modifier,
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Home") },
                    navigationIcon = {
                        IconButton(
                            onClick = { scope.launch { drawerState.open() } }
                        ) {
                            Icon(Icons.Filled.Menu, null)
                        }
                    }
                )
            },
            bottomBar = {
                NavigationBar {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = index == selectedItemIndex,
                            onClick = {
                                selectedItemIndex = index
                                when (item.name) {
                                    "Activity" -> {
                                       ReactMainActivity.start(context)
                                    }
                                    "Fragment" -> {
                                        ReactFrameActivity.start(context)
                                    }
                                    "Turn" -> {
                                        TurnDemoActivity.start(context)
                                    }
                                }
                            },
                            icon = { Icon(painterResource(id = item.icon), null) },
                            alwaysShowLabel = false,
                            label = { Text(item.name) }
                        )
                    }
                }
            }
        ) { innerPadding ->
            Box(Modifier.padding(innerPadding).background(Color.LightGray)) {
                GreetingDemos(name)
            }
        }
    }
}

@Composable
fun GreetingDemos(name: String) {
    Box (
        modifier = Modifier.padding(10.dp)
    ) {
        Column {
            GreetingList()
            Spacer(Modifier.height(5.dp))
            GreetingConstraintLayout()
            Spacer(Modifier.height(5.dp))
            GreetingLayouts()
            Spacer(Modifier.height(5.dp))
            GreetingLayoutRow()
            Spacer(Modifier.height(5.dp))
            GreetingDialog()
            Spacer(Modifier.height(5.dp))
            GreetingCheckbox()
            Spacer(Modifier.height(5.dp))
            GreetingButton()
            Spacer(Modifier.height(5.dp))
            GreetingTextField()
            Spacer(Modifier.height(5.dp))
            GreetingText()
            Spacer(Modifier.height(5.dp))
            GreetingStart(name)
        }
    }
}

@Composable
fun GreetingList() {
    val items = listOf(
        Item("Home", R.drawable.img_avatar),
        Item("List", R.drawable.img_avatar),
        Item("Settings", R.drawable.img_avatar),
    )

    val menuExpended = remember { mutableStateOf(false) }

    Box(
        Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color.Green, shape = RoundedCornerShape(8.dp))
            .padding(10.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            LazyColumn (
                modifier = Modifier.fillMaxWidth().background(Color.Gray),
                contentPadding = PaddingValues(10.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items (items) {
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box (Modifier.padding(10.dp)) {
                            Text(it.name)
                        }
                    }
                }
            }
            Box {
                GreetingMenu(items, menuExpended.value) { menuExpended.value = false }
                Button(onClick = { menuExpended.value = true }) {
                    Text(text = if (menuExpended.value) "关闭菜单" else "打开菜单")
                }
            }
        }
    }
}

@Composable
fun GreetingMenu(
    options: List<Item>,
    expended: Boolean,
    onDismissRequest: () -> Unit
) {
    DropdownMenu(
        expended,
        onDismissRequest = onDismissRequest,
    ) {
        Column {
            options.forEach { option ->
                ListItem(headlineContent = { Text(option.name) })
            }
        }
    }
}

@Composable
fun GreetingConstraintLayout() {
    Box(
        Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color.Green, shape = RoundedCornerShape(8.dp))
            .padding(10.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            ConstraintLayout(
                modifier = Modifier
                    .width(300.dp)
                    .height(200.dp)
                    .padding(10.dp)
                    .background(Color.Gray)
            ) {
                val (quotesFirstLineRef, quotesSecondLineRef, quotesThirdLineRef, quotesForthLineRef) = remember { createRefs() }

                Text(text = "First Line", modifier = Modifier.constrainAs(quotesFirstLineRef) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }.background(Color.Red))
                Text(text = "Second Line", modifier = Modifier.constrainAs(quotesSecondLineRef) {
                    top.linkTo(quotesFirstLineRef.bottom, 10.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }.background(Color.Yellow))
                Text(text = "Third Line", modifier = Modifier.constrainAs(quotesThirdLineRef) {
                    top.linkTo(quotesSecondLineRef.bottom, 10.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }.background(Color.Blue))
                Text(text = "Forth Line", modifier = Modifier.constrainAs(quotesForthLineRef) {
                    top.linkTo(quotesThirdLineRef.bottom, 10.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }.background(Color.Green))

                createVerticalChain(quotesThirdLineRef, quotesSecondLineRef, quotesThirdLineRef, quotesForthLineRef, chainStyle = ChainStyle.Packed)
            }
            ConstraintLayout(
                modifier = Modifier
                    .width(300.dp)
                    .height(200.dp)
                    .padding(10.dp)
                    .background(Color.Gray)
            ) {
                val (userPortraitBackgroundRef, userPortraitImgRef, usernameTextRef) = remember { createRefs() }

                val guideline = createGuidelineFromTop(0.4f)
                Box(
                    modifier = Modifier
                        .constrainAs(userPortraitBackgroundRef) {
                            top.linkTo(parent.top)
                            bottom.linkTo(guideline)
                            height = Dimension.fillToConstraints
                            width = Dimension.matchParent
                        }
                        .background(Color(0xFF1E9FFF))
                )
                Image(
                    painter = painterResource(id = R.drawable.img_avatar),
                    contentDescription = stringResource(R.string.app_name),
                    modifier = Modifier
                        .constrainAs(userPortraitImgRef) {
                            top.linkTo(guideline)
                            bottom.linkTo(guideline)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .size(100.dp)
                        .clip(CircleShape)
                        .border(width = 2.dp, color = Color(0XFF5FB878), shape = CircleShape)
                )
                Text(text = "Rayl", modifier = Modifier.constrainAs(usernameTextRef) {
                    top.linkTo(userPortraitImgRef.bottom, 10.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
            }
            ConstraintLayout(
                modifier = Modifier
                    .width(300.dp)
                    .height(100.dp)
                    .padding(10.dp)
                    .background(Color.Gray)
            ) {
                val (usernameTextRef, passwordTextRef, usernameInputRef, passwordInputRef, dividerRef) = remember { createRefs() }
                val barrier = createEndBarrier(usernameTextRef, passwordTextRef)
                Text(
                    text = "用户名",
                    modifier = Modifier.constrainAs(usernameTextRef) {
                        start.linkTo(parent.start, 10.dp)
                        top.linkTo(parent.top, 10.dp)
                    }
                )
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.constrainAs(usernameInputRef) {
                        start.linkTo(barrier, 10.dp)
                        top.linkTo(usernameTextRef.top)
                        bottom.linkTo(usernameTextRef.bottom)
                        width = Dimension.percent(0.6f)
                        height = Dimension.fillToConstraints
                    }
                )
                Text(
                    text = "密码",
                    modifier = Modifier.constrainAs(passwordTextRef) {
                        start.linkTo(parent.start, 10.dp)
                        top.linkTo(usernameTextRef.bottom, 10.dp)
                    }
                )
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.constrainAs(passwordInputRef) {
                        start.linkTo(barrier, 10.dp)
                        top.linkTo(passwordTextRef.top)
                        bottom.linkTo(passwordTextRef.bottom)
                        width = Dimension.value(180.dp)
                        height = Dimension.fillToConstraints
                    }
                )
            }
            ConstraintLayout(
                modifier = Modifier
                    .width(300.dp)
                    .height(100.dp)
                    .padding(10.dp)
                    .background(Color.Gray)
            ) {
                val (portraitImageRef, usernameTextRef, desTextRef) = remember { createRefs() }
                Image(
                    painter = painterResource(id = R.drawable.img_avatar),
                    contentDescription = stringResource(R.string.app_name),
                    modifier = Modifier
                        .constrainAs(portraitImageRef) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            width = Dimension.percent(0.2f)
                        }
                        .background(Color.Green)
                )
                Text(
                    text = "Hello world",
                    fontSize = 16.sp,
                    maxLines = 1,
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .constrainAs(usernameTextRef) {
                            top.linkTo(portraitImageRef.top)
                            start.linkTo(portraitImageRef.end, 10.dp)
                        }
                        .background(Color.White)
                )

                Text(
                    text = "This is my work",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.constrainAs(desTextRef) {
                        top.linkTo(usernameTextRef.bottom)
                        start.linkTo(portraitImageRef.end, 10.dp)
                    }
                )
            }
        }
    }
}

@Composable
fun GreetingLayouts() {
    Box(
        Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color.Green, shape = RoundedCornerShape(8.dp))
            .padding(10.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row {
                Box(
                    Modifier
                        .size(100.dp)
                        .background(Color.Red))
                Spacer(Modifier.width(20.dp))
                Box(
                    Modifier
                        .size(100.dp)
                        .background(Color.Magenta))
                Spacer(Modifier.weight(1f))
                Box(
                    Modifier
                        .size(100.dp)
                        .background(Color.Black))
            }

            Spacer(Modifier.height(5.dp))

            Surface (
                shape = RoundedCornerShape(8.dp),
                shadowElevation = 8.dp,
                modifier = Modifier
                    .width(300.dp)
                    .height(100.dp)
            ) {
                Row (modifier = Modifier.clickable {  }){
                    Image(
                        painter = painterResource(id = R.drawable.img_avatar),
                        contentDescription = stringResource(R.string.app_name),
                        modifier = Modifier.size(100.dp),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(Modifier.padding(horizontal = 12.dp))
                    Column (
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Center
                    ){
                        Text(
                            text = "Liratie",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Spacer(Modifier.padding(vertical = 8.dp))
                        Text(text = "呵呵")
                    }
                }
            }


            Spacer(Modifier.height(5.dp))


            Box {
                Box( modifier = Modifier
                    .size(150.dp)
                    .background(Color.Green) )
                Box(  modifier = Modifier
                    .size(80.dp)
                    .background(Color.Red) )
                Text(text = "World")
            }
        }
    }
}

@Composable
fun GreetingLayoutRow() {
    val layoutTextModifier = Modifier
        .padding(horizontal = 2.dp)
        .background(Color.White, CircleShape)
        .padding(horizontal = 20.dp)

    Box(
        Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color.Green, shape = RoundedCornerShape(8.dp))
            .padding(10.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()) {
                Text("Equal Weight", fontSize = 14.sp, modifier = Modifier
                    .width(120.dp)
                    .padding(5.dp))
                Row (
                    modifier = Modifier
                        .background(Color.Gray, CircleShape)
                        .padding(5.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "A", textAlign = TextAlign.Center, modifier = layoutTextModifier.weight(1f))
                    Text(text = "B", textAlign = TextAlign.Center, modifier = layoutTextModifier.weight(1f))
                    Text(text = "C", textAlign = TextAlign.Center, modifier = layoutTextModifier.weight(1f))
                }
            }
            GreetingLayoutArrangement("Space Between", Arrangement.SpaceBetween)
            GreetingLayoutArrangement("Space Around", Arrangement.SpaceAround)
            GreetingLayoutArrangement("Space Evenly", Arrangement.SpaceEvenly)
            GreetingLayoutArrangement("End", Arrangement.End)
            GreetingLayoutArrangement("Center", Arrangement.Center)
            GreetingLayoutArrangement("Start", Arrangement.Start)

        }
    }
}

@Composable
fun GreetingLayoutArrangement(name: String, hArrangement: Arrangement.Horizontal) {
    val layoutTextModifier = Modifier
        .padding(horizontal = 2.dp)
        .background(Color.White, CircleShape)
        .padding(horizontal = 20.dp)

    Row(modifier = Modifier
        .padding(5.dp)
        .fillMaxWidth()) {
        Text(name, fontSize = 14.sp, modifier = Modifier
            .width(120.dp)
            .padding(5.dp))
        Row (
            horizontalArrangement = hArrangement,
            modifier = Modifier
                .background(Color.Gray, CircleShape)
                .padding(5.dp)
                .fillMaxWidth()
        ) {
            Text(text = "A", textAlign = TextAlign.Center, modifier = layoutTextModifier)
            Text(text = "B", textAlign = TextAlign.Center, modifier = layoutTextModifier)
            Text(text = "C", textAlign = TextAlign.Center, modifier = layoutTextModifier)
        }
    }
}

@Composable
fun GreetingDialog() {
    val openNormalDialog = remember { mutableStateOf(false) }
    val openLocationDialog = remember { mutableStateOf(false) }

    var progress by remember { mutableStateOf(0.1f) }
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )

    Box(
        Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color.Green, shape = RoundedCornerShape(8.dp))
            .padding(10.dp)
    ) {
        Column() {
            CircularProgressIndicator(
                progress = { animatedProgress },
            )
            Spacer(Modifier.requiredHeight(30.dp))
            OutlinedButton(
                onClick = {
                    if (progress < 1f) progress += 0.1f
                }
            ) {
                Text("增加进度")
            }

            Button(
                onClick = {
                    openNormalDialog.value = true
                },
            ) {
                Text("打开一般服务")
            }
            if (openNormalDialog.value) {
                Dialog(
                    onDismissRequest = { openNormalDialog.value = false }
                ) {
                    Box(
                        Modifier
                            .size(200.dp, 50.dp)
                            .background(Color.White))
                }
            }
            Button(
                onClick = {
                    openLocationDialog.value = true
                },
            ) {
                Text("打开定位服务")
            }
            if (openLocationDialog.value) {
                AlertDialog(
                    title = {
                        Text(text = "开启定位服务")
                    },
                    text = {
                        Text("提供精确服务")
                    },
                    onDismissRequest = {
                        openLocationDialog.value = false
                    },
                    confirmButton = {
                        TextButton(
                            onClick = { openLocationDialog.value = false }
                        ) {
                            Text("同意")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { openLocationDialog.value = false }
                        ) {
                            Text("取消")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun GreetingCheckbox() {
    val checkedState = remember { mutableStateOf(true) }

    val (state1, onStateChange1) = remember { mutableStateOf(true) }
    val (state2, onStateChange2) = remember { mutableStateOf(true) }

    val parentState = remember(state1, state2) {
        if (state1 && state2) ToggleableState.On
        else if (!state1 && !state2) ToggleableState.Off
        else ToggleableState.Indeterminate
    }

    val onParentClick = {
        val s = parentState != ToggleableState.On
        onStateChange1(s)
        onStateChange2(s)
    }

    var sliderPosition by remember { mutableFloatStateOf(0f) }

    Box(
        Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color.Green, shape = RoundedCornerShape(8.dp))
            .padding(10.dp)
    ) {
        Column() {
            Checkbox(
                checked = checkedState.value,
                onCheckedChange = { checkedState.value = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0XFF0079D3)
                )
            )
            TriStateCheckbox(
                state = parentState,
                onClick = onParentClick,
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary
                )
            )
            Column(Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)) {
                Checkbox(state1, onStateChange1)
                Checkbox(state2, onStateChange2)
            }
            Switch(
                checked = checkedState.value,
                onCheckedChange = { checkedState.value = it }
            )
            Text(text = "%.1f".format(sliderPosition * 100) + "%")
            Slider(value = sliderPosition, onValueChange = { sliderPosition = it })
        }
    }
}

@Composable
fun GreetingButton() {
    // 支持相应
    val interactionSource = remember { MutableInteractionSource() }
    val pressState = interactionSource.collectIsPressedAsState()
    val borderColor = if (pressState.value) Color.Green else Color.White

    Box(
        Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color.Green, shape = RoundedCornerShape(8.dp))
            .padding(10.dp)
    ) {
        Column() {
            Row {
                Button(
                    onClick = {},
                    border = BorderStroke(2.dp, color = borderColor),
                    interactionSource = interactionSource
                ) {
                    Icon(
                        imageVector = Icons.Filled.Abc,
                        contentDescription = stringResource(R.string.text_field_description),
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("长按")
                }
                Spacer(Modifier.size(5.dp))
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Filled.AcUnit,
                        contentDescription = stringResource(R.string.text_field_description),
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                }
                Spacer(Modifier.size(5.dp))
                FloatingActionButton(
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Filled.ImageAspectRatio,
                        contentDescription = stringResource(R.string.text_field_description),
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                }
                Spacer(Modifier.size(5.dp))
                ExtendedFloatingActionButton(
                    icon = {
                        Icon(
                            Icons.Filled.Favorite,
                            stringResource(R.string.text_field_description)
                        )
                    },
                    text = { Text("I like it") },
                    onClick = {}
                )
            }
        }
    }
}

@Composable
fun GreetingTextField() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var text by remember { mutableStateOf("") }

    Box(
        Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color.Green, shape = RoundedCornerShape(8.dp))
            .padding(10.dp)
    ) {
        Column() {
            TextField(
                value = username,
                onValueChange = {
                    username = it
                },
                label = { Text("用户名") },
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.AccountBox, contentDescription = stringResource(R.string.app_name))
                },
                modifier = Modifier.fillMaxWidth(),
            )
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                },
                label = { Text("密码") },
                trailingIcon = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(painter = painterResource(id = R.drawable.img_avatar), contentDescription = stringResource(R.string.text_field_description))
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0XFFD3D3D3))
                    .padding(vertical = 5.dp),
                contentAlignment = Alignment.Center
            ) {
                BasicTextField(
                    value = text,
                    onValueChange = {
                        text = it
                    },
                    decorationBox = { innerTextField ->
                        Row (
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 2.dp, horizontal = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = stringResource(R.string.text_field_description)
                            )
                            Box(
                                modifier = Modifier.padding(horizontal = 10.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                if (text.isEmpty()) {
                                    Text(
                                        text = "请输入",
                                        style = TextStyle(
                                            color = Color(0, 0, 0, 120)
                                        )
                                    )
                                }
                                innerTextField()
                            }
                        }
                    },
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .background(Color.White, CircleShape)
                        .height(30.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun GreetingText() {
    val annotatedText = buildAnnotatedString {
        withStyle(style = SpanStyle(fontSize = 24.sp)) {
            append("现在你知道了")
        }
        withStyle(style = SpanStyle(fontWeight = FontWeight.W900, fontSize = 24.sp)) {
            append("光")
        }
        append("\r")
        withStyle(style = ParagraphStyle(lineHeight = 30.sp)) {
            append("是电磁波")
        }
        append("\r")
        append("它没有静质量")
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.W900,
                textDecoration = TextDecoration.Underline,
                color = Color(0xFF59A869)
            )
        ) {
            append("但它有动质量")
        }

        // 为pushStringAnnoatation与pop之间添加标签
        pushStringAnnotation(tag = "URL", annotation = "https://jetpackcompose.cn/docs/elements/text")
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.W900,
                color = Color(0xFF59A869)
            )
        ){
            append("AnnotatedString")
        }
        pop()
    }

    Box(
        Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color.Green, shape = RoundedCornerShape(8.dp))
            .padding(10.dp)
    ) {
        Column {
            Text(
                text = stringResource(R.string.app_name),
                style = TextStyle(
                    color = Color.Red,
                    background = Color.Cyan,
                    lineHeight = 30.sp,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    letterSpacing = 2.sp,
                    textDecoration = TextDecoration.Underline,
                    fontFamily = FontFamily.Serif
                )
            )
            SelectionContainer {
                Text(
                    text = stringResource(R.string.app_name) + "一些纯粹多余的话，bulabulabulabulabulabula",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.headlineMedium.copy(fontStyle = FontStyle.Italic)
                )
            }
            Text( text = annotatedText )
            Text(buildAnnotatedString {
                append("View my ")
                withLink(LinkAnnotation.Url(url = "https://joebirch.co")) {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.W900,
                            textDecoration = TextDecoration.Underline,
                            color = Color(0xFF59A869)
                        )
                    ) {
                        append("web site")
                    }
                }
            })
        }
    }
}

@Composable
fun GreetingStart(name: String) {
    Box(
        Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color.Green, shape = RoundedCornerShape(8.dp))
            .padding(10.dp)
    ) {
        Column {
            Row {
                Image(
                    painterResource(id = R.drawable.img_avatar),
                    contentDescription = "测试Greeting",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                )
                Spacer(Modifier.width(10.dp))
                Image(
                    painterResource(id = R.drawable.img_avatar),
                    contentDescription = stringResource(id = R.string.app_name),
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .offset(x = 20.dp, y = 20.dp)
                )
                Text(
                    text = "Hello $name!",
                    color = Color.Magenta,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Color.Green)
                )
            }
            Row {
                Box(
                    Modifier
                        .size(50.dp)
                        .background(
                            shape = RoundedCornerShape(8.dp),
                            color = Color.Red
                        )
                ) {
                    Text(text = "纯色", modifier = Modifier.align(Alignment.Center))
                }
                Spacer(Modifier.width(10.dp))
                Box(
                    Modifier
                        .size(50.dp)
                        .background(
                            shape = RoundedCornerShape(8.dp),
                            brush = Brush.verticalGradient(
                                colors = listOf(Color.Red, Color.Yellow, Color.White)
                            )
                        )
                ) {
                    Text(
                        text = "渐变色",
                        fontSize = 10.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeDemosTheme {
        Greeting("Android", modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray))
    }
}

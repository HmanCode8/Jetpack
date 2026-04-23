package com.example.jetcompose.components.common

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetcompose.services.MockData
import com.example.jetcompose.untils.LayerItem
import com.example.jetcompose.untils.getDrawable


// ========================
// 完全封装版 Element UI 风格树形组件
// ========================
@Composable
fun Tree(
    layerList: List<LayerItem>,
    onSelectedChange: (selectedList: List<LayerItem>) -> Unit // 外部只接收结果
) {
    // 内部自己维护整棵树状态（外部完全不用管）
    val treeState = remember { mutableStateListOf<LayerItem>() }

    // 初始化 深度拷贝原始数据，避免外部数据修改影响内部状态
    LaunchedEffect(layerList) {
        treeState.clear()
        // 深拷贝整棵树，完全切断和外部原始数据的对象引用
        treeState.addAll(deepCopyTree(layerList))
    }

    // 每次状态变化 → 自动计算选中结果 → 实时抛给外部
// 监听 treeState 的整体变化，每次重组都计算选中项
    LaunchedEffect(Unit) {
        snapshotFlow { treeState.toList() }
            .collect { newTree ->

                val selected = getAllCheckedNodes(newTree)

                onSelectedChange(selected)
            }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        treeState.forEachIndexed { index, item ->
            TreeItemNode(
                item = item,
                // 🔥 修复1：传入【内部真实节点+索引】，不再传旧对象
                onNodeClick = { targetNode ->
                    // 内部处理勾选 + 完整双向递归联动
                    val newWholeTree = toggleNodeCheck(treeState, targetNode.id)
                    Log.d("newWholeTree","$newWholeTree")
//                    val selected = getAllCheckedNodes(treeState)
//                    onSelectedChange(selected)
//                     整体替换整棵树，触发Compose状态刷新
                    treeState.clear()
                    treeState.addAll(newWholeTree)
                }
            )
        }
    }
}

@Composable
fun TreeItemNode(
    item: LayerItem,
    onNodeClick: (LayerItem) -> Unit,
    depth: Int = 0
) {
    val expanded = remember { mutableStateOf(false) }
    val hasChildren = !item.children.isNullOrEmpty()

    Row(
        modifier = Modifier
            .clickable { if (hasChildren) expanded.value = !expanded.value }
            .padding(start = (depth * 8).dp, top = 8.dp)
            .background(Color(0xFFF4F6F9)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(0.dp, 3.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (hasChildren) {
                    Icon(
                        imageVector = if (expanded.value) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowRight,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                }

                Icon(
                    painter = painterResource(getDrawable(if (hasChildren) "resource" else "tool_tuceng")),
                    contentDescription = null,
                    tint = if (hasChildren) Color(0xFFffcc66) else Color(0xFF0091ea),
                    modifier = Modifier.size(16.dp).padding(start = 3.dp)
                )

                Text(
                    text = item.name ?: "未知",
                    fontSize = 10.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(2.dp, 0.dp)
                )
            }

            Box(
                Modifier
                    .padding(end = 3.dp)
                    .width(12.dp)
                    .height(12.dp)
                    .background(
                        animateColorAsState(
                            targetValue = if (item.isChecked) Color(0xFF0091ea) else Color(0xFFFFFFFF),
                            animationSpec = spring(stiffness = 300f)
                        ).value,
                        RoundedCornerShape(1.dp)
                    )
                    .border(1.dp, Color.LightGray, RoundedCornerShape(1.dp))
                    .clip(RoundedCornerShape(1.dp))
                    .clickable {
                        onNodeClick(item)
                        Log.d("点击直接", "$item")
                    }
            ) {
                // 🔥 加上完整包名，强制使用全局通用版动画，彻底避开RowScope冲突
                androidx.compose.animation.AnimatedVisibility(
                    visible = item.isChecked,
                    enter = scaleIn(animationSpec = spring(stiffness = 300f)),
                    exit = scaleOut(animationSpec = spring(stiffness = 300f)),
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(10.dp).align(Alignment.Center)
                    )
                }
            }

//            Switch(
//                checked = item.isChecked,
//                onCheckedChange = {
//                    onNodeClick(item)
//                }
//            )
        }
    }

    // 子节点递归渲染，完整透传回调
    if (expanded.value && hasChildren) {
        item.children!!.forEach { child ->
            TreeItemNode(
                item = child,
                onNodeClick = onNodeClick,
                depth = depth + 1
            )
        }
    }
}

// ==============================
// 内部封装：树形勾选递归逻辑（完全黑盒，全部BUG修复）
// ==============================

// 切换节点勾选（父 ↔ 子 完整双向联动）
fun toggleNodeCheck(tree: List<LayerItem>, targetId: Int?): List<LayerItem> {
    // 先查找目标节点，拿到它最新的勾选状态
    val targetNode = findNodeById(tree, targetId) ?: return tree
    val newChecked = !targetNode.isChecked

    // 整棵树全量递归更新
    return updateNodeRecursive(tree, targetId, newChecked)
}

// 【核心修复】递归更新整棵树：向下全选子节点 + 向上联动父节点
fun updateNodeRecursive(
    list: List<LayerItem>,
    targetId: Int?,
    checked: Boolean
): List<LayerItem> {
    return list.map { item ->
        // 1. 命中当前点击的目标节点
        if (item.id == targetId) {
            item.copy(
                isChecked = checked,
                // 向下递归：所有子、孙子、无限层级全部同步状态
                children = updateAllChildrenRecursive(item.children, checked)
            )
        }
        // 2. 子树内包含目标节点，更新完子节点后，自动校验父节点状态
        else if (containsTargetNode(item, targetId)) {
            val newChildren = updateNodeRecursive(item.children!!, targetId, checked)
            // 父节点规则：所有子节点全部选中，父节点自动选中；反之自动取消
            val allChildChecked = isAllChildrenChecked(newChildren)
            item.copy(
                isChecked = allChildChecked,
                children = newChildren
            )
        }
        // 3. 无关节点，原封不动返回
        else {
            item
        }
    }
}

// 向下递归：无限层级所有子节点全部同步勾选状态
fun updateAllChildrenRecursive(children: List<LayerItem>?, checked: Boolean): List<LayerItem>? {
    return children?.map { child ->
        child.copy(
            isChecked = checked,
            children = updateAllChildrenRecursive(child.children, checked)
        )
    }
}

// 【修复】安全递归查找：判断当前节点的整棵子树里，是否包含目标ID节点（兼容所有层级+空安全）
fun containsTargetNode(parent: LayerItem, targetId: Int?): Boolean {
    if (parent.id == targetId) return true
    return parent.children?.any { containsTargetNode(it, targetId) } == true
}

// 【修复】安全递归根据ID查找任意节点（全层级）
fun findNodeById(tree: List<LayerItem>, targetId: Int?): LayerItem? {
    fun traverse(node: LayerItem): LayerItem? {
        if (node.id == targetId) return node
        return node.children?.firstNotNullOfOrNull { traverse(it) }
    }
    return tree.firstNotNullOfOrNull { traverse(it) }
}

// 【修复】判断一个节点下所有后代子节点，是否全部为选中状态
fun isAllChildrenChecked(children: List<LayerItem>?): Boolean {
    children ?: return false
    return children.all { child ->
        // 叶子节点：直接看自身选中状态
        if (child.children.isNullOrEmpty()) child.isChecked
        // 父节点：递归判断它所有子节点全部选中
        else isAllChildrenChecked(child.children)
    }
}

// 【修复】整棵树深拷贝，解决对象引用错乱问题
fun deepCopyTree(source: List<LayerItem>): List<LayerItem> {
    return source.map { item ->
        item.copy(
            children = deepCopyTree(item.children ?: emptyList())
        )
    }
}

// 遍历获取所有已选中节点（扁平数组，完全对齐Element UI返回格式）
fun getAllCheckedNodes(tree: List<LayerItem>): List<LayerItem> {
    val resultList = mutableListOf<LayerItem>()
    // 深度优先全树遍历
    fun traverse(node: LayerItem) {
        if (node.isChecked) {
            resultList.add(node)
        }
        node.children?.forEach { traverse(it) }
    }
    tree.forEach { traverse(it) }
    return resultList
}

@Preview
@Composable
fun OnPreview(){
    Tree(
        layerList = MockData.layerData,
        onSelectedChange = { selectedNodes ->
            // 🔥 这里就是你要的：所有选中的节点（父+子+孙）
            Log.d("最终选中", selectedNodes.toString())
        }
    )
}
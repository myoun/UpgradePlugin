package kr.myoung2.upgrade.listeners

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.*
import java.util.Random
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class EnchantGUIListener : Listener {

    private val isName:String = "${ChatColor.GREEN}강화석"

    private val displayName:String  = "${ChatColor.RED}강화 ${ChatColor.GREEN}메뉴"

    val PrivateGUI: MutableMap<Player,Inventory> = mutableMapOf()

    @EventHandler
    fun onEnchantTableOpen(e: InventoryOpenEvent){
        val p:Player = e.player as Player
        val inv:Inventory = e.inventory
        val localGUI = Bukkit.createInventory(null,27,displayName)

        if (inv.type != InventoryType.ENCHANTING){
            return
        }
        e.isCancelled = true;

        for (i in 0..26){
            val emptyItem = ItemStack(Material.WHITE_STAINED_GLASS_PANE)
            val EIM = emptyItem.itemMeta
            EIM.setDisplayName(" ")
            emptyItem.setItemMeta(EIM)
            if (i == 13){
                continue
            }
            else if (i== 22){
                val item = ItemStack(Material.ENCHANTING_TABLE)
                val im = item.itemMeta
                im.setDisplayName("${ChatColor.AQUA}강화 ${ChatColor.RED}도전")
                item.setItemMeta(im)
                localGUI.setItem(22,item)
            }
            else{
                localGUI.setItem(i,emptyItem)

            }
        }
      PrivateGUI.put(p,localGUI)
        p.openInventory(PrivateGUI.get(p)!!)
    }

    @EventHandler
    fun onUpgradeGUI(e:InventoryClickEvent){
        val p:Player = e.whoClicked as Player

        if (e.view.title != displayName){
            return
        }

        if (e.currentItem?.type == Material.WHITE_STAINED_GLASS_PANE || e.currentItem?.type == Material.ENCHANTING_TABLE){
            e.isCancelled = true;
        }
        if (e.view.getItem(13) == ItemStack(Material.AIR)){
            p.closeInventory()
        }

        val item = e.view.getItem(13)
        val meta = item?.itemMeta

        if (e.currentItem?.itemMeta?.displayName == "${ChatColor.AQUA}강화 ${ChatColor.RED}도전") {
            val improveStone:ItemStack = ItemStack(Material.FIREWORK_STAR)
            val impMeta = improveStone.itemMeta
            impMeta.setDisplayName(isName)
            improveStone.setItemMeta(impMeta)

            if (p.inventory.itemInOffHand.type == Material.AIR){
                p.sendMessage("${ChatColor.GOLD}반대쪽 손에 강화석이 없습니다.")
            }
            else{
                if (p.inventory.itemInOffHand.itemMeta.displayName != isName) {
                    p.sendMessage("${ChatColor.GOLD}반대쪽 손에 강화석이 아닌 다른 아이템이 있습니다.")
                }


                else {
                    if (item?.type != Material.AIR) {
                        val enchants = Enchantment.values()
                        enchants.shuffle()
                        val mec = meta?.enchants
                        for (i in mec?.keys!!) {
                            meta.removeEnchant(i)
                        }
                        for (i in 1..rand(1, 7)) {

                            val enchant = enchants[i] as Enchantment
                            meta.addEnchant(enchant, rand(1, 6), true)
                        }
                        val nowStack = p.inventory.itemInOffHand
                        nowStack.amount = nowStack.amount - 1

                        p.playSound(p.location, Sound.BLOCK_ANVIL_USE, 30.0f, 1.0f)
                        p.sendMessage("${ChatColor.GOLD}강화가 끝났습니다.")

                        item.itemMeta = meta

                        e.clickedInventory?.setItem(13, item)
                    }
                }
            }
        }


    }

    @EventHandler
    fun onUpgradeGUIClosed(e:InventoryCloseEvent){
        val p = e.player
        if (e.view.title == displayName){
            val item = e.view.getItem(13)
            if (item != null){
                e.view.setItem(13, ItemStack(Material.AIR))
                p.inventory.addItem(item)
            }
        }
    }

    private fun rand(from: Int, to:Int): Int{
        val random = Random()
        return random.nextInt(to-from)+ from
    }

}
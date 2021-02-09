package kr.myoung2.upgrade

import kr.myoung2.upgrade.commands.UpgradeCommand
import kr.myoung2.upgrade.commands.arguments.UpgradeCommandArgument
import kr.myoung2.upgrade.listeners.EnchantGUIListener
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class Upgrade : JavaPlugin() {




    override fun onEnable() {
        println("ENABLE!")
        server.pluginManager.registerEvents(EnchantGUIListener(),this)
        addRecipe()

        getCommand("upgrade")?.setExecutor(UpgradeCommand())
        getCommand("upgrade")?.tabCompleter = UpgradeCommandArgument()

    }

    override fun onDisable() {
        super.onDisable()
    }

    private fun addRecipe(){
        val improveStone = ItemStack(Material.FIREWORK_STAR)
        val meta = improveStone.itemMeta
        meta.setDisplayName("${ChatColor.GREEN}강화석")
        improveStone.setItemMeta(meta)

        val key = NamespacedKey(this,"improve_stone")

        val improveStoneRecipe = ShapedRecipe(key,improveStone)

        improveStoneRecipe.shape(" b ","dad"," b ")
        improveStoneRecipe.setIngredient('a', Material.GOLD_BLOCK)
        improveStoneRecipe.setIngredient('b',Material.STONE_BRICKS)
        improveStoneRecipe.setIngredient('d', Material.LAPIS_LAZULI)

        Bukkit.addRecipe(improveStoneRecipe)

    }
}
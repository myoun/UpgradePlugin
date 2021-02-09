package kr.myoung2.upgrade.commands

import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class UpgradeCommand : CommandExecutor {

    private val isName:String = "${ChatColor.GREEN}강화석"

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (!sender.isOp){
            return false
        }
        if (command.name.equals("upgrade")){
            if (args[0] == "impstone"){
                if (!(sender is  Player)){
                    return false
                }
                val item = ItemStack(Material.FIREWORK_STAR)
                val meta = item.itemMeta
                meta.setDisplayName(isName)
                item.setItemMeta(meta)

                 sender.inventory.addItem(item)
                return true
            }
        }
        return false
    }
}

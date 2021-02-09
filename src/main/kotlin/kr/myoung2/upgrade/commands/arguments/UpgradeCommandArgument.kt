package kr.myoung2.upgrade.commands.arguments

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class UpgradeCommandArgument : TabCompleter {

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String> {
        if (sender is Player) {
            when (args.size) {
                1 -> {
                    val firstArgument: ArrayList<String> = ArrayList()
                    firstArgument.add("impstone")
                    return firstArgument
                }
                else -> return ArrayList()
            }

        }

        return ArrayList()
    }

}
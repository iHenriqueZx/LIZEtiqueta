package br.com.henriplugins;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import br.com.henriplugins.listeners.TagListener;

public class LIZEtiqueta extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("§aLIZEtiqueta ativado!");
        getServer().getPluginManager().registerEvents(new TagListener(), this);
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§cLIZEtiqueta desativado!");
    }
}

package br.com.henriplugins.listeners;

import br.com.henriplugins.utils.ColorUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TagListener implements Listener {

    // Armazenar temporariamente o jogador que está tentando renomear a etiqueta
    private Player renamingPlayer;

    @EventHandler
    public void onTagClick(PlayerInteractEvent event) {
        // Verifica se é um clique com o botão esquerdo (bater)
        if (!event.getAction().toString().contains("LEFT_CLICK")) return;

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        // Verifica se o item é uma etiqueta
        if (item.getType() != Material.NAME_TAG) return;

        // Inicia o processo de renomeação
        startRenamingProcess(player);
    }

    // Inicia o processo de renomeação
    private void startRenamingProcess(Player player) {
        // Armazena o jogador que iniciou o processo
        renamingPlayer = player;

        // Envia uma mensagem para o jogador pedindo para ele digitar o novo nome
        player.sendMessage("§aDigite o novo nome para a etiqueta:");
    }

    // Evento para capturar a resposta do jogador no chat (após o jogador digitar)
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        // Verifica se o jogador está no processo de renomeação
        if (renamingPlayer == null || !event.getPlayer().equals(renamingPlayer)) return;

        // Cancela o evento para que a mensagem não seja enviada no chat
        event.setCancelled(true);

        // Obtém o novo nome digitado pelo jogador
        String newName = event.getMessage();

        // Chama o método para renomear a etiqueta
        renameTag(renamingPlayer, newName);

        // Limpa a variável renamingPlayer
        renamingPlayer = null;
    }

    // Método para renomear a etiqueta
    public void renameTag(Player player, String newName) {
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item == null || item.getType() != Material.NAME_TAG) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        // Usa o método applyColors para aplicar as cores
        String coloredName = ColorUtils.applyColors(newName);
        meta.setDisplayName(coloredName);
        item.setItemMeta(meta);

        player.sendMessage("§aEtiqueta renomeada para: §f" + coloredName);
    }
}

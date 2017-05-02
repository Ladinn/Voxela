package com.voxela.chat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.voxela.chat.formatter.Format;
import com.voxela.chat.location.InProvince;

public class AsyncPlayerChatListener implements Listener {
	
    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent e) {

        Player player = e.getPlayer();
        String msg = e.getMessage();
        String format = Format.format(player, true);
        
        e.setFormat(format + msg);
        
        InProvince.local(player, e);

    }

}

package net.neferett.linaris.pvpbox.events.players;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerManagers {
	
	public PlayerManagers(){
		
	}
	
	public void ActionOnPlayers(Consumer<Player> consumer){
		Bukkit.getOnlinePlayers().forEach(consumer);
	}
	
	public void ActionOnPlayer(PlayerActions a){
		a.Actions();
	}
	
	public PlayerActions getPlayer(Player p){
		 return (new PlayerActions(p) {
			
			@Override
			public void Actions() {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public List<PlayerActions> ConvertAllPlayer(){
		List<PlayerActions> pa = new ArrayList<>();
		ActionOnPlayers(p -> {
			pa.add(new PlayerActions(p) {
				
				@Override
				public void Actions() {
					// TODO Auto-generated method stub
					
				}
			});
		});
		return pa;
	}
	
}

package color;

import org.bukkit.ChatColor;

public class color {
	public static String add(String str) {
		return ChatColor.translateAlternateColorCodes('&', str);
	}
	public static String reset(String str) {
		str=str.replace("§", "&");
		for(int i=0;i<10;i++)
			str=str.replace("&"+i, "");
		str=str.replace("&a", "");
		str=str.replace("&b", "");
		str=str.replace("&c", "");
		str=str.replace("&d", "");
		str=str.replace("&e", "");
		str=str.replace("&r", "");
		str=str.replace("&n", "");
		str=str.replace("&m", "");
		str=str.replace("&l", "");
		str=str.replace("&k", "");
		return str;
	}	
}

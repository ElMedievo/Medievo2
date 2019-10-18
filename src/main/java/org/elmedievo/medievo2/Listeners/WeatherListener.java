package org.elmedievo.medievo2.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.elmedievo.medievo2.Configuration;
import org.elmedievo.medievo2.Medievo2;

public class WeatherListener implements Listener {
    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        Configuration configuration = Medievo2.getConfiguration;
        if (!configuration.isWeather_enabled()) event.setCancelled(true);
    }
}

package org.elmedievo.medievo2;

import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class Configuration {
    private FileConfiguration configFile = Medievo2.getPlugin.getConfig();
    private String chat_format = configFile.getString("chat.format");
    private String broadcast_format = configFile.getString("chat.broadcast_format");
    private String join_format = configFile.getString("chat.join_format");
    private String leave_format = configFile.getString("chat.leave_format");
    private String pm_prefix = configFile.getString("chat.pm.prefix");
    private String pm_to_format = configFile.getString("chat.pm.to_format");
    private String pm_from_format = configFile.getString("chat.pm.from_format");
    private Sound pmSound = Sound.valueOf(configFile.getString("chat.pm.sound.effect"));
    private int pmSoundV = configFile.getInt("chat.pm.sound.v");
    private int pmSoundV1 = configFile.getInt("chat.pm.sound.v1");
    private List<String> welcome_message = configFile.getStringList("chat.welcome_message");
    private List<String> coords_format = configFile.getStringList("chat.coords_format");
    private boolean weather_enabled = configFile.getBoolean("weather");

    Configuration() {
    }

    public FileConfiguration getConfigFile() {
        return configFile;
    }

    public void setConfigFile(FileConfiguration configFile) {
        this.configFile = configFile;
    }

    public String getChat_format() {
        return chat_format;
    }

    public void setChat_format(String chat_format) {
        this.chat_format = chat_format;
    }

    public String getBroadcast_format() {
        return broadcast_format;
    }

    public void setBroadcast_format(String broadcast_format) {
        this.broadcast_format = broadcast_format;
    }

    public String getJoin_format() {
        return join_format;
    }

    public void setJoin_format(String join_format) {
        this.join_format = join_format;
    }

    public String getLeave_format() {
        return leave_format;
    }

    public void setLeave_format(String leave_format) {
        this.leave_format = leave_format;
    }

    public String getPm_prefix() {
        return pm_prefix;
    }

    public void setPm_prefix(String pm_prefix) {
        this.pm_prefix = pm_prefix;
    }

    public String getPm_to_format() {
        return pm_to_format;
    }

    public void setPm_to_format(String pm_to_format) {
        this.pm_to_format = pm_to_format;
    }

    public String getPm_from_format() {
        return pm_from_format;
    }

    public void setPm_from_format(String pm_from_format) {
        this.pm_from_format = pm_from_format;
    }

    public Sound getPmSound() {
        return pmSound;
    }

    public void setPmSound(Sound pmSound) {
        this.pmSound = pmSound;
    }

    public int getPmSoundV() {
        return pmSoundV;
    }

    public void setPmSoundV(int pmSoundV) {
        this.pmSoundV = pmSoundV;
    }

    public int getPmSoundV1() {
        return pmSoundV1;
    }

    public void setPmSoundV1(int pmSoundV1) {
        this.pmSoundV1 = pmSoundV1;
    }

    public List<String> getWelcome_message() {
        return welcome_message;
    }

    public void setWelcome_message(List<String> welcome_message) {
        this.welcome_message = welcome_message;
    }

    public List<String> getCoords_format() {
        return coords_format;
    }

    public void setCoords_format(List<String> coords_format) {
        this.coords_format = coords_format;
    }

    public boolean isWeather_enabled() {
        return weather_enabled;
    }

    public void setWeather_enabled(boolean weather_enabled) {
        this.weather_enabled = weather_enabled;
    }
}

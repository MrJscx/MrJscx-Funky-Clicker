package baka.MrJscx.save;

import java.util.*;

/**
 * 游戏数据容器 - 包含所有需要保存的游戏状态
 */
public class GameData {
    // 基础数据
    public int currentFunky = 0;
    public int funkyPerClick = 1;
    public int autoClickers = 0;

    // 预留未来扩展字段（都有默认值，确保向后兼容）
    public int prestigeLevel = 0;
    public long totalClicks = 0;
    public long playTime = 0;
    public Map<String, Integer> upgrades = new HashMap<>();
    public List<String> achievements = new ArrayList<>();

    // 构造函数
    public GameData() {}

    public GameData(int funky, int perClick, int assistants) {
        this.currentFunky = funky;
        this.funkyPerClick = perClick;
        this.autoClickers = assistants;
    }

    // 方便调试
    @Override
    public String toString() {
        return String.format("GameData{funky=%d, perClick=%d, assistants=%d}",
                currentFunky, funkyPerClick, autoClickers);
    }
}

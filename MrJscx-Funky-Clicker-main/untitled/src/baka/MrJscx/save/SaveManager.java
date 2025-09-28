package baka.MrJscx.save;

import baka.MrJscx.save.handlers.*;
import java.io.*;
import java.util.*;

/**
 * 存档管理器 - 核心存档/读档逻辑
 */
public class SaveManager {
    private static final Map<Integer, SaveHandler> handlers = new HashMap<>();

    static {
        // 注册所有版本处理器
        registerHandler(1, new V1Handler());
        registerHandler(2, new V2Handler());
    }

    /**
     * 注册版本处理器
     */
    private static void registerHandler(int version, SaveHandler handler) {
        handlers.put(version, handler);
    }

    /**
     * 加载存档文件
     */
    public static GameData load(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            // 读取版本号
            int version = Integer.parseInt(reader.readLine());

            // 获取对应的处理器
            SaveHandler handler = handlers.get(version);
            if (handler == null) {
                throw new IOException("不支持的存档版本: " + version);
            }

            // 加载数据
            GameData data = handler.load(reader);

            // 如果需要升级到最新版本
            if (version < SaveVersion.getCurrentVersion()) {
                System.out.println("检测到旧版本存档(" + version + ")，正在升级到版本" + SaveVersion.getCurrentVersion());
                data = upgradeToLatest(data, version);
            }

            return data;
        } catch (NumberFormatException e) {
            throw new IOException("存档文件格式错误", e);
        }
    }

    /**
     * 保存存档文件
     */
    public static void save(File file, GameData data) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            SaveHandler handler = handlers.get(SaveVersion.getCurrentVersion());
            handler.save(writer, data);
        }
    }

    /**
     * 版本升级链：从指定版本逐步升级到最新版本
     */
    private static GameData upgradeToLatest(GameData data, int fromVersion) {
        GameData currentData = data;

        for (int version = fromVersion + 1; version <= SaveVersion.getCurrentVersion(); version++) {
            SaveHandler handler = handlers.get(version);
            if (handler != null) {
                System.out.println("正在从版本 " + (version-1) + " 升级到版本 " + version);
                currentData = handler.upgrade(currentData);
            }
        }

        return currentData;
    }

    /**
     * 获取支持的版本列表（用于调试）
     */
    public static List<Integer> getSupportedVersions() {
        return new ArrayList<>(handlers.keySet());
    }
}

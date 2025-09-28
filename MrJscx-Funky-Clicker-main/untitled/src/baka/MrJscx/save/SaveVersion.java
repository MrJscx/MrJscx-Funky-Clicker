package baka.MrJscx.save;

/**
 * 存档版本枚举 - 管理所有支持的版本
 */
public enum SaveVersion {
    V1(1, "初始版本 - 只有基础点击"),
    V2(2, "添加助手系统"),
    CURRENT(2); // 当前最新版本

    public final int version;
    public final String description;

    SaveVersion(int version) {
        this(version, "");
    }

    SaveVersion(int version, String description) {
        this.version = version;
        this.description = description;
    }

    /**
     * 根据版本号获取枚举
     */
    public static SaveVersion fromInt(int version) {
        for (SaveVersion v : values()) {
            if (v.version == version) return v;
        }
        throw new IllegalArgumentException("未知版本: " + version);
    }

    /**
     * 获取当前版本号
     */
    public static int getCurrentVersion() {
        return CURRENT.version;
    }
}

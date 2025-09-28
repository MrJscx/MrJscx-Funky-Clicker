package baka.MrJscx.save.handlers;

import baka.MrJscx.save.GameData;
import java.io.*;

/**
 * 存档处理器接口 - 每个版本实现自己的读写逻辑
 */
public interface SaveHandler {
    /**
     * 从文件读取数据
     */
    GameData load(BufferedReader reader) throws IOException;

    /**
     * 将数据写入文件
     */
    void save(PrintWriter writer, GameData data) throws IOException;

    /**
     * 从旧版本升级数据（数据迁移）
     */
    GameData upgrade(GameData oldData);
}

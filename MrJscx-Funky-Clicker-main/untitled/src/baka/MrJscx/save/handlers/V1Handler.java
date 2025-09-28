package baka.MrJscx.save.handlers;

import baka.MrJscx.save.GameData;
import java.io.*;

/**
 * 版本1处理器 - 只支持基础点击功能
 */
public class V1Handler implements SaveHandler {

    @Override
    public GameData load(BufferedReader reader) throws IOException {
        GameData data = new GameData();
        // V1格式：版本号\nFunky值\n点击力量
        data.currentFunky = Integer.parseInt(reader.readLine());
        data.funkyPerClick = Integer.parseInt(reader.readLine());
        // V1没有助手数据，使用默认值0
        data.autoClickers = 0;
        return data;
    }

    @Override
    public void save(PrintWriter writer, GameData data) throws IOException {
        // V1只保存基础数据
        writer.println(1); // 版本号
        writer.println(data.currentFunky);
        writer.println(data.funkyPerClick);
        // 不保存助手数量
    }

    @Override
    public GameData upgrade(GameData oldData) {
        // V1升级到V2：添加助手数量（设为0）
        GameData newData = new GameData();
        newData.currentFunky = oldData.currentFunky;
        newData.funkyPerClick = oldData.funkyPerClick;
        newData.autoClickers = 0; // V1没有助手，设为0
        return newData;
    }
}

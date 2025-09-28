package baka.MrJscx.save.handlers;

import baka.MrJscx.save.GameData;
import java.io.*;

/**
 * 版本2处理器 - 支持助手系统
 */
public class V2Handler implements SaveHandler {

    @Override
    public GameData load(BufferedReader reader) throws IOException {
        GameData data = new GameData();
        // V2格式：版本号\nFunky值\n点击力量\n助手数量
        data.currentFunky = Integer.parseInt(reader.readLine());
        data.funkyPerClick = Integer.parseInt(reader.readLine());
        data.autoClickers = Integer.parseInt(reader.readLine());
        return data;
    }

    @Override
    public void save(PrintWriter writer, GameData data) throws IOException {
        writer.println(2); // 版本号
        writer.println(data.currentFunky);
        writer.println(data.funkyPerClick);
        writer.println(data.autoClickers);
    }

    @Override
    public GameData upgrade(GameData oldData) {
        // V2升级到未来版本：目前直接返回，未来可以添加转换逻辑
        GameData newData = new GameData();
        newData.currentFunky = oldData.currentFunky;
        newData.funkyPerClick = oldData.funkyPerClick;
        newData.autoClickers = oldData.autoClickers;
        return newData;
    }
}

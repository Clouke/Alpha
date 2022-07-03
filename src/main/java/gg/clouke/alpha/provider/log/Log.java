package gg.clouke.alpha.provider.log;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.util.UUID;

/**
 * @author Lucanius
 * @since May 31, 2022.
 * Alpha - All Rights Reserved.
 */
@Getter @Setter
public class Log {

    private final UUID uuid;
    private final String player;

    private final long timeStamp;

    private final String baseName;
    private final String baseType;
    private final String data;
    private final int vl;

    private final Document document;

    public Log(UUID uuid, String player, String baseName, String baseType, int vl, String data) {
        this.uuid = uuid;
        this.player = player;

        this.timeStamp = System.currentTimeMillis();

        this.baseName = baseName;
        this.baseType = baseType;
        this.vl = vl;
        this.data = data;

        this.document = new Document()
                .append("uuid", uuid.toString())
                .append("player", player)
                .append("timeStamp", timeStamp)
                .append("baseName", baseName)
                .append("baseType", baseType)
                .append("vl", vl)
                .append("data", data);
    }

    public Log(Document document) {
        this.uuid = UUID.fromString(document.getString("uuid"));
        this.player = document.getString("player");

        this.timeStamp = document.getLong("timeStamp");

        this.baseName = document.getString("baseName");
        this.baseType = document.getString("baseType");
        this.vl = document.getInteger("vl");
        this.data = document.getString("data");

        this.document = document;
    }

    public String string() {
        return "&e[" + baseName + " " + baseType + "] &7" + data;
    }

    public String append() {
        return player + " -> [" + baseName + " " + baseType + "] " + data + "\n";
    }
}

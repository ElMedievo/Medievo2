package org.elmedievo.medievo2.Ranks;

import org.apache.commons.io.FileUtils;
import org.elmedievo.medievo2.Medievo2;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;


public class RankRegistry {
    private Document ranksXML;
    private List<Rank> ranks = new ArrayList<>();

    public RankRegistry() {
    }

    public List<Rank> getRanks() {
        return ranks;
    }

    public void setRanks(List<Rank> ranks) {
        this.ranks = ranks;
    }

    public void load() {
        File file = new File(Medievo2.getPlugin.getDataFolder(), "ranks.xml");
        if (!file.exists()) {
            try {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(Medievo2.getPlugin.getResource("ranks.xml")), file);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        try {
            ranksXML = new SAXBuilder().build(file);
        } catch (JDOMException | IOException exception) {
            exception.printStackTrace();
        }
        loadRankModels();
    }

    public void reload() {
        ranks.clear();
        load();
    }

    private void loadRankModels() {
        Element root = ranksXML.getRootElement();
        root.getChildren("rank").forEach(rankModule -> {
            String name = rankModule.getAttributeValue("name");
            String flair = rankModule.getAttributeValue("flair");
            boolean staff = Boolean.getBoolean(rankModule.getAttributeValue("staff"));
            int priority = Integer.parseInt(rankModule.getAttributeValue("priority"));

            List<String> permissions = new ArrayList<>();
            rankModule.getChildren().forEach(permissionModule -> permissions.add(permissionModule.getText()));

            List<String> inheritedPermissions = new ArrayList<>();
            if (rankModule.getAttributeValue("parents") != null) {
                String trimmedParents = rankModule.getAttributeValue("parents").replaceAll(" ", "");
                String[] parents = trimmedParents.split(",");

                root.getChildren().forEach(probableParent -> {
                    for (String parent : parents) {
                        if (probableParent.getAttributeValue("name").equalsIgnoreCase(parent)) {
                            probableParent.getChildren().forEach(parentPermissionModule -> inheritedPermissions.add(parentPermissionModule.getText()));
                        }
                    }
                });

                permissions.addAll(inheritedPermissions);
            }

            ranks.add(new Rank(name, flair, staff, priority, permissions));
        });
    }

    public boolean containsRank(String rankName) {
        AtomicBoolean exists = new AtomicBoolean(false);
        ranks.forEach(rank -> {
            if (rank.getName().equalsIgnoreCase(rankName)) exists.set(true);
        });
        return exists.get();
    }
}

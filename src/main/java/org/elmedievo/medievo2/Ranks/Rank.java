package org.elmedievo.medievo2.Ranks;

import java.util.List;

public class Rank {
    private String name;
    private String flair;
    private boolean staff;
    private int priority;
    private List<String> permissions;

    Rank(String name, String flair, boolean staff, int priority, List<String> permissions) {
        this.name = name;
        this.flair = flair;
        this.staff = staff;
        this.priority = priority;
        this.permissions = permissions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlair() {
        return flair;
    }

    public void setFlair(String flair) {
        this.flair = flair;
    }

    public boolean isStaff() {
        return staff;
    }

    public void setStaff(boolean staff) {
        this.staff = staff;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}

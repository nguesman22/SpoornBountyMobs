package org.spoorn.spoornbountymobs;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Bounty tiers to make bounties more dynamic.
 */
@EqualsAndHashCode
@ToString
public final class SpoornBountyTiers {

    public static final SpoornBountyTiers COMMON = new SpoornBountyTiers("common", 20);
    public static final SpoornBountyTiers UNCOMMON = new SpoornBountyTiers("uncommon", 15);
    public static final SpoornBountyTiers RARE = new SpoornBountyTiers("rare", 10);
    public static final SpoornBountyTiers EPIC = new SpoornBountyTiers("epic", 6);
    public static final SpoornBountyTiers LEGENDARY = new SpoornBountyTiers("legendary", 3);
    public static final SpoornBountyTiers DOOM = new SpoornBountyTiers("doom", 1);

    @Getter
    private String value;
    @Getter
    private double weight;

    private SpoornBountyTiers(String value, int weight) {
        this.value = value;
        this.weight = weight;
    }

    public static SpoornBountyTiers fromValue(String value) {
        switch (value) {
            case "common":
                return COMMON;
            case "uncommon":
                return UNCOMMON;
            case "rare":
                return RARE;
            case "epic":
                return EPIC;
            case "legendary":
                return LEGENDARY;
            case "doom":
                return DOOM;
            default:
                return COMMON;
        }
    }
}

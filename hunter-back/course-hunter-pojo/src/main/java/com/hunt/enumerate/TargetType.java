package com.hunt.enumerate;

import lombok.Getter;

@Getter
public enum TargetType {
    TEACHER(1),
    COURSE(2);

    private final int value;

    TargetType(int value) {
        this.value = value;
    }

    public static TargetType fromValue(int value) {
        for (TargetType type : TargetType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid TargetType value: " + value);
    }
}
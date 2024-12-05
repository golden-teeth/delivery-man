package com.delivery_man.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExpiredPolish {
    POINT(1);
    private final int months;
}

package dev.yubin.imageconverter.api.common.util;

import de.huxhorn.sulky.ulid.ULID;

public class ULIDGenerator {

    private static final ULID ulid = new ULID();

    private ULIDGenerator() {
        // 유틸 클래스이므로 인스턴스화 방지
    }

    public static String generate() {
        return ulid.nextULID();
    }

}
package com.ssafy.maryfarmboardservice.domain.board;

public enum BoardType {
    SEOUL("서울"), INCHEON("인천"), KANGWON("강원"), CHUNGCHEONG("충청"),
    GYEONGSANG("경상"), JEONLA("전라"), JEJU("제주");

    private String krName;
    BoardType(String krName) {
        this.krName = krName;
    }
    public String getKrName() {
        return krName;
    }

    public static BoardType nameOf(String name) {
        for(BoardType type : BoardType.values()) {
            if(type.getKrName().equals(name)) {
                return type;
            }
        }
        return null;
    }
}

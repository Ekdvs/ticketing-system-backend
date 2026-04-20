package com.company.ticket_booking_backend.model;

import java.util.Arrays;

public enum SubCategory {

    // ================= EVENT =================
    CONCERT(Category.EVENT),
    LIONEL_WENDT_THEATRE(Category.EVENT),
    DINNER_DANCE(Category.EVENT),
    EDM(Category.EVENT),
    CLASSICAL(Category.EVENT),
    EDUCATIONAL(Category.EVENT),
    EXHIBITION(Category.EVENT),
    EVENT_FESTIVAL(Category.EVENT),
    ORCHESTRAL(Category.EVENT),
    SEMINAR(Category.EVENT),
    CONFERENCE(Category.EVENT),
    MUSICAL_FESTIVAL(Category.EVENT),
    TECH(Category.EVENT),
    FREE(Category.EVENT),
    ONLINE(Category.EVENT),
    DJ(Category.EVENT),
    OTHER_EVENT(Category.EVENT),

    // ================= MOVIE =================
    MOVIE_FESTIVAL(Category.MOVIE),
    ACTION_MOVIE(Category.MOVIE),
    ADVENTURE_MOVIE(Category.MOVIE),
    ANIMATION(Category.MOVIE),
    ART(Category.MOVIE),
    BIOGRAPHY(Category.MOVIE),
    CHILDREN_MOVIE(Category.MOVIE),
    COMEDY_MOVIE(Category.MOVIE),
    CRIME(Category.MOVIE),
    DOCUMENTARY(Category.MOVIE),
    DRAMA_MOVIE(Category.MOVIE),
    EPIC(Category.MOVIE),
    FANTASY(Category.MOVIE),
    FICTION(Category.MOVIE),
    HISTORICAL(Category.MOVIE),
    HORROR(Category.MOVIE),
    MUSICAL(Category.MOVIE),
    MYSTERY(Category.MOVIE),
    ROMANCE(Category.MOVIE),
    SCIENCE_FICTION(Category.MOVIE),
    SPORTS_MOVIE(Category.MOVIE),
    THRILLER(Category.MOVIE),
    WESTERN(Category.MOVIE),
    ADULT_18_PLUS(Category.MOVIE),

    // ================= SPORT =================
    CRICKET(Category.SPORT),
    RUGBY(Category.SPORT),
    FOOTBALL(Category.SPORT),
    MOTOR_SPORT(Category.SPORT),
    CHILDREN_SPORT(Category.SPORT),
    BASKETBALL(Category.SPORT),
    SPORT_FESTIVAL(Category.SPORT),
    GOLF(Category.SPORT),
    BOXING(Category.SPORT),
    VOLLEYBALL(Category.SPORT),

    // ================= FOOD =================
    FOOD_FESTIVAL(Category.FOOD),
    TABLE_RESERVATION(Category.FOOD),
    FOOD_ORDER(Category.FOOD),
    RESTAURANT(Category.FOOD),
    PUB_AND_BAR(Category.FOOD),
    HOTELS(Category.FOOD),

    // ================= OTHER =================
    CULTURAL_FESTIVAL(Category.OTHER),
    E_VOUCHERS(Category.OTHER),

    // ================= HOLIDAY =================
    AMUSEMENT_PARK(Category.HOLIDAY),
    ADVENTURE_TRAVEL(Category.HOLIDAY),
    CARTING(Category.HOLIDAY),
    SURFING(Category.HOLIDAY),
    DIVING(Category.HOLIDAY),
    TRAVELING(Category.HOLIDAY),
    WHALE_WATCHING(Category.HOLIDAY),

    ATTRACTIONS(Category.HOLIDAY),
    RIDES(Category.HOLIDAY),
    ACTIVITIES(Category.HOLIDAY),
    CHILDREN_ACTIVITY(Category.HOLIDAY),

    HOTEL_BOOKING(Category.HOLIDAY),
    VILLA_BOOKING(Category.HOLIDAY),
    ROOM_RESERVATION(Category.HOLIDAY),

    HOLIDAY_FESTIVAL(Category.HOLIDAY),
    VOUCHERS(Category.HOLIDAY),

    WATER_RAFTING(Category.HOLIDAY),
    CAMPING(Category.HOLIDAY),
    HIKING(Category.HOLIDAY),
    NATIONAL_PARK(Category.HOLIDAY),

    OTHER_HOLIDAY(Category.HOLIDAY);

    // ================= FIELD =================
    private final Category category;

    // ================= CONSTRUCTOR =================
    SubCategory(Category category) {
        this.category = category;
    }

    // ================= GETTER =================
    public Category getCategory() {
        return category;
    }

    // ================= VALIDATION =================
    public static boolean isValid(Category category, SubCategory subCategory) {
        return subCategory.getCategory() == category;
    }

    // ================= OPTIONAL HELPER =================
    public static SubCategory fromString(String value) {
        return Arrays.stream(SubCategory.values())
                .filter(sc -> sc.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid SubCategory: " + value));
    }

    // ================= DISPLAY NAME =================
    public String displayName() {
        return this.name().replace("_", " ");
    }
}
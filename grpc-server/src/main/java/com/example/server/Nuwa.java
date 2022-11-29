package com.example.server;

import com.google.protobuf.Timestamp;
import com.lau.grpc.Friend;
import com.lau.grpc.Person;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.util.*;

/**
 * @author Lau wc7625716@163.com
 * Date: 2022/11/12
 * Description: 女娲
 */
public class Nuwa {

    private final Random random = new Random();

    private static volatile Nuwa INSTANCE;

    private Nuwa() {
    }

    public static Nuwa getInstance() {
        if (INSTANCE == null) {
            synchronized (Nuwa.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Nuwa();
                }
            }
        }

        return INSTANCE;
    }

    public Person newPerson() {
        String name = randomStringFromSet("Tom", "Jerry", "Lisa", "Jonny");
        int age = randomInt(1, 100);
        boolean isAdult = random.nextBoolean();

        // oneof
        int heightCm = randomInt(160, 220);
        double heightM = randomDouble(1.6, 2.2);

        Person.Color color = randomPersonColor();
        Person.Gender gender = randomPersonGender();

        List<String> hobbies = randomHobbies(randomInt(1, 3));

        Map<String, Friend> friends = getFriends(randomInt(2, 10));

        Timestamp birthday = randomTimestamp();

        return Person.newBuilder()
                .setName(name)
                .setAge(age)
                .setIsAdult(isAdult)
                .setHeightCm(heightCm)
                .setHeightM(heightM)
                .setColor(color)
                .setGender(gender)
                .addAllHobbies(hobbies)
                .putAllFriends(friends)
                .setBirthday(birthday)
                .build();
    }

    private Timestamp randomTimestamp() {
        LocalDate date = LocalDate.of(randomInt(1900, 2022), randomInt(1, 12), randomInt(1, 27));
        LocalDateTime birthday = LocalDateTime.of(date, LocalTime.NOON);
        Instant instant = birthday.toInstant(ZoneOffset.UTC);
        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }

    private List<String> randomHobbies(int n) {
        List<String> hobbies = new ArrayList<>(n);
        String[] hobbiesOptions = new String[]{
                "跑",
                "走",
                "跳",
                "睡",
                "吃"
        };
        for (int i = 0; i < n; i++) {
            hobbies.add(hobbiesOptions[randomInt(0, hobbiesOptions.length - 1)]);
        }
        return hobbies;
    }

    private Map<String, Friend> getFriends(int n) {
        Map<String, Friend> friends = new HashMap<>(n);
        String friendName;
        int friendAge;
        for (int i = 0; i < n; i++) {
            friendName = randomStringFromSet(
                    "张三",
                    "李四",
                    "王五",
                    "赵六",
                    "孙七",
                    "王八"
            );
            friendAge = randomInt(1, 100);

            Friend friend = Friend.newBuilder()
                    .setName(friendName)
                    .setAge(friendAge)
                    .setBirthday(randomTimestamp())
                    .build();
            friends.put(friendName, friend);
        }
        return friends;
    }

    private Person.Color randomPersonColor() {
        switch (random.nextInt(4)) {
            case 1:
                return Person.Color.YELLOW;
            case 2:
                return Person.Color.WHITE;
            case 3:
                return Person.Color.BLACK;
            default:
                return Person.Color.UNKNOWN;
        }
    }

    private Person.Gender randomPersonGender() {
        switch (random.nextInt(3)) {
            case 1:
                return Person.Gender.FEMALE;
            case 2:
                return Person.Gender.MALE;
            default:
                return Person.Gender.SECRET;
        }
    }

    private double randomDouble(double min, double max) {
        return randomDouble(min, max, 2);
    }

    private double randomDouble(double min, double max, int scale) {
        return new BigDecimal(min + random.nextDouble() * (max - min)).setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }

    private int randomInt(int min, int max) {
        return min + random.nextInt(max - min + 1);
    }


    private String randomStringFromSet(String... strings) {
        if (strings.length == 0) {
            return null;
        }
        return strings[random.nextInt(strings.length)];
    }

}

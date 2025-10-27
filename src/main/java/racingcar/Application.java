package racingcar;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        try {
            List<Car> cars = getCars();
            int rounds = getRounds();

            System.out.println();
            System.out.println("실행 결과");
            playGame(cars, rounds);

            announceWinners(cars);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    private static List<Car> getCars() {
        System.out.println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)");
        String input = Console.readLine();
        String[] names = input.split(",");

        List<Car> cars = new ArrayList<>();
        for (String name : names) {
            validateName(name);
            cars.add(new Car(name));
        }
        return cars;
    }

    private static void validateName(String name) {
        if (name.length() > 5) {
            throw new IllegalArgumentException("자동차 이름은 5자 이하여야 합니다.");
        }
    }

    private static int getRounds() {
        System.out.println("시도할 횟수는 몇 회인가요?");
        String input = Console.readLine();
        return Integer.parseInt(input);
    }

    private static void playGame(List<Car> cars, int rounds) {
        for (int i = 0; i < rounds; i++) {
            moveAllCars(cars);
            printStatus(cars);
        }
    }

    private static void moveAllCars(List<Car> cars) {
        for (Car car : cars) {
            int randomValue = Randoms.pickNumberInRange(0, 9);
            if (randomValue >= 4) {
                car.move();
            }
        }
    }

    private static void printStatus(List<Car> cars) {
        for (Car car : cars) {
            System.out.println(car.getName() + " : " + car.getPosition());
        }
        System.out.println();
    }

    private static void announceWinners(List<Car> cars) {
        int maxDistance = getMaxDistance(cars);
        List<String> winners = getWinners(cars, maxDistance);
        System.out.println("최종 우승자 : " + String.join(", ", winners));
    }

    private static int getMaxDistance(List<Car> cars) {
        int max = 0;
        for (Car car : cars) {
            if (car.getDistance() > max) {
                max = car.getDistance();
            }
        }
        return max;
    }

    private static List<String> getWinners(List<Car> cars, int maxDistance) {
        List<String> winners = new ArrayList<>();
        for (Car car : cars) {
            if (car.getDistance() == maxDistance) {
                winners.add(car.getName());
            }
        }
        return winners;
    }
}

class Car {
    private final String name;
    private int distance;

    public Car(String name) {
        this.name = name;
        this.distance = 0;
    }

    public void move() {
        distance++;
    }

    public String getName() {
        return name;
    }

    public int getDistance() {
        return distance;
    }

    public String getPosition() {
        return "-".repeat(distance);
    }
}
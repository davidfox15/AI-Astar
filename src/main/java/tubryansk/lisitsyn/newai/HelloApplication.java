package tubryansk.lisitsyn.newai;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.*;

public class HelloApplication extends Application {
    private final int size = 12;
    private int mapChose = 1;
    private Cube cube;
    private Cube target;
    private List<Field> fields;

    @Override
    public void start(Stage stage) {
        //Создание рамки
        BorderPane border = new BorderPane();
        //Создание карты и кубика
        reset(border, mapChose);
        //Создание Hatbox
        HBox control = new HBox();
        control.setPrefHeight(40);
        control.setSpacing(10.0);
        control.setAlignment(Pos.BASELINE_CENTER);
        //Создание кнопки
        Button start = new Button("BFS");
        Button startD = new Button("DFS");
        Button startA = new Button("A*");
        Button restart = new Button("Рестарт");
        Button map1 = new Button("1");
        Button map2 = new Button("2");
        Button map3 = new Button("3");
        Button map4 = new Button("4");
        control.getChildren().addAll(start);
        control.getChildren().addAll(startD);
        control.getChildren().addAll(startA);
        control.getChildren().addAll(restart);
        control.getChildren().addAll(map1);
        control.getChildren().addAll(map2);
        control.getChildren().addAll(map3);
        control.getChildren().addAll(map4);
        border.setBottom(control);
        border.setCenter(buildGrid());
        //Создание окна и сцены
        Scene scene = new Scene(border, 800, 600);
        stage.setScene(scene);
        stage.setTitle("AI");
        stage.setResizable(false);
        stage.show();
        //Обработка нажатия на кнопку мышью
        start.setOnMouseClicked(
                event -> this.play(border)
        );
        startD.setOnMouseClicked(
                event -> this.playD(border)
        );
        startA.setOnMouseClicked(
                event -> this.playA(border)
        );
        restart.setOnMouseClicked(
                event -> this.reset(border, mapChose)
        );
        map1.setOnMouseClicked(
                event -> this.reset(border, 1)
        );
        map2.setOnMouseClicked(
                event -> this.reset(border, 2)
        );
        map3.setOnMouseClicked(
                event -> this.reset(border, 3)
        );
        map4.setOnMouseClicked(
                event -> this.reset(border, 4)
        );
        //Обработка нажатия кнопок
        control.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.A) {
                //System.out.println("GO LEFT!");
                cube.x = cube.x - 1;
                refresh(border);
            }
            if (event.getCode() == KeyCode.D) {
                //System.out.println("GO RIGHT!");
                cube.x = cube.x + 1;
                //cube.moveRight(map);
                refresh(border);
            }
            if (event.getCode() == KeyCode.W) {
                //System.out.println("GO UP!");
                cube.y = cube.y - 1;
                //cube.moveUp(map);
                refresh(border);
            }
            if (event.getCode() == KeyCode.S) {
                //aSystem.out.println("GO DOWN!");
                cube.y = cube.y + 1;
                //cube.moveDown(map);
                refresh(border);
            }
            if (event.getCode() == KeyCode.C) {
                if (cube.last != null)
                    cube = cube.last;
                refresh(border);
            }
            if (event.getCode() == KeyCode.T) {
                test(border);
            }
        });
        System.out.println("Название алгоритма - Кол-во итераций - Кол-во состояний в конце - Максимальное кол-во состояний - Максимальное количество состояний в закрытом списке - Длинна маршрута - Полей проверено");
        refresh(border);
    }

    private void test(BorderPane border) {
        for (int i = 1; i < 5; i++) {
            System.out.println("Map" + i);
            reset(border, i);
            playD(border);
            reset(border, i);
            play(border);
            reset(border, i);
            playA(border);
        }
    }

    private void refresh(final BorderPane border) {
        Group grid = buildGrid();
        border.setCenter(grid);
        grid.getChildren().add(buildRectangle(cube.x, cube.y, Color.RED));
    }

    private void reset(BorderPane border, int x) {
        mapChose = x;
        switch (mapChose) {
            case 1:
                map(border);
                break;
            case 2:
                map2(border);
                break;
            case 3:
                map3(border);
                break;
            case 4:
                map4(border);
                break;
        }
    }

    private void map(BorderPane border) {
        // Задаем стар и финшь
        // Ставим куби на старт и создаем целевое состояние
        int[] start = new int[]{1, 8};
        cube = new Cube(start[0], start[1]);
        int[] finish = new int[]{8, 8};
        target = new Cube(finish[0], finish[1]);
        // Заполняем карту полями
        fields = new ArrayList<>();
        for (int y = 0; y != this.size; y++) {
            for (int x = 0; x != this.size; x++) {
                if (x == start[0] && y == start[1])
                    fields.add(new Field(x, y, FieldType.START));
                else if (x == finish[0] && y == finish[1])
                    fields.add(new Field(x, y, FieldType.FINISH));
                else
                    fields.add(new Field(x, y, FieldType.FREE));
            }
        }
        addWall(1, 2);
        addWall(2, 2);
        addWall(3, 2);
        addWall(4, 2);
        addWall(5, 2);
        addWall(5, 3);
        addWall(5, 4);
        addWall(5, 5);
        addWall(6, 5);
        addWall(7, 5);
        addWall(8, 5);
        addWall(9, 5);
        addWall(10, 5);
        addWall(5, 6);
        addWall(5, 7);
        addWall(5, 8);
        addWall(5, 9);
        refresh(border);
    }

    private void map2(BorderPane border) {
        // Задаем стар и финшь
        // Ставим куби на старт и создаем целевое состояние
        int[] start = new int[]{5, 6};
        cube = new Cube(start[0], start[1]);
        int[] finish = new int[]{5, 11};
        target = new Cube(finish[0], finish[1]);
        // Заполняем карту полями
        fields = new ArrayList<>();
        for (int y = 0; y != this.size; y++) {
            for (int x = 0; x != this.size; x++) {
                if (x == start[0] && y == start[1])
                    fields.add(new Field(x, y, FieldType.START));
                else if (x == finish[0] && y == finish[1])
                    fields.add(new Field(x, y, FieldType.FINISH));
                else
                    fields.add(new Field(x, y, FieldType.FREE));
            }
        }
        addWall(5, 0);
        addWall(6, 0);
        addWall(7, 0);

        addWall(8, 0);
        addWall(8, 1);
        addWall(8, 2);

        addWall(5, 2);
        addWall(4, 2);
        addWall(3, 2);
        addWall(2, 2);
        addWall(1, 2);
        addWall(1, 3);
        addWall(1, 4);
        addWall(1, 5);
        addWall(1, 6);
        addWall(1, 7);

        addWall(1, 8);
        addWall(2, 8);
        addWall(3, 8);
        addWall(4, 8);
        addWall(5, 8);
        addWall(6, 8);
        addWall(7, 8);
        addWall(8, 8);

        addWall(3, 6);
        addWall(3, 5);
        addWall(3, 4);
        addWall(4, 4);
        addWall(5, 4);
        addWall(6, 4);
        addWall(7, 4);
        addWall(7, 5);
        addWall(7, 6);

        addWall(10, 2);
        addWall(10, 3);
        addWall(10, 4);
        addWall(10, 5);
        addWall(10, 6);
        addWall(10, 7);
        addWall(10, 8);
        addWall(10, 9);
        addWall(10, 10);
        addWall(9, 10);
        addWall(8, 10);
        addWall(7, 10);
        addWall(6, 10);
        addWall(5, 10);
        addWall(4, 10);
        addWall(3, 10);
        addWall(2, 10);
        addWall(1, 10);


        addWall(5, 5);
        addWall(7, 7);
        refresh(border);
    }

    private void map3(BorderPane border) {
        // Задаем стар и финшь
        // Ставим куби на старт и создаем целевое состояние
        int[] start = new int[]{0, 11};
        cube = new Cube(start[0], start[1]);
        int[] finish = new int[]{9, 1};
        target = new Cube(finish[0], finish[1]);
        // Заполняем карту полями
        fields = new ArrayList<>();
        for (int y = 0; y != this.size; y++) {
            for (int x = 0; x != this.size; x++) {
                if (x == start[0] && y == start[1])
                    fields.add(new Field(x, y, FieldType.START));
                else if (x == finish[0] && y == finish[1])
                    fields.add(new Field(x, y, FieldType.FINISH));
                else
                    fields.add(new Field(x, y, FieldType.FREE));
            }
        }
        addWall(0, 5);
        addWall(1, 5);
        addWall(2, 5);
        addWall(3, 5);
        addWall(5, 11);
        addWall(5, 10);
        addWall(5, 9);
        addWall(5, 8);
        addWall(8, 10);
        addWall(8, 9);
        addWall(8, 8);
        addWall(8, 7);
        addWall(8, 6);
        addWall(8, 5);
        addWall(8, 4);
        addWall(8, 3);
        addWall(8, 2);
        addWall(1, 2);
        addWall(2, 2);
        addWall(3, 2);
        addWall(4, 2);
        addWall(5, 2);
        addWall(6, 2);
        addWall(7, 2);
        refresh(border);
    }

    private void map4(BorderPane border) {
        // Задаем стар и финшь
        // Ставим куби на старт и создаем целевое состояние
        int[] start = new int[]{0, 11};
        cube = new Cube(start[0], start[1]);
        int[] finish = new int[]{9, 1};
        target = new Cube(finish[0], finish[1]);
        // Заполняем карту полями
        fields = new ArrayList<>();
        for (int y = 0; y != this.size; y++) {
            for (int x = 0; x != this.size; x++) {
                if (x == start[0] && y == start[1])
                    fields.add(new Field(x, y, FieldType.START));
                else if (x == finish[0] && y == finish[1])
                    fields.add(new Field(x, y, FieldType.FINISH));
                else
                    fields.add(new Field(x, y, FieldType.FREE));
            }
        }
        addWall(0, 5);
        addWall(1, 5);
        addWall(2, 5);
        addWall(3, 5);
        addWall(5, 11);
        addWall(5, 10);
        addWall(5, 9);
        addWall(5, 8);
        addWall(8, 10);
        addWall(8, 9);
        addWall(8, 8);
        addWall(8, 7);
        addWall(8, 6);
        addWall(8, 5);
        addWall(8, 4);
        addWall(8, 2);
        addWall(1, 2);
        addWall(2, 2);
        addWall(3, 2);
        addWall(4, 2);
        addWall(5, 2);
        addWall(6, 2);
        addWall(7, 2);
        refresh(border);
    }

    private Rectangle buildRectangle(int x, int y, Color color) {
        Rectangle rect = new Rectangle();
        rect.setX(x * 40);
        rect.setY(y * 40);
        rect.setHeight(40);
        rect.setWidth(40);
        rect.setFill(color);
        rect.setStroke(Color.BLACK);
        return rect;
    }

    private Rectangle buildRectangle(int x, int y, String image) {
        Rectangle rect = new Rectangle();
        rect.setX(x * 40);
        rect.setY(y * 40);
        rect.setHeight(40);
        rect.setWidth(40);
        Image img = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource(image)).toString());
        rect.setFill(new ImagePattern(img));
        rect.setStroke(Color.BLACK);
        return rect;
    }

    private Group buildGrid() {
        Group panel = new Group();
        for (Field field : fields) {
            switch (field.type) {
                case START:
                    panel.getChildren().add(buildRectangle(field.x, field.y, "start.png"));
                    break;
                case FINISH:
                    panel.getChildren().add(buildRectangle(field.x, field.y, "finish.png"));
                    break;
                case FREE:
                    panel.getChildren().add(buildRectangle(field.x, field.y, Color.WHITE));
                    break;
                case VISITED:
                    panel.getChildren().add(buildRectangle(field.x, field.y, Color.GAINSBORO));
                    break;
                case ROAD:
                    panel.getChildren().add(buildRectangle(field.x, field.y, Color.AQUA));
                    break;
                case BLOCK:
                    panel.getChildren().add(buildRectangle(field.x, field.y, Color.GRAY));
                    break;
            }
        }
        return panel;
    }

    private void playD(BorderPane border) {
        int maxOpenList = 0;
        int maxClosedList = 0;
        int counter = 0;

        Stack<Cube> openList = new Stack<>();
        LinkedList<Cube> closedList = new LinkedList<>();

        openList.add(cube);

        while (!openList.isEmpty()) {
            counter++;
            Cube tmp = openList.peek();

            changeColor(tmp, border, FieldType.VISITED);


            if (tmp.x == target.x && tmp.y == target.y && tmp.sides.get("UP") == target.sides.get("UP")) {
                System.out.println("DFS [ " + (counter - 1) + " - " + openList.size() + " - " + maxOpenList + " - " + maxClosedList + " - " + lengthOfRoad(tmp, border) + " - " + visitedFieldsCount() + " ] ");
                cube = tmp;
                refresh(border);
                return;
            }
            closedList.add(tmp);
            openList.remove(tmp);

            // Смежные верщины
            for (Cube m : tmp.countNeighbors(fields)) {
                m.last = tmp;
                if (!openList.contains(m) && !closedList.contains(m)) {
                    openList.add(m);
                }
            }
            if (maxOpenList < openList.size()) {
                maxOpenList = openList.size();
                for (Cube c : openList) {
                    changeColor(c, border, FieldType.VISITED);
                }
            }
            if (maxClosedList < closedList.size()) {
                maxClosedList = closedList.size();
            }
        }
    }

    private void play(BorderPane border) {
        int maxOpenList = 0;
        int maxClosedList = 0;
        int counter = 0;

        LinkedList<Cube> openList = new LinkedList<>();
        LinkedList<Cube> closedList = new LinkedList<>();

        openList.add(cube);

        while (!openList.isEmpty()) {
            counter++;
            Cube tmp = openList.getFirst();

            changeColor(tmp, border, FieldType.VISITED);


            if (tmp.x == target.x && tmp.y == target.y && tmp.sides.get("UP") == target.sides.get("UP")) {
                System.out.println("BFS [ " + (counter - 1) + " - " + openList.size() + " - " + maxOpenList + " - " + maxClosedList + " - " + lengthOfRoad(tmp, border) + " - " + visitedFieldsCount() + " ] ");
                cube = tmp;
                refresh(border);
                return;
            }
            closedList.add(tmp);
            openList.remove(tmp);

            // Смежные верщины
            for (Cube m : tmp.countNeighbors(fields)) {
                m.last = tmp;
                if (!openList.contains(m) && !closedList.contains(m)) {
                    openList.add(m);
                }
            }
            if (maxOpenList < openList.size()) {
                maxOpenList = openList.size();
                for (Cube c : openList) {
                    changeColor(c, border, FieldType.VISITED);
                }
            }
            if (maxClosedList < closedList.size()) {
                maxClosedList = closedList.size();
            }
        }
    }

    private void playA(BorderPane border) {
        int maxOpenList = 0;
        int maxClosedList = 0;
        int counter = 0;

        List<Cube> openList = new ArrayList<>();
        List<Cube> closedList = new ArrayList<>();

        cube.g = 0;
        cube.f = cube.g + cube.countH(target);

        openList.add(cube);

        while (!openList.isEmpty()) {
            counter++;

            // Упорядочиваем наш список по возрастанию
            Comparator<Cube> FComparator = new CubeFComparator();
            openList.sort(FComparator);

            Cube tmp = openList.get(0);

            changeColor(tmp, border, FieldType.VISITED);

            if (tmp.x == target.x && tmp.y == target.y && tmp.sides.get("UP") == target.sides.get("UP")) {
                System.out.println("A* [ " + (counter - 1) + " - " + openList.size() + " - " + maxOpenList + " - " + maxClosedList + " - " + lengthOfRoad(tmp, border) + " - " + visitedFieldsCount() + " ] ");
                cube = tmp;
                refresh(border);
                return;
            }

            closedList.add(tmp);
            openList.remove(tmp);

            // Смежные верщины
            for (Cube m : tmp.countNeighbors(fields)) {
                int totalWeight = tmp.g + 1;
                if (!openList.contains(m) && !closedList.contains(m)) {
                    m.last = tmp;
                    m.g = totalWeight;
                    m.f = m.g + m.countH(target);
                    openList.add(m);
                } else {
                    if (totalWeight < m.g) {
                        m.last = tmp;
                        m.g = totalWeight;
                        m.f = m.g + m.countH(target);
                        if (closedList.contains(m)) {
                            closedList.remove(m);
                            openList.add(m);
                        }
                    }
                }
            }
            if (maxOpenList < openList.size()) {
                maxOpenList = openList.size();
                for (Cube c : openList) {
                    changeColor(c, border, FieldType.VISITED);
                }
            }
            if (maxClosedList < closedList.size()) {
                maxClosedList = closedList.size();
            }
        }
    }

    private int visitedFieldsCount() {
        int count = 2;
        for (Field tmp : fields) {
            if (tmp.type == FieldType.VISITED || tmp.type == FieldType.ROAD)
                count++;
        }
        return count;
    }

    private void changeColor(Cube tmp, BorderPane border, FieldType fieldType) {
        for (Field f : fields) {
            if (tmp.x == f.x && tmp.y == f.y) {
                if (f.type == FieldType.FREE || f.type == FieldType.VISITED || f.type == FieldType.ROAD)
                    f.type = fieldType;
            }
        }
    }

    public int lengthOfRoad(Cube cube, BorderPane border) {
        int F = 0;
        Cube tmp = cube;
        while (tmp.last != null) {
            changeColor(tmp, border, FieldType.ROAD);
            F += 1;
            tmp = tmp.last;
        }
        return F;
    }

    public void addWall(int x, int y) {
        for (Field field : fields) {
            if (field.x == x && field.y == y) {
                if (field.type == FieldType.FREE)
                    field.type = FieldType.BLOCK;
                return;
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
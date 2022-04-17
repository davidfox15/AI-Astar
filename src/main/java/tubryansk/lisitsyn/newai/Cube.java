package tubryansk.lisitsyn.newai;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Cube {
    int x;
    int y;

//    Color up;
//    Color down;
//    Color forward;
//    Color back;
//    Color left;
//    Color right;

    public HashMap<String, Color> sides;

    Cube last = null;

    int g;
    int f;

    public Cube(int x, int y) {
        this.x = x;
        this.y = y;
//        this.up = Color.RED;
//        this.down = Color.BLUE;
//        this.forward = Color.GREEN;
//        this.back = Color.ORANGE;
//        this.left = Color.PURPLE;
//        this.right = Color.YELLOW;
        sides = new HashMap<>();
        sides.put("UP", Color.RED);
        sides.put("DOWN", Color.BLUE);
        sides.put("FORWARD", Color.GREEN);
        sides.put("BACK", Color.ORANGE);
        sides.put("LEFT", Color.PURPLE);
        sides.put("RIGHT", Color.YELLOW);
    }
    public Cube(int x, int y, Color up, Color down, Color forward, Color back, Color left, Color right) {
        this.x = x;
        this.y = y;
//        this.up = up;
//        this.down = down;
//        this.forward = forward;
//        this.back = back;
//        this.left = left;
//        this.right = right;
        sides = new HashMap<>();
        sides.put("UP", up);
        sides.put("DOWN", down);
        sides.put("FORWARD", forward);
        sides.put("BACK", back);
        sides.put("LEFT", left);
        sides.put("RIGHT", right);
    }

    public List<Cube> countNeighbors(List<Field> fields) {
        List<Cube> list = new ArrayList<>();
        if (findNoBlockField(x, y - 1, fields)) {
            list.add(new Cube(x, y - 1, sides.get("BACK"), sides.get("FORWARD"), sides.get("UP"), sides.get("DOWN"), sides.get("LEFT"), sides.get("RIGHT")));
//            list.add(new Cube(x, y - 1, back, forward, up, down, left, right));
        }
        if (findNoBlockField(x, y + 1, fields)) {
            list.add(new Cube(x, y + 1, sides.get("FORWARD"), sides.get("BACK"), sides.get("DOWN"), sides.get("UP"), sides.get("LEFT"), sides.get("RIGHT")));
//            list.add(new Cube(x, y + 1, forward, back, down, up, left, right));
        }
        if (findNoBlockField(x - 1, y, fields)) {
            list.add(new Cube(x - 1, y, sides.get("RIGHT"), sides.get("LEFT"), sides.get("FORWARD"), sides.get("BACK"), sides.get("UP"), sides.get("DOWN")));
//            list.add(new Cube(x - 1, y, right, left, forward, back, up, down));
        }
        if (findNoBlockField(x + 1, y, fields)) {
            list.add(new Cube(x + 1, y, sides.get("LEFT"), sides.get("RIGHT"), sides.get("FORWARD"), sides.get("BACK"), sides.get("DOWN"), sides.get("UP")));
//            list.add(new Cube(x + 1, y, left, right, forward, back, down, up));
        }
        return list;
    }

    private boolean findNoBlockField(int x, int y, List<Field> fields) {
        for (Field f : fields)
            if (f.x == x && f.y == y && f.type != FieldType.BLOCK)
                return true;
        return false;
    }

    // Contains сравнивает через equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cube cube = (Cube) o;
        return x == cube.x && y == cube.y && Objects.equals(sides, cube.sides);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, sides);
    }

    public int countH(Cube target) {
        return (Math.abs(x - target.x) + Math.abs(y - target.y));
    }
}

package tubryansk.lisitsyn.newai;

import java.util.Comparator;

public class CubeFComparator implements Comparator<Cube> {

    @Override
    public int compare(Cube o1, Cube o2) {
        return o1.f - o2.f;
    }
}

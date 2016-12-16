
package avl;

import javafx.scene.paint.Color;

public class Node {
	Color color;
	Comparable<Comparable<?>> info;// Dùng để so sánh
	Node trai, phai, cha;

	Node(Comparable<Comparable<?>> o) {
		color = Color.BLACK;
		info = o;
		trai = phai = cha = null;
	}

	Node(Comparable<Comparable<?>> o, Color c, Node g, Node d, Node p) {
		color = c;
		info = o;
		trai = g;
		phai = d;
		cha = p;
	}

	boolean laylinhcanh() {
		return this == Tree.linhcanh;
	}

	int layduongcao() {
		if (laylinhcanh())
			return 0;
		else
			return 1 + Math.max(trai.layduongcao(), phai.layduongcao());
	}

	boolean timkiem(Comparable<?> o) {
		if (laylinhcanh())
			return false;
		if (info.equals(o))
			return true;
		if (info.compareTo(o) == -1)
			return phai.timkiem(o);
		if (info.compareTo(o) == 1)
			return trai.timkiem(o);
		return false;
	}

}

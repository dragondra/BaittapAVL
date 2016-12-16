
package avl;

import javafx.scene.paint.Color;

/* cây đỏ đen
Một nút hoặc là đỏ hoặc đen.
Gốc là đen.
Tất cả các lá là đen.
Cả hai con của mọi nút đỏ là đen. (và suy ra mọi nút đỏ có nút cha là đen.)
Tất cả các đường đi từ một nút đã cho tới các lá chứa một số như nhau các nút đen.*/
public class Tree {
	static Node linhcanh;
	static {
		Tree.linhcanh = new Node(null, Color.BLACK, null, null, null);
	}
	Node goc, nodethem;

	public Tree() {
		goc = Tree.linhcanh;
	}

	// Comparable có nghĩa là so sánh
	public void them(Comparable o) {
		goc = them(o, goc, null);
		dieuchinhcay(nodethem);
	}

	// Hàm thêm một node
	private Node them(Comparable o, Node r, Node p) {
		// r p là cha mẹ

		if (r.laylinhcanh())
			r = nodethem = new Node(o, Color.RED, r, r, p);
		else if (o.compareTo(r.info) < 0)// sosanh
			r.trai = them(o, r.trai, r);
		else
			r.phai = them(o, r.phai, r);
		return r;
	}

	// hàm quay trái xử lý trường hợp mất cân bằng
	private void quaytrai(Node n) {
		Node y = n.phai;
		n.phai = y.trai;
		if (!y.trai.laylinhcanh())
			y.trai.cha = n;
		y.cha = n.cha;
		if (n.cha == null)
			goc = y;
		else if (n.cha.trai == n) //
			n.cha.trai = y;
		else
			n.cha.phai = y;
		y.trai = n;
		n.cha = y;
	}

	// hàm quay phai xử lý trường hợp mất cân bằng
	private void quayphai(Node n) {
		Node y = n.trai;
		n.trai = y.phai;
		if (!y.phai.laylinhcanh())
			y.phai.cha = n;
		y.cha = n.cha;
		if (n.cha == null)
			goc = y;
		else if (n.cha.phai == n)
			n.cha.phai = y;
		else
			n.cha.trai = y;
		y.phai = n;
		n.cha = y;
	}

	// Hàm điều chỉnh cây
	private void dieuchinhcay(Node n) {
		while (n != goc && n.cha.color == Color.RED) { // Vòng lập để kiểm tra
														// cây cân bằng chưa
			if (n.cha == n.cha.cha.trai) { // kiểm tra xem cây có lệch trái
											// không
				Node y = n.cha.cha.phai;
				if (y.color == Color.RED) {
					n.cha.color = Color.BLACK;
					y.color = Color.BLACK;
					n.cha.cha.color = Color.RED;
					n = n.cha.cha;
				} else {
					if (n == n.cha.phai) {
						n = n.cha;
						quaytrai(n);
					}
					n.cha.color = Color.BLACK;
					n.cha.cha.color = Color.RED;
					quayphai(n.cha.cha);
				}
			} else {
				Node y = n.cha.cha.trai;
				if (y.color == Color.RED) {
					n.cha.color = Color.BLACK;
					y.color = Color.BLACK;
					n.cha.cha.color = Color.RED;
					n = n.cha.cha;
				} else {
					if (nodethem == n.cha.trai) {
						n = n.cha;
						quayphai(n);
					}
					n.cha.color = Color.BLACK;
					n.cha.cha.color = Color.RED;
					quaytrai(n.cha.cha);
				}
			}
		}
		goc.color = Color.BLACK;

	}

	// Hàm xóa cây
	public void xoa(Comparable o) {
		xoa(goc, o);
	}

	// xóa
	private Node xoa(Node r, Comparable o) {
		if (r.laylinhcanh())
			return r; // Không tìm thấy ©
		if (o.compareTo(r.info) == 0)
			r = xoa(r);
		else if (o.compareTo(r.info) < 0)
			xoa(r.trai, o);
		else
			xoa(r.phai, o);
		return r;
	}

	// The © tiết theo phương pháp quả © ALISE việc loại bỏ các nút Na \
	// By nhỏ Ã © lÃ © ment của cây con phải, cô € ™ ông có hai con trai,
	// và ngăn chặn có hiệu quả, SA € ™ Dona € ™ nó có qua con trai € ™ một
	// Hàm xóa node
	private Node xoa(Node z) {
		Node y, x;
		if (z.trai.laylinhcanh() || z.phai.laylinhcanh())
			y = z;
		else
			y = caykethua(z);
		if (!y.trai.laylinhcanh())
			x = y.trai;
		else
			x = y.phai;
		x.cha = y.cha;
		if (y.cha == null)
			goc = x;
		else if (y == y.cha.trai)
			y.cha.trai = x;
		else
			y.cha.phai = x;
		if (y != z)
			z.info = y.info;

		// nếu Node Deleted © Node là một màu đỏ, ông didnâ € ™ không có gì để
		// làm cho cây giữ lại propria € ™ của nó được © © s
		// nhưng nếu ud NA ,
		// Chúng ta phải tổ chức lại trục € ™

		if (y.color == Color.BLACK)
			canbangcay(x);
		return y;
	}

	private Node caykethua(Node x) {

		// Thừa kế của node x trong cây,
		// Trọng điểm nếu điều này là lớn nhất
		if (!x.phai.laylinhcanh())
			return caynhonhat(x.phai);
		Node y = x.cha;
		while (!y.laylinhcanh() && x == y.phai) {
			x = y;
			y = x.cha;
		}
		return y;
	}

	private Node caynhonhat(Node x) {
		while (!x.trai.laylinhcanh())
			x = x.trai;
		return x;
	}

	// Hàm cân bằng cây (các màu của node, cấu trúc node)
	private void canbangcay(Node n) {
		// Tái tổ chức của cây, sẽ trở lại vào thư mục gốc
		while (n != goc && n.color == Color.BLACK) {
			if (n == n.cha.trai) {
				Node y = n.cha.phai;
				if (y.color == Color.RED) {
					y.color = Color.BLACK;
					n.cha.color = Color.RED;
					quaytrai(n.cha);
					y = n.cha.phai;
				}
				if (y.trai.color == Color.BLACK && y.phai.color == Color.BLACK) {
					y.color = Color.RED;
					n = n.cha;
				} else {
					if (y.phai.color == Color.BLACK) {
						y.trai.color = Color.BLACK;
						y.color = Color.RED;
						quayphai(y);
						y = n.cha.phai;
					}
					y.color = n.cha.color;
					n.cha.color = Color.BLACK;
					y.phai.color = Color.BLACK;
					quaytrai(n.cha);
					break;
				}
			} else {
				Node y = n.cha.trai;
				if (y.color == Color.RED) {
					y.color = Color.BLACK;
					n.cha.color = Color.RED;
					quayphai(n.cha);
					y = n.cha.trai;
				}
				if (y.phai.color == Color.BLACK && y.trai.color == Color.BLACK) {
					y.color = Color.RED;
					n = n.cha;
				} else {
					if (y.trai.color == Color.BLACK) {
						y.phai.color = Color.BLACK;
						y.color = Color.RED;
						quaytrai(y);
						y = n.cha.trai;
					}
					y.color = n.cha.color;
					n.cha.color = Color.BLACK;
					y.trai.color = Color.BLACK;
					quayphai(n.cha);
					break;
				}
			}
		}
		n.color = Color.BLACK;
	}
}

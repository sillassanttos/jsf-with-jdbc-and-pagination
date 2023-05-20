import java.util.List;

public class ProdutoDaoImpl extends GenericDaoImpl<Integer, Produto> {

	public ProdutoDaoImpl() {
		super(Produto.class, new MyDataSource());
	}

	public static void main(String[] args) throws Exception {

		ProdutoDaoImpl userDao = new ProdutoDaoImpl();

		List<Produto> produtos = userDao.getAll(new Filtro());

		if (produtos != null && produtos.size() > 0) {

			for (Produto p : produtos) {

				System.out.println(p.getId() + " " + p.getNome());

			}

		}

	}

}

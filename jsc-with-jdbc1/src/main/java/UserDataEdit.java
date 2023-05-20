import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "userDataEdit", eager = true)
@SessionScoped
public class UserDataEdit implements Serializable {

	private static final long serialVersionUID = 1L;

	private Produto produto = new Produto();

	public void atualizar() throws Exception {

		ProdutoDaoImpl dao = new ProdutoDaoImpl();

		try {
			
			if (this.produto.getId() != null && this.produto.getId() > 0) {

				dao.update(this.produto);

			} else {

				dao.save(this.produto);

			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}

	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

}

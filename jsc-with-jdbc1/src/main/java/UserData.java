
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UICommand;
import javax.faces.event.ActionEvent;

//@ManagedBean(name = "userData", eager = true)
@ManagedBean(name = "userData")
@SessionScoped
public class UserData implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Produto> produtos = new ArrayList<>();

	private Filtro filtro = new Filtro();

	private Integer[] pages;

	public UserData() {
		filtro = new Filtro();
		filtro.setRowsPerPage(5);
		filtro.setPageRange(10);
	}
	
	public void pesquisar() {
		
		this.produtos = listagem();
	}

	@PostConstruct
	public void inicializar() {
		pesquisar();
	}

	private List<Produto> listagem() {

		ProdutoDaoImpl produtoDao = new ProdutoDaoImpl();
		List<Produto> produtos = new ArrayList<>();

		try {

			produtos = produtoDao.getAll(filtro);
			// cdList = queryHelper.getListOfCds(firstRow, rowsPerPage);
			filtro.setTotalRows(produtoDao.count());
			// Set currentPage, totalPages and pages.
			filtro.setCurrentPage((filtro.getTotalRows() / filtro.getRowsPerPage())
					- ((filtro.getTotalRows() - filtro.getFirstRow()) / filtro.getRowsPerPage()) + 1);
			filtro.setTotalPages((filtro.getTotalRows() / filtro.getRowsPerPage())
					+ ((filtro.getTotalRows() % filtro.getRowsPerPage() != 0) ? 1 : 0));
			int pagesLength = Math.min(filtro.getPageRange(), filtro.getTotalPages());
			pages = new Integer[pagesLength];
			// firstPage must be greater than 0 and lesser than
			// totalPages-pageLength.
			int firstPage = Math.min(Math.max(0, filtro.getCurrentPage() - (filtro.getPageRange() / 2)),
					filtro.getTotalPages() - pagesLength);
			// Create pages (page numbers for page links).
			for (int i = 0; i < pagesLength; i++) {
				pages[i] = ++firstPage;
			}

			return produtos;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	// Paging actions
	// -----------------------------------------------------------------------------
	public void pageFirst() {
		page(0);
	}

	public void pageNext() {
		page(filtro.getFirstRow() + filtro.getRowsPerPage());
	}

	public void pagePrevious() {
		page(filtro.getFirstRow() - filtro.getRowsPerPage());
	}

	public void pageLast() {
		page(filtro.getTotalRows() - ((filtro.getTotalRows() % filtro.getRowsPerPage() != 0)
				? filtro.getTotalRows() % filtro.getRowsPerPage() : filtro.getRowsPerPage()));
	}

	public void page(ActionEvent event) {
		page(((Integer) ((UICommand) event.getComponent()).getValue() - 1) * filtro.getRowsPerPage());
	}

	private void page(int firstRow) {
		this.filtro.setFirstRow(firstRow);
		pesquisar();
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public Integer[] getPages() {
		return pages;
	}

	public void setPages(Integer[] pages) {
		this.pages = pages;
	}

	public Filtro getFiltro() {
		return filtro;
	}

	public void setFiltro(Filtro filtro) {
		this.filtro = filtro;
	}

}

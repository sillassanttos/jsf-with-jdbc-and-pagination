
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@SuppressWarnings("rawtypes")
@FacesConverter(forClass = Produto.class)
public class ProdutoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Produto retorno = null;

		if (value != null && !value.equals("")) {
			retorno = porId(new Integer(value));

			if (retorno == null) {
				String descricaoErro = "Produto nao encontrado";
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, descricaoErro, descricaoErro);
				throw new ConverterException(message);
			}
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Integer codigo = ((Produto) value).getId();
			return codigo == null ? "" : codigo.toString();
		}
		return null;
	}

	private Produto porId(Integer id) {

		ProdutoDaoImpl dao = new ProdutoDaoImpl();
		Produto produto = null;
		try {
			 produto = dao.get(id);
			return produto;
		} catch (Exception e) {
			e.printStackTrace();
			return produto;
		}

	}

}
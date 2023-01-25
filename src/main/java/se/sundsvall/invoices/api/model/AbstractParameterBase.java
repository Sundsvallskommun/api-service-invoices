package se.sundsvall.invoices.api.model;

import static java.lang.Integer.parseInt;

import java.util.Objects;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import io.swagger.v3.oas.annotations.media.Schema;

abstract class AbstractParameterBase {

	private static final String DEFAULT_LIMIT = "100";
	private static final String DEFAULT_PAGE = "1";

	@Schema(description = "Page number", example = DEFAULT_PAGE, defaultValue = DEFAULT_PAGE)
	@Min(1)
	protected int page = parseInt(DEFAULT_PAGE);

	@Schema(description = "Result size per page", example = DEFAULT_LIMIT, defaultValue = DEFAULT_LIMIT)
	@Min(1)
	@Max(1000)
	protected int limit = parseInt(DEFAULT_LIMIT);

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	@Override
	public int hashCode() {
		return Objects.hash(limit, page);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractParameterBase other = (AbstractParameterBase) obj;
		return limit == other.limit && page == other.page;
	}
}

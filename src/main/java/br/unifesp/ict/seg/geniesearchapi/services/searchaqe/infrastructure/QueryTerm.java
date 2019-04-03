package br.unifesp.ict.seg.geniesearchapi.services.searchaqe.infrastructure;

import java.util.ArrayList;
import java.util.List;

public class QueryTerm {

	private List<String> expandedTerms = new ArrayList<String>();
	private List<String> expandedTermsNot = new ArrayList<String>();

	public QueryTerm(String term) {

		//Linhas comentadas para funcionar o InterfaceMetric (04/01/16)
		//term = StringUtils.replace(term, "[", "\\[");
		//term = StringUtils.replace(term, "]", "\\]");
		term = term.replace(',', ' ');
		
		expandedTerms.add(term);
	}

	public List<String> getExpandedTerms() {
		return expandedTerms;
	}

	public void setExpandedTerms(List<String> expandedTerms) {
		this.expandedTerms = expandedTerms;
	}
	
	public List<String> getExpandedTermsNot() {
		return expandedTermsNot;
	}
	
	public void setExpandedTermsNot(List<String> expandedTermsNot) {
		this.expandedTermsNot = expandedTermsNot;
	}
}

package com.edu.utils;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.edu.model.InjectionResult;

public class ResultSpecification implements Specification<InjectionResult> {

	private InjectionResult filter;

	public ResultSpecification(InjectionResult filter) {
		super();
		this.filter = filter;
	}
	@Override
	public Predicate toPredicate(Root<InjectionResult> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
		
		
		Predicate p = cb.and();
		
		
		if (filter.getPrevention() != null) {
			p.getExpressions().add(cb.like(root.get("prevention").get("preventionName"), "%" + filter.getPrevention().getPreventionName() + "%"));
		}
		if (filter.getVaccine() != null) {
			p.getExpressions().add(cb.equal(root.get("vaccine").get("vaccineId"), filter.getVaccine().getVaccineId()));
		}
		if (filter.getInjectionDate() != null) {
			p.getExpressions().add(cb.greaterThanOrEqualTo(root.<Date>get("injectionDate"), filter.getInjectionDate()));
		}
		if (filter.getNextInjectionDate() != null) {
			p.getExpressions().add(cb.lessThanOrEqualTo(root.<Date>get("injectionDate"), filter.getNextInjectionDate()));
		}

		return p;
	}

}

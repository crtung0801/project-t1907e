package com.edu.service.Impl;

import java.time.Month;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.edu.dto.ReportCustomerDto;
import com.edu.model.Customer;
import com.edu.model.InjectionResult;
import com.edu.model.Vaccine;
import com.edu.repository.ReportChartVaccineRepository;
import com.edu.repository.ReportCustomerRepository;
import com.edu.repository.ReportResultRepository;
import com.edu.repository.ReportVaccineRepository;
import com.edu.service.ReportService;
import com.edu.utils.ResultSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

	private final EntityManager entityManager;

	@Autowired
	private ReportVaccineRepository vaccineRepo;

	@Autowired
	private ReportCustomerRepository customerRepo;

	@Autowired
	private ReportResultRepository resultRepo;

	@Autowired
	private ReportChartVaccineRepository reportChartVaccineRepository;

	@SuppressWarnings("unchecked")
	@Override
	public Page<Vaccine> reportVaccines(int pageNumber, String vaccineTypeId, String origin, String startDate,
			String endDate) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 5);
		if (vaccineTypeId != null || origin != null || startDate != null || endDate != null) {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Vaccine> createQuery = criteriaBuilder.createQuery(Vaccine.class);
			Root<Vaccine> root = createQuery.from(Vaccine.class);
			createQuery.select(root);
			if (!startDate.isEmpty()) {
				createQuery.where(criteriaBuilder.greaterThanOrEqualTo(root.get("timeBeginNextInjection"), startDate));
			}
			if (!endDate.isEmpty()) {
				createQuery.where(criteriaBuilder.lessThanOrEqualTo(root.get("timeEndNextInjection"), endDate));
			}
			if (!startDate.isEmpty() && !endDate.isEmpty()) {
				createQuery.where(criteriaBuilder.and(
						criteriaBuilder.greaterThanOrEqualTo(root.get("timeBeginNextInjection"), startDate),
						criteriaBuilder.lessThanOrEqualTo(root.get("timeEndNextInjection"), endDate)));
			}
			if (!vaccineTypeId.isEmpty()) {
				createQuery.where(
						criteriaBuilder.like(root.get("vaccineType").get("vaccineTypeId"), "%" + vaccineTypeId + "%"));
			}
			if (!origin.isEmpty()) {
				createQuery.where(criteriaBuilder.like(root.get("origin"), "%" + origin + "%"));
			}
			List<Vaccine> resultList = entityManager.createQuery(createQuery).getResultList();
			Page<Vaccine> listVaccine = new PageImpl<Vaccine>(resultList);
			return listVaccine;
		}
		return vaccineRepo.reportNoSearch(pageable);
	}

	@Override
	public int countCustomer(int year, int month) {
		return resultRepo.countByInjectResult(year, month);
	}

	@Override
	public int countInjectResult(int year, int month) {
		return resultRepo.countInjectResult(year, month);
	}

	@Override
	public List<Integer> listYear() {
		return resultRepo.listYear();
	}

	@Override
	public Page<InjectionResult> findPaginated(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		Page<InjectionResult> findAll = resultRepo.findAll(pageable);
		return findAll;
	}

	@Override
	public Page<InjectionResult> filterPaginated(InjectionResult injectionResult, int pageNo, int pageSize) {
		Specification<InjectionResult> spec = new ResultSpecification(injectionResult);
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		Page<InjectionResult> findAll = resultRepo.findAll(spec, pageable);
		return findAll;
	}

	@Override
	public Page<Customer> searchCustomerReport(int pageNo, int pageSize, Date fromDate, Date toDate, String fullName,
			String address) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		Page<Customer> findAll = (Page<Customer>) customerRepo.findAll(new Specification<Customer>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				Predicate p = cb.and();
				if (Objects.nonNull(fromDate) && !Objects.nonNull(toDate)) {
					p.getExpressions().add(cb.greaterThan(root.<Date>get("dateOfBirth"), fromDate));
				}
				if (!Objects.nonNull(fromDate) && Objects.nonNull(toDate)) {
					p.getExpressions().add(cb.lessThan(root.<Date>get("dateOfBirth"), toDate));
				}
				if (Objects.nonNull(fromDate) && Objects.nonNull(toDate) && fromDate.before(toDate)) {
					p.getExpressions().add(cb.between(root.<Date>get("dateOfBirth"), fromDate, toDate));
				}
				if (!fullName.isEmpty()) {
					p.getExpressions().add(cb.like(root.get("fullName"), "%" + fullName + "%"));
				}
				if (!address.isEmpty()) {
					p.getExpressions().add(cb.like(root.get("address"), "%" + address + "%"));
				}
				cq.orderBy(cb.desc(root.get("fullName")));
				return p;
			}
		}, pageable);
		return findAll;

	}

	@Override
	public Page<Customer> findPaginatedCus(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		Page<Customer> findAll = customerRepo.findAll(pageable);
		return findAll;
	}

	@Override
	public List<InjectionResult> findAll() {
		return (List<InjectionResult>) resultRepo.findAll();
	}

	@Override
	public Map<Month, Integer> numberInjectionByMonth(Integer year, String typeName) {
		Map<Month, Integer> treeMap = new TreeMap<Month, Integer>();

		treeMap.put(Month.JANUARY, reportChartVaccineRepository.numberInjectM1(year, typeName));

		treeMap.put(Month.FEBRUARY, reportChartVaccineRepository.numberInjectM2(year, typeName));

		treeMap.put(Month.MARCH, reportChartVaccineRepository.numberInjectM3(year, typeName));

		treeMap.put(Month.APRIL, reportChartVaccineRepository.numberInjectM4(year, typeName));

		treeMap.put(Month.MAY, reportChartVaccineRepository.numberInjectM5(year, typeName));

		treeMap.put(Month.JUNE, reportChartVaccineRepository.numberInjectM6(year, typeName));

		treeMap.put(Month.JULY, reportChartVaccineRepository.numberInjectM7(year, typeName));

		treeMap.put(Month.AUGUST, reportChartVaccineRepository.numberInjectM8(year, typeName));

		treeMap.put(Month.SEPTEMBER, reportChartVaccineRepository.numberInjectM9(year, typeName));

		treeMap.put(Month.OCTOBER, reportChartVaccineRepository.numberInjectM10(year, typeName));

		treeMap.put(Month.NOVEMBER, reportChartVaccineRepository.numberInjectM11(year, typeName));

		treeMap.put(Month.DECEMBER, reportChartVaccineRepository.numberInjectM12(year, typeName));

		return treeMap;
	}
}

package com.edu.service.Impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.edu.model.Vaccine;
import com.edu.model.VaccineType;
import com.edu.repository.ReadFileReposiroty;
import com.edu.service.ReadFileService;
import com.edu.service.VaccineService;
import com.edu.service.VaccineTypeService;
import com.edu.utils.Constant;
import com.edu.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ReadFileServiceImpl implements ReadFileService {

	@Autowired
	private ReadFileReposiroty readFileReposiroty;

	@Autowired
	private VaccineTypeService vaccineTypeService;
	
	@Autowired
	private VaccineService vaccineService;

	@Override
	public List<Vaccine> findAll() {
		return (List<Vaccine>) readFileReposiroty.findAll();
	}

	@Override
	public boolean save(@RequestPart("file") MultipartFile file) throws Exception {
		boolean isFlag = false;
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		if (extension.equalsIgnoreCase("json")) {
			isFlag = readDataFromJson(file);
		} else if (extension.equalsIgnoreCase("xlsx") || extension.equalsIgnoreCase("xls")) {
			isFlag = readDataFromExcel(file);
		}
		return isFlag;
	}

	private boolean readDataFromExcel(MultipartFile file) throws Exception {
		Workbook workbook = getWorkBook(file);
		DataFormatter formatter = new DataFormatter();
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rows = sheet.iterator();
		rows.next();
		while (rows.hasNext()) {
			Row row = rows.next();
			Vaccine vaccine = Vaccine.builder()
					.vaccineId(formatter.formatCellValue(row.getCell(0)))
					.vaccineName(formatter.formatCellValue(row.getCell(1)))
					.vaccineType(vaccineTypeService.findById(formatter.formatCellValue(row.getCell(2))))
					.numberOfInjection(Utils.number(formatter.formatCellValue(row.getCell(3))))
					.origin(formatter.formatCellValue(row.getCell(4)))
					.timeBeginNextInjection(Utils.date(formatter.formatCellValue(row.getCell(5))))
					.timeEndNextInjection((Utils.date(formatter.formatCellValue(row.getCell(6)))))
					.contraindication(formatter.formatCellValue(row.getCell(7)))
					.indication(formatter.formatCellValue(row.getCell(8)))
					.active(true)
					.build();
			vaccineService.save(vaccine);
		}

		return true;
	}

	private Workbook getWorkBook(MultipartFile file) {
		Workbook workbook = null;
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		try {
			if (extension.equalsIgnoreCase("xlsx")) {
				workbook = new XSSFWorkbook(file.getInputStream());
			} else if (extension.equalsIgnoreCase("xls")) {
				workbook = new HSSFWorkbook(file.getInputStream());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workbook;
	}

	private boolean readDataFromJson(MultipartFile file) {
		try {
			InputStream inputStream = file.getInputStream();
			ObjectMapper mapper = new ObjectMapper();
			List<Vaccine> vaccines = Arrays.asList(mapper.readValue(inputStream, Vaccine[].class));

			if (vaccines != null && vaccines.size() > 0) {
				for (Vaccine vaccine : vaccines) {
					vaccine.setFileType(FilenameUtils.getExtension(file.getOriginalFilename()));
					readFileReposiroty.save(vaccine);
				}
			}
			return true;

		} catch (Exception e) {
			return false;
		}

	}

}

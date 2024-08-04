package com.backend.api.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DataTablesResponse<T> {

	private int currentPage;
	private long totalItems;
	private int totalPages;
	private List<T> data;
}

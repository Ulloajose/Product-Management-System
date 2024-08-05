package com.backend.api.domain.port.in;

import com.backend.api.domain.dto.StatisticDto;

import java.util.List;

public interface GetStatisticUseCase {

    List<StatisticDto> getStatistic();
}

package com.backend.api.application.usercase;

import com.backend.api.domain.dto.StatisticDto;
import com.backend.api.domain.port.in.GetStatisticUseCase;
import com.backend.api.domain.port.out.StatisticRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetStatisticUserCaseImpl implements GetStatisticUseCase {

    private final StatisticRepositoryPort statisticRepositoryPort;

    @Override
    public List<StatisticDto> getStatistic() {
        return statisticRepositoryPort.findAll();
    }
}

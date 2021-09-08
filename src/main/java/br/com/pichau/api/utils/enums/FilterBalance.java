package br.com.pichau.api.utils.enums;

import br.com.pichau.api.controllers.response.DayBalance;
import br.com.pichau.api.repositories.ChargesRepository;
import br.com.pichau.api.repositories.GenerationRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public enum FilterBalance {
    charge,
    generation,
    both
}

package com.cleargoal.monthlycycle.infrastructure;

import com.cleargoal.monthlycycle.domain.MonthlyCycle;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthlyCycleRepository extends JpaRepository<MonthlyCycle, Long> {
    Optional<MonthlyCycle> findTopByOrderByCycleMonthDesc();
}

package com.cleargoal.settings.infrastructure;

import com.cleargoal.settings.domain.SettingsProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingsProfileRepository extends JpaRepository<SettingsProfile, Long> {
}

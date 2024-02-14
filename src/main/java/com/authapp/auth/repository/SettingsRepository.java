package com.authapp.auth.repository;

import com.authapp.auth.entity.AppSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingsRepository extends JpaRepository<AppSettings, Long> {

}

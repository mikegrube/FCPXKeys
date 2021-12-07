package com.camas.shortcutkeys;

import org.springframework.data.jpa.repository.JpaRepository;

interface KeyPositionRepository extends JpaRepository<KeyPosition, Long> {

	KeyPosition findByKey(String key);

	KeyPosition findByCode(Integer key);
}


package com.camas.shortcutkeys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

interface CombinationRepository extends JpaRepository<Combination, Long>, JpaSpecificationExecutor {

}


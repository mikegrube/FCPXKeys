package com.camas.shortcutkeys;

import org.springframework.data.jpa.domain.Specification;

public class CombinationSpecifications {

	public static Specification<Combination> hasCommandKeyDown() {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Combination_.commandKey), 1);
	}

	public static Specification<Combination> hasShiftKeyDown() {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Combination_.shiftKey), 1);
	}

	public static Specification<Combination> hasOptionKeyDown() {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Combination_.optionKey), 1);
	}

	public static Specification<Combination> hasControlKeyDown() {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Combination_.controlKey), 1);
	}

	public static Specification<Combination> hasKeyCode(Integer code) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Combination_.code), code);
	}
}

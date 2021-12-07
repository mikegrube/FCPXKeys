package com.camas.shortcutkeys;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.camas.shortcutkeys.CombinationSpecifications.*;
import static org.springframework.data.jpa.domain.Specification.where;

@Slf4j
@Service
public class CombinationService {

	@Autowired
	private CombinationRepository combinationRepository;

	@Autowired
	private KeyPositionRepository keyPositionRepository;

	//CONSTANTS
	int[] KEYHEIGHT = {42, 89};
	int[] KEYWIDTH = {42, 53, 65, 76, 97, 272};
	int[] ROWY = {23, 66, 110, 154, 198, 243};
	int LEFT = 6;
	int PAD = 4;


	public Iterable<Combination> list() {
		return combinationRepository.findAll();
	}

	public Combination get(Long id) {
		Optional<Combination> combination = combinationRepository.findById(id);
		return combination.orElse(null);
	}

	public Combination save(Combination combination) {
		return combinationRepository.save(combination);
	}

	public Long count() {
		return combinationRepository.count();
	}

	//=====

	public void load() {

		if (combinationRepository.count() == 0) {

			try {

				List<Combination> combinations = CSVHelperCombinations.load(new FileInputStream(new File("FCPX Keyboard Shortcuts.csv")));

				combinationRepository.saveAll(combinations);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			//Now update the codes
			List<Combination> combinations = combinationRepository.findAll();
			for (Combination combination : combinations) {
				KeyPosition keyPosition = keyPositionRepository.findByKey(combination.getKey());
				if (keyPosition != null) {
					combination.setCode(keyPosition.getCode());
					save(combination);
				}
			}
		}
	}

	//===== Key display rectangles

	public List<Rectangle> getRectangles(CombinationSearch search) {

		List<Rectangle> rectangles = new ArrayList<>();

		if (search != null) {

			if (search.getCommandKeyDown() == 1) {
				rectangles.add(getRectangle(keyPositionRepository.findByKey("ctrl")));
			}
			if (search.getShiftKeyDown() == 1) {
				rectangles.add(getRectangle(keyPositionRepository.findByKey("shift")));
			}
			if (search.getOptionKeyDown() == 1) {
				rectangles.add(getRectangle(keyPositionRepository.findByKey("option")));
			}
			if (search.getControlKeyDown() == 1) {
				rectangles.add(getRectangle(keyPositionRepository.findByKey("control")));
			}
			if (search.getKey() != null) {
				rectangles.add(getRectangle(keyPositionRepository.findByCode(search.getCode())));
			}
		}

		return rectangles;
	}

	public Rectangle getRectangle(KeyPosition keyPosition) {

		Rectangle rectangle = new Rectangle();

		if (keyPosition == null) {
			return rectangle;
		}

		rectangle.y = ROWY[keyPosition.getY()];
		rectangle.height = KEYHEIGHT[0];
		rectangle.width = KEYWIDTH[keyPosition.keySize.ordinal()];

		switch (keyPosition.getY()) {
			case 0:
				rectangle.x = LEFT;
				if (keyPosition.getX() > 0) {
					rectangle.x += KEYWIDTH[KeySize.TAB.ordinal()] + PAD;
					rectangle.x += keyPosition.getX() * (PAD + KEYWIDTH[KeySize.REGULAR.ordinal()]);
				}
				break;
			case 1:
				rectangle.x = LEFT;
				rectangle.x += keyPosition.getX() * (PAD + KEYWIDTH[KeySize.REGULAR.ordinal()]);
				break;
			case 2:
				rectangle.x = LEFT;
				if (keyPosition.getX() > 0) {
					rectangle.x += KEYWIDTH[KeySize.TAB.ordinal()] + PAD;
					rectangle.x += (keyPosition.getX() - 1) * (PAD + KEYWIDTH[KeySize.REGULAR.ordinal()]);
				}
				break;
			case 3:
				rectangle.x = LEFT;
				if (keyPosition.getX() > 0) {
					rectangle.x += KEYWIDTH[KeySize.CAPS.ordinal()] + PAD;
					rectangle.x += (keyPosition.getX() - 1)  * (PAD + KEYWIDTH[KeySize.REGULAR.ordinal()]);
				}
				break;
			case 4:
				rectangle.x = LEFT;
				if (keyPosition.getX() > 0) {
					rectangle.x += KEYWIDTH[KeySize.SHIFT.ordinal()] + PAD;
					rectangle.x += (keyPosition.getX() - 1) * (PAD + KEYWIDTH[KeySize.REGULAR.ordinal()]);
				}
				if (keyPosition.getX() > 11) {
					rectangle.x += 4 * PAD + 2 * KEYWIDTH[KeySize.REGULAR.ordinal()];
				}
				break;
			case 5:
				rectangle.x = LEFT;
				switch (keyPosition.getX()) {
					case 0:
						break;
					case 1:
						rectangle.x += PAD + KEYWIDTH[KeySize.TAB.ordinal()];
						break;
					case 2:
						rectangle.x += 2 * PAD + KEYWIDTH[KeySize.TAB.ordinal()] + KEYWIDTH[KeySize.OPTION.ordinal()];
						break;
					case 3:
						rectangle.x += 3 * PAD + 2 * KEYWIDTH[KeySize.TAB.ordinal()] + KEYWIDTH[KeySize.OPTION.ordinal()];
						break;
					case 4:
						rectangle.x += keyPosition.getX() * PAD + 2 * KEYWIDTH[KeySize.TAB.ordinal()] + KEYWIDTH[KeySize.OPTION.ordinal()] + KEYWIDTH[KeySize.SPACE.ordinal()];
						break;
					case 5:
						rectangle.x += keyPosition.getX() * PAD + 3 * KEYWIDTH[KeySize.TAB.ordinal()] + KEYWIDTH[KeySize.OPTION.ordinal()] + KEYWIDTH[KeySize.SPACE.ordinal()];
						break;
					case 6:
						rectangle.x += keyPosition.getX() * PAD + 2 * KEYWIDTH[KeySize.TAB.ordinal()] + 2 * KEYWIDTH[KeySize.OPTION.ordinal()] + KEYWIDTH[KeySize.SPACE.ordinal()];
						break;
					case 7:
						rectangle.x += keyPosition.getX() * PAD + 4 * KEYWIDTH[KeySize.TAB.ordinal()] + 2 * KEYWIDTH[KeySize.OPTION.ordinal()] + KEYWIDTH[KeySize.SPACE.ordinal()];
						break;
					case 8:
						rectangle.x += keyPosition.getX() * PAD + 4 * KEYWIDTH[KeySize.TAB.ordinal()] + 2 * KEYWIDTH[KeySize.OPTION.ordinal()] + KEYWIDTH[KeySize.SPACE.ordinal()] + KEYWIDTH[KeySize.REGULAR.ordinal()];
						break;
					case 9:
						rectangle.x += keyPosition.getX() * PAD + 4 * KEYWIDTH[KeySize.TAB.ordinal()] + 2 * KEYWIDTH[KeySize.OPTION.ordinal()] + KEYWIDTH[KeySize.SPACE.ordinal()] + 2 * KEYWIDTH[KeySize.REGULAR.ordinal()];
						break;
					default:
						break;
				}
				break;
			default:
				break;
		}

		return rectangle;
	}

	//===== Searching

	public List<Combination> findPossibles(CombinationSearch search) {

		Specification<Combination> specification = where(hasKeyCode(search.code));

		if (search.commandKeyDown == 1) {
			specification = specification.and(hasCommandKeyDown());
		}
		if (search.shiftKeyDown == 1) {
			specification = specification.and(hasShiftKeyDown());
		}
		if (search.optionKeyDown == 1) {
			specification = specification.and(hasOptionKeyDown());
		}
		if (search.controlKeyDown == 1) {
			specification = specification.and(hasControlKeyDown());
		}

		return combinationRepository.findAll(specification);
	}

}

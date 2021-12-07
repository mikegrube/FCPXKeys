package com.camas.shortcutkeys;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class KeyPositionService {

	@Autowired
	private KeyPositionRepository keyPositionRepository;

	public Iterable<KeyPosition> list() {
		return keyPositionRepository.findAll();
	}

	public KeyPosition get(Long id) {
		Optional<KeyPosition> keyPosition = keyPositionRepository.findById(id);
		return keyPosition.orElse(null);
	}

	public KeyPosition save(KeyPosition keyPosition) {
		return keyPositionRepository.save(keyPosition);
	}

	public void delete(Long id) {
		keyPositionRepository.deleteById(id);
	}

	//=====

	public void load() {

		if (keyPositionRepository.count() == 0) {

			try {

				List<KeyPosition> keyPositions = CSVHelperKeyPositions.load(new FileInputStream(new File("Key Positions.csv")));

				keyPositionRepository.saveAll(keyPositions);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public Long count() {
		return keyPositionRepository.count();
	}
}

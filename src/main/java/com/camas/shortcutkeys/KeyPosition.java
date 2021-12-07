package com.camas.shortcutkeys;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class KeyPosition {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String key;
	private Integer code;
	private Integer x;	//Key position across
	private Integer y;	//Key row (0-based)

	@Enumerated(EnumType.STRING)
	KeySize keySize;

}

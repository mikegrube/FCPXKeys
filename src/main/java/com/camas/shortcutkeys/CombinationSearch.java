package com.camas.shortcutkeys;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CombinationSearch {

	Integer code;
	Character key;
	Integer commandKeyDown;
	Integer shiftKeyDown;
	Integer optionKeyDown;
	Integer controlKeyDown;


}

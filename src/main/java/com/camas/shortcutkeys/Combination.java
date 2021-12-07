package com.camas.shortcutkeys;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Combination {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String description;		//Description of what the command does
	private String area;			//Functional area of the command
	private String command;			//Short definition
	private String combination;		//String representation of the command keystrokes
	private String key;				//The base key
	private Integer code;			//The base key code
	private Integer commandKey;		//Command key down?
	private Integer shiftKey;		//Shift key down?
	private Integer optionKey;		//Option key down?
	private Integer controlKey;		//Control key down?

	public String  paragraph() {
		StringBuilder sb = new StringBuilder();
		sb.append("Command: ").append(command).append("\n");
		sb.append("Area: ").append(area).append("\n");
		sb.append("Description: ").append(description).append("\n");
		sb.append("Combination: ").append(combination).append("\n");
		sb.append("Key: ").append(key).append("\n");
		sb.append("Code: ").append(code).append("\n");
		sb.append("Command Key: ").append(commandKey).append("\n");
		sb.append("Shift Key: ").append(shiftKey).append("\n");
		sb.append("Option Key: ").append(optionKey).append("\n");
		sb.append("Control Key: ").append(controlKey).append("\n");

		return sb.toString();
	}

}

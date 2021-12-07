package com.camas.shortcutkeys;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Combination.class)
public class Combination_ {

//	public static volatile SingularAttribute<Combination, Integer> id;
//	public static volatile SingularAttribute<Combination, String> description;
//	public static volatile SingularAttribute<Combination, String> area;
//	public static volatile SingularAttribute<Combination, String> command;
//	public static volatile SingularAttribute<Combination, String> combination;
//	public static volatile SingularAttribute<Combination, String> key;
	public static volatile SingularAttribute<Combination, Integer> code;
	public static volatile SingularAttribute<Combination, Integer> commandKey;
	public static volatile SingularAttribute<Combination, Integer> shiftKey;
	public static volatile SingularAttribute<Combination, Integer> optionKey;
	public static volatile SingularAttribute<Combination, Integer> controlKey;

}

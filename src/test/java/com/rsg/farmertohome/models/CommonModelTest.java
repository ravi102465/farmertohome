package com.rsg.farmertohome.models;

import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.NoPublicFieldsExceptStaticFinalRule;
import com.openpojo.validation.rule.impl.NoPublicFieldsRule;
import com.openpojo.validation.rule.impl.NoStaticExceptFinalRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;

public class CommonModelTest {

	private static final String POJO_PACKAGE = "com.rsg.farmertohome.models";

	private List<PojoClass> pojoClasses;

	private ValidatorBuilder validatorBuilder;

	@Before
	public void setup() {
		pojoClasses = PojoClassFactory.getPojoClasses(POJO_PACKAGE);
		validatorBuilder = ValidatorBuilder.create();
		validatorBuilder.getRules().add(new NoStaticExceptFinalRule());
		validatorBuilder.getRules().add(new NoPublicFieldsExceptStaticFinalRule());
		validatorBuilder.getRules().add(new NoPublicFieldsRule());
		validatorBuilder.getTesters().add(new SetterTester());
		validatorBuilder.getTesters().add(new GetterTester());

	}

	@Test
	public void testPojoStructureAndBehavior() {
		for (PojoClass pojoClass : pojoClasses) {
			validatorBuilder.build().validate(pojoClass);
		}
	}
}


package hexmo;

import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;

/**
 * Valide les dépendances entre les paquetages.
 * */
public class ArchTest {
	@Test
	void domainsShouldBeAccessedBySupervisorsAndDomains() {
		var classes = new ClassFileImporter().importPackages("hexmo");
		
		ArchRule myRule = classes()
			    .that().resideInAPackage("hexmo.domains..")
			    .should().onlyBeAccessed()
			    .byAnyPackage("hexmo", "hexmo.supervisors..", "hexmo.domains..","hexmo.automated..");
		
		myRule.check(classes);
	}
	
	@Test
	void supervisorsShouldOnlyBeAccessedByViewsAndSupervisors() {
		var classes = new ClassFileImporter().importPackages("hexmo");
		
		ArchRule myRule = classes()
			    .that().resideInAPackage("hexmo.supervisors..")
			    .should().onlyBeAccessed()
			    .byAnyPackage("hexmo","hexmo.views..", "hexmo.supervisors..","hexmo.automated..", "hexmo.acceptances..");
		
		myRule.check(classes);
	}
	
	@Test
	void viewsShouldOnlyBeAccessedByViews() {
		var classes = new ClassFileImporter().importPackages("hexmo");
		
		ArchRule myRule = classes()
			    .that().resideInAPackage("hexmo.views..")
			    .should().onlyBeAccessed()
			    .byAnyPackage("hexmo","hexmo.views..","hexmo.automated..", "hexmo.acceptances..");
		
		myRule.check(classes);
	}
}
